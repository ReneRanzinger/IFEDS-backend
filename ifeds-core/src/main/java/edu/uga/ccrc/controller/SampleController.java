package edu.uga.ccrc.controller;

//import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.DatasetDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.dao.SampleDAO;
import edu.uga.ccrc.dao.SampleDescriptorDAO;
import edu.uga.ccrc.dao.SampleToSampleDescriptorDAO;
import edu.uga.ccrc.dao.SampleTypeDAO;
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.entity.Sample;
import edu.uga.ccrc.entity.SampleDescriptor;
import edu.uga.ccrc.entity.SampleToSampleDescriptor;
import edu.uga.ccrc.entity.SampleToSampleDescriptorPK;
import edu.uga.ccrc.entity.SampleType;
import edu.uga.ccrc.exception.EntityNotFoundException;
import edu.uga.ccrc.exception.SQLException;
import edu.uga.ccrc.view.bean.CreateSampleHelperBean;
import edu.uga.ccrc.view.bean.CreateSampleToSampleDescriptorHelperBean;
import edu.uga.ccrc.view.bean.DatasetBean;
import edu.uga.ccrc.view.bean.SampleBean;
import edu.uga.ccrc.view.bean.SampleToSampleDescriptorBean;

@RestController
public class SampleController {
	@Autowired
	ProviderDAO providerDao;

	@Autowired
	SampleDAO sampleDAO;
	
	@Autowired
	SampleTypeDAO sampleTypeDAO;
	
	@Autowired
	SampleDescriptorDAO sampleDescriptorDAO;
	
	@Autowired
	SampleToSampleDescriptorDAO sampleToSampleDescriptorDAO;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/getSample", produces="application/json")
	public List<SampleBean> getSamples(HttpServletRequest request, HttpServletResponse response) throws EntityNotFoundException {
		
		System.out.println("Retrieving provider's uploaded dataset information : getSamples() ");
		final String requestTokenHeader = request.getHeader("Authorization");
		
		//token starts after 7th position as token is appnended with 'Bearer' 
		String jwtToken = requestTokenHeader.substring(7);
		
		//get username from token
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

		//get provider data by username
		Provider provider = providerDao.findByUsername(username);
		
		//
		
		String providerName = provider.getName();
		
		//search in the dataset, the dataset owned by current provider
		List<SampleBean> result = new ArrayList<SampleBean>();
		
		for (Sample sample : sampleDAO.findSampleByProviderId(provider.getProviderId())) {
		
			SampleBean sampleBean = new SampleBean();
			
			sampleBean.setSampleId(sample.getSampleId());
			sampleBean.setSampleTypeId(sample.getSampleType().getSampleTypeId());
			sampleBean.setSampleTypeName(sample.getSampleType().getName());
			sampleBean.setUrl(sample.getUrl());
			sampleBean.setName(sample.getName());
			sampleBean.setDescription(sample.getDescription());

			
			result.add(sampleBean);
			
		}
		
		return result;
	
		
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/samples/{id}", produces="application/json")
	public SampleBean getSample(HttpServletRequest request, @PathVariable Long id, HttpServletResponse response){
		
		
		System.out.println("in samples/id");
		
		//get sample
		Sample sample = sampleDAO.findById(id).orElse(null);
		if(sample == null) return null; //no sample found
		
		//result list
		SampleBean result = new SampleBean(sample);
		
		
		//get sample Descriptors
		List<SampleToSampleDescriptor> sampleDescriptors = sampleToSampleDescriptorDAO.findSamepleDescriptorsBySampleId(sample.getSampleId());
		
		
		List<SampleToSampleDescriptorBean> sdBean = new ArrayList<>();
		
		for(SampleToSampleDescriptor sampleDesc : sampleDescriptors) {
			SampleToSampleDescriptorBean sampleToSampleDescriptorBean = new SampleToSampleDescriptorBean();
			
			sampleToSampleDescriptorBean.setSampleDescriptor(sampleDesc.getSampleDescriptor());
			sampleToSampleDescriptorBean.setUnitOfMeasurement(sampleDesc.getUnitOfMeasurement());
			sampleToSampleDescriptorBean.setValue(sampleDesc.getSampleToSampleDescPK().getSampleDescriptorValue());
			sdBean.add(sampleToSampleDescriptorBean);
			
		}
		
		//add all sample descriptor to result bean
		result.setSampleToSameDescriptorBean(sdBean);
		
		
		return result;
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/samples", produces="application/json")
	public String createSample(HttpServletRequest request, @Valid  @RequestBody CreateSampleHelperBean sampleHelperBean) throws SQLException, EntityNotFoundException{
	
		System.out.println("In Create Sample : ");
		Long savedSampleId = null;
		
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		
		//get username from token
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		Provider owner = providerDao.findByUsername(username);
		
		//get sampleType from id
		SampleType sampleType = sampleTypeDAO.findSampleTypeById(sampleHelperBean.getSample_type_id());
		
		//create sample	
		Sample newSample = new Sample();
		
		newSample.setProvider(owner);
		newSample.setName(sampleHelperBean.getName());
		newSample.setSampleType(sampleType);
		newSample.setUrl(sampleHelperBean.getUrl());
		newSample.setDescription(sampleHelperBean.getDescription());
		Sample saved = null;
		try {
		 saved = sampleDAO.save(newSample);
		}catch(Exception e) {
			
			 throw new SQLException("Cannot save the sample. :  "+e.getLocalizedMessage());
		}
		savedSampleId = saved.getSampleId();			
		
		//create entry in sampleToSampleDescriptors
		//sample descriptors are stored by Ids in the List of Longs
		//need to save same descriptors one by one from the list
		
		for(CreateSampleToSampleDescriptorHelperBean descriptor : sampleHelperBean.getSample_descriptors()) {
			
			//1) Create composite Primary key
			SampleToSampleDescriptorPK sampleToSampleDescPK = new SampleToSampleDescriptorPK(newSample.getSampleId(),descriptor.getSample_descriptor_id(),descriptor.getSample_descriptor_value());
			
			//2) Create SampleToSampleDescriptor object
			SampleToSampleDescriptor sampleToSampleDescriptor = new SampleToSampleDescriptor();
			
			//3) Get Sample Descriptor
			SampleDescriptor sampleDescriptor = sampleDescriptorDAO.findSampleDescriptorById(descriptor.getSample_descriptor_id());	
			
			if(sampleDescriptor == null) {
				throw new EntityNotFoundException("Sample Descriptor not present " + descriptor.getSample_descriptor_id());
			}
			
			//4) Set all entries
			sampleToSampleDescriptor.setSampleToSampleDescPK(sampleToSampleDescPK);	
			sampleToSampleDescriptor.setSample(saved);
			sampleToSampleDescriptor.setSampleDescriptor(sampleDescriptor);
			sampleToSampleDescriptor.setUnitOfMeasurement(descriptor.getUnit_of_measurment());
			
			//5) Save
			SampleToSampleDescriptor savedEntry = sampleToSampleDescriptorDAO.save(sampleToSampleDescriptor);
			

		}
	
		return "{\n\tmessage: new sample created succssfully \n\t" + "id :" + saved.getSampleId() + "}";
	
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/samples/{id}", produces="application/json")
	public String updateSample(HttpServletRequest request, @PathVariable Long id, @Valid  @RequestBody CreateSampleHelperBean sampleHelperBean) throws EntityNotFoundException {
		
		System.out.println("In update Sample : ");
		
	
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		
		//get username from token
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		Provider owner = providerDao.findByUsername(username);
		
		//get sampleType from id
		SampleType sampleType = sampleTypeDAO.findSampleTypeById(sampleHelperBean.getSample_type_id());
		Sample sample;
		
		
		//create sample	
		
		sample = sampleDAO.findById(id).orElse(null);
		if(sample == null)
			throw new EntityNotFoundException("Sample with id : "+ id +" not present");
		
		sample.setProvider(owner);
		sample.setName(sampleHelperBean.getName());
		sample.setSampleType(sampleType);
		sample.setUrl(sampleHelperBean.getUrl());
		sample.setDescription(sampleHelperBean.getDescription());
		Sample saved = sampleDAO.save(sample);
		
		//add sample descriptor
		//flush all sameToSampleDescriptor entries
		sampleToSampleDescriptorDAO.deleteSampleToSampleDescriptorBySampleId(id);
		
		for(CreateSampleToSampleDescriptorHelperBean descriptor : sampleHelperBean.getSample_descriptors()) {
			
			//1) Create composite Primary key
			SampleToSampleDescriptorPK sampleToSampleDescPK = new SampleToSampleDescriptorPK(saved.getSampleId(),descriptor.getSample_descriptor_id(),descriptor.getSample_descriptor_value());
			
			//2) Create SampleToSampleDescriptor object
			SampleToSampleDescriptor sampleToSampleDescriptor = new SampleToSampleDescriptor();
			
			//3) Get Sample Descriptor
			SampleDescriptor sampleDescriptor = sampleDescriptorDAO.findSampleDescriptorById(descriptor.getSample_descriptor_id());	
			
			
			if(sampleDescriptor == null) {
				throw new EntityNotFoundException("Sample Descriptor not present " + descriptor.getSample_descriptor_id());
			}
			
			//4) Set all entries
			sampleToSampleDescriptor.setSampleToSampleDescPK(sampleToSampleDescPK);	
			sampleToSampleDescriptor.setSample(saved);
			sampleToSampleDescriptor.setSampleDescriptor(sampleDescriptor);
			sampleToSampleDescriptor.setUnitOfMeasurement(descriptor.getUnit_of_measurment());
			
			//5) Save
			SampleToSampleDescriptor savedEntry = sampleToSampleDescriptorDAO.save(sampleToSampleDescriptor);
			

		}
		return "message : Sample Updated : " + id;

			
	}
	
		
	
		
	
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/samples/{id}", produces="application/json")
	public List<String> deleteSample(@PathVariable Long id) throws EntityNotFoundException {
		List<String> res = new ArrayList<>();
		if(sampleDAO.findById(id).orElse(null) == null)
			throw new EntityNotFoundException("Id not present : " + id);
		
		System.out.println("Deleting Sample : deleteSample() id : " + id);
		sampleDAO.deleteById(id);
		res.add("Sample with id " + id +" deleted successfully");	
			
		
		return res;
	}
	
	
}

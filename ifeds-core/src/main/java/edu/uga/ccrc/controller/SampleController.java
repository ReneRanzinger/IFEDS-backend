package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import edu.uga.ccrc.dao.SampleTypeDAO;
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.entity.Sample;
import edu.uga.ccrc.entity.SampleDescriptor;
import edu.uga.ccrc.entity.SampleToSampleDescriptorPK;
import edu.uga.ccrc.entity.SampleType;
import edu.uga.ccrc.view.bean.CreateSampleHelperBean;
import edu.uga.ccrc.view.bean.DatasetBean;
import edu.uga.ccrc.view.bean.SampleBean;

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
	
	//@Autowired
	//SampleToSampleDescriptorDAO sampleToSampleDescriptorDAO;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSample", produces="application/json")
	public List<SampleBean> getSamples(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Retrieving provider's uploaded dataset information : getSamples() ");
		final String requestTokenHeader = request.getHeader("Authorization");
		
		//token starts after 7th position as token is appnended with 'Bearer' 
		String jwtToken = requestTokenHeader.substring(7);
		
		//get username from token
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

		//get provider data by username
		Provider provider = providerDao.findByUsername(username); 
		String providerName = provider.getName();
		
		//search in the dataset, the dataset owned by current provider
		List<SampleBean> result =new ArrayList<SampleBean>();
		
		for (Sample sample : sampleDAO.findSampleByProviderId(provider.getProviderId())) {
		
			SampleBean sampleBean = new SampleBean();
			
			sampleBean.setSampleId(sample.getSampleId());
			sampleBean.setName(sample.getName());
			sampleBean.setDescription(sample.getDescription());
			
			result.add(sampleBean);
			
		}
		
		return result;
	
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/samples", produces="application/json")
	public String createSample(HttpServletRequest request, @Valid  @RequestBody CreateSampleHelperBean sampleHelperBean) {
	
		System.out.println("In Create Sample : ");
		
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
		Sample saved = sampleDAO.save(newSample);
						
		//create entry in sampleToSampleDescriptors
		//sample descriptors are stored by Ids in the List of Longs
		//need to save same descriptors one by one from the list
		
		for(Long descriptorIds : sampleHelperBean.getSample_descriptor_id()) {
			System.out.println(descriptorIds);
			
		}
	
		return "{\n\tmessage: new sample created succssfully \n\t" + "id :" + saved.getSampleId() + "}";
	
	}
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/samples/{id}", produces="application/json")
	public List<String> deleteDataset(@PathVariable Long id) {
		List<String> res = new ArrayList<>();
		System.out.println("Deleting datasets : deleteDataset() id : " + id);
		sampleDAO.deleteById(id);
		res.add("success");	
			
		
		return res;
	}
	
	
}

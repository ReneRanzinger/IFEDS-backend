package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.dao.SampleDescriptorDAO;
import edu.uga.ccrc.entity.SampleDescriptor;
import edu.uga.ccrc.entity.SampleType;
import edu.uga.ccrc.view.bean.SampleDescriptorBean;
import edu.uga.ccrc.view.bean.SampleTypeBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@CrossOrigin
@RestController
public class SampleDescriptorController {
	@Autowired
	SampleDescriptorDAO SampleDescriptorDAO;
	
	final static Logger log = LoggerFactory.getLogger(SampleController.class);
	
	/*
	 * the method returns list of sameDescriptors
	 * */
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/SampleDescriptors", produces="application/json")
	@ApiOperation(value = "Get Sample Descriptors", response = SampleDescriptorBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Accessing the sample descriptors is forbidden"),
			@ApiResponse(code = 404, message = "The sample descriptor resource is not found") })
	public List<SampleDescriptorBean> listAllSampleDescriptors(){
		List<SampleDescriptorBean> result = new ArrayList<>();
		log.info("Getting Sample Descriptors");
		try {
			
			for(SampleDescriptor sampleDescriptor : SampleDescriptorDAO.findAll()) {
				
				SampleDescriptorBean sampleDescriptorBean = new SampleDescriptorBean();
				
				sampleDescriptorBean.setDescriptor(sampleDescriptor.getDescription());
				sampleDescriptorBean.setName(sampleDescriptor.getName());
				sampleDescriptorBean.setLink_pattern(sampleDescriptor.getLinkPattern());
				sampleDescriptorBean.setNamespace(sampleDescriptor.getNamespace());
				sampleDescriptorBean.setSample_descriptor_id(sampleDescriptor.getSampleDescriptorId());
				sampleDescriptorBean.setUrl(sampleDescriptor.getUrl());
				result.add(sampleDescriptorBean);
				
			}
			
			return result;
		}catch(Exception e) {
			log.error(e.getMessage());
			return result;
		}
		
				
	}
	
}

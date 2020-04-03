package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.dao.SampleTypeDAO;
import edu.uga.ccrc.entity.SampleType;
import edu.uga.ccrc.view.bean.SampleTypeBean;
import edu.uga.ccrc.dao.SampleDescriptorDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@CrossOrigin
@RestController
public class SampleTypeController {
	
	@Autowired
	SampleTypeDAO SampleTypeDAO;
	
	@Autowired
	SampleDescriptorDAO SampleDescriptorDAO;
	
	/*
	 * the method returns list of Sample Types
	 * 
	 * */
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/SampleTypes", produces="application/json")
	@ApiOperation(value = "Get Sample Type", response = SampleTypeBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Accessing the sample type is forbidden"),
			@ApiResponse(code = 404, message = "The sample type resource is not found") })
	public List<SampleTypeBean> listAllSampleType(){
		System.out.println("Getting Sample Types");
		List<SampleTypeBean> result = new ArrayList<>();
		
		for(SampleType sampleType : SampleTypeDAO.findAll()) {
			
			SampleTypeBean sampleTypeBean = new SampleTypeBean();
			sampleTypeBean.setName(sampleType.getName());
			sampleTypeBean.setDescriptor(sampleType.getDescription());
			sampleTypeBean.setSampleTypeId(sampleType.getSampleTypeId());
			sampleTypeBean.setUrl(sampleType.getUrl());
			
			result.add(sampleTypeBean);
			
		}
		
		return result;
	}
	
	
	
	
	
}

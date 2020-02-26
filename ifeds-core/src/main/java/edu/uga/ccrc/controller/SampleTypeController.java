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

@CrossOrigin
@RestController
public class SampleTypeController {
	
	@Autowired
	SampleTypeDAO SampleTypeDAO;
	
	@Autowired
	SampleDescriptorDAO SampleDescriptorDAO;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/SampleTypes", produces="application/json")
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

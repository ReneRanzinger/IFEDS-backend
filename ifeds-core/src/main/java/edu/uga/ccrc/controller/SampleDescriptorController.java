package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;

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

@CrossOrigin
@RestController
public class SampleDescriptorController {
	@Autowired
	SampleDescriptorDAO SampleDescriptorDAO;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/SampleDescriptors", produces="application/json")
	public List<SampleDescriptorBean> listAllSampleDescriptors(){
		List<SampleDescriptorBean> result = new ArrayList<>();
		System.out.println("Getting Sample Descriptors");
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
			return result;
		}
		
				
	}
	
}

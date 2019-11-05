package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.entity.Sample;
import edu.uga.ccrc.view.bean.DatasetBean;
import edu.uga.ccrc.view.bean.SampleBean;

@RestController
public class SampleController {
	@Autowired
	ProviderDAO providerDao;

	@Autowired
	SampleDAO sampleDAO;
	
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
	public Sample createSample(HttpServletRequest request, @RequestBody Sample sample) {
		
		System.out.println(sample.getName());
		System.out.println(sample.getUrl());
		
		//System.out.println(sample.getProvider().getProviderId());
		//Sample saved = sampleDAO.save(sample);
		return null;
	
	
	
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

package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.DatasetDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.view.bean.DatasetBean;
import edu.uga.ccrc.view.bean.ProviderBean;

@RestController
public class ProviderController {
	
	@Autowired
	ProviderDAO providerDao;

	@Autowired
	DatasetDAO datasetDAO;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@RequestMapping(method = RequestMethod.GET, value = "/getProvider", produces="application/json")
	public List<ProviderBean> findInformation() {
		//this method under development
		System.out.println("Retrieving provider information : findInformation() ");
		List<ProviderBean> result = new ArrayList<ProviderBean>();
		long id = 1;
		Provider provider = providerDao.findByUsername("ccrc_user"); 
		ProviderBean providerBean = new ProviderBean();
		providerBean.setName(provider.getName());
		providerBean.setEmail(provider.getEmail());
		providerBean.setUsername(provider.getUsername());
		providerBean.setProviderId(provider.getProviderId());
		result.add(providerBean);
		
		return result;
		
		
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getProviderDatasets", produces="application/json")
	public List<DatasetBean> getProviderDataSets(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Retrieving provider's uploaded dataset information : getProviderDataSets() ");		
		
		final String requestTokenHeader = request.getHeader("Authorization");
		
		//token starts after 7th position as token is appnended with 'Bearer' 
		String jwtToken = requestTokenHeader.substring(7);
		
		//get username from token
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

		//get provider data by username
		Provider provider = providerDao.findByUsername(username); 
		String providerName = provider.getName();
		
		//search in the dataset, the dataset owned by current provider
		List<DatasetBean> result =new ArrayList<DatasetBean>();
	
		for (Dataset ds : datasetDAO.findAll()) {
			Provider currentProvider = ds.getProvider();
			if(currentProvider.getName().equals(providerName) ) {
				DatasetBean b=new DatasetBean();
				b.setDatasetId(ds.getDatasetId());
				b.setDatasetName(ds.getName());
				b.setDescription(ds.getDescription());
				b.setSampleName(ds.getSample().getName());
				b.setProviderName(ds.getProvider().getName());
				result.add(b);	
			}
			
		}
		return result;
	
	}
}

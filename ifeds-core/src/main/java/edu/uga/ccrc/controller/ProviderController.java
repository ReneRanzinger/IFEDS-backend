package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.DatasetDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.exception.EntityNotFoundException;
import edu.uga.ccrc.exception.NoResponeException;
import edu.uga.ccrc.exception.SQLException;
import edu.uga.ccrc.view.bean.DatasetBean;
import edu.uga.ccrc.view.bean.ProviderBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@CrossOrigin
@RestController
public class ProviderController {
	
	@Autowired
	ProviderDAO providerDao;

	@Autowired
	DatasetDAO datasetDAO;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@RequestMapping(method = RequestMethod.GET, value = "/getProvider", produces="application/json")
	@ApiOperation(value = "Get Provider Info", response = ProviderBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Accessing the Provider Info is forbidden"),
			@ApiResponse(code = 404, message = "The Provider Info is not found") })
	public ProviderBean findInformation(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Retrieving provider information : findByUsername() ");
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String jwtToken = requestTokenHeader.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		Provider provider = providerDao.findByUsername(username); 
		
		ProviderBean providerBean = new ProviderBean();
		providerBean.setName(provider.getName());
		providerBean.setEmail(provider.getEmail());
		providerBean.setUsername(provider.getUsername());
		providerBean.setProviderId(provider.getProviderId());
	
		
		return providerBean;
		
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/update_provider", produces="application/json")
	@ApiOperation(value = "Updates Provider(User profile) Info", response = ProviderBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "SQL Exception"),
			@ApiResponse(code = 403, message = "Accessing the Provider Info is forbidden"),
			@ApiResponse(code = 404, message = "The Provider Info is not found") })
	public String updateProviderInformation(HttpServletRequest request, HttpServletResponse response, @RequestBody ProviderBean providerBean) throws EntityNotFoundException, SQLException, NoResponeException {
		
		System.out.println("Updating provider information ");
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		
		if(username == null)
			throw new EntityNotFoundException("Invalid username");
		
		Provider provider = providerDao.findByUsername(username); 
		
		if(providerBean.getName().length() > 64)
			throw new SQLException("Name should be less than 64 character");
		provider.setName(providerBean.getName());
		
		if(providerBean.getAffiliation() != null && providerBean.getAffiliation().length() > 64)
			throw new SQLException("Affilation should be less than 64 character");
		provider.setAffiliation(providerBean.getAffiliation());

		if(providerBean.getContact() != null && providerBean.getContact().length() > 32)
			throw new SQLException("Contact should be less than 32 character");
		provider.setContact(providerBean.getContact());
		
		if(providerBean.getDepartment() != null && providerBean.getDepartment().length() > 64)
			throw new SQLException("Department should be less than 64 character");
		provider.setDepartment(providerBean.getDepartment());

		if(providerBean.getUrl() != null && providerBean.getUrl().length() > 256)
			throw new SQLException("URL should be less than 256 character");
		provider.setUrl(providerBean.getUrl());
		
		if(providerBean.getProviderGroup() != null && providerBean.getProviderGroup().length() > 64)
			throw new SQLException("Groups should be less than 64 character");
		provider.setProviderGroup(providerBean.getProviderGroup());

		try {
		providerDao.save(provider);}
		catch(Exception e) {
			throw new NoResponeException("Something went wrong");
		}
		return "{\n\t message : Successfully updated Provider's information \n}";
		
		
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getProviderDatasets", produces="application/json")
	@ApiOperation(value = "Get Provider Dataset", response = DatasetBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Accessing provider dataset is forbidden"),
			@ApiResponse(code = 404, message = "The provider dataset is not found") })
	public List<DatasetBean> getProviderDataSets(HttpServletRequest request, HttpServletResponse response) throws EntityNotFoundException {
		System.out.println("Retrieving provider's uploaded dataset information : findByUsername() ");		
		
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

		Provider provider = providerDao.findByUsername(username); 
		String providerName = provider.getName();
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

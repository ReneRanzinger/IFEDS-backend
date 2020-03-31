package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.DatasetDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.entity.Dataset;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.exception.EntityNotFoundException;
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

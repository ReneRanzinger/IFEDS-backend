package edu.uga.ccrc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.DatasetToExperimentTypeDAO;
import edu.uga.ccrc.dao.DatasetToKeywordDAO;
import edu.uga.ccrc.dao.ExperimentTypeDAO;
import edu.uga.ccrc.dao.FundingGrantDAO;
import edu.uga.ccrc.dao.FundingSourceDAO;
import edu.uga.ccrc.dao.KeywordDAO;
import edu.uga.ccrc.dao.PermissionsDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.dao.SampleDescriptorDAO;
import edu.uga.ccrc.dao.SampleToSampleDescriptorDAO;
import edu.uga.ccrc.entity.ExperimentType;
import edu.uga.ccrc.entity.FundingSource;
import edu.uga.ccrc.entity.Keyword;
import edu.uga.ccrc.entity.Permissions;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.entity.SampleDescriptor;
import edu.uga.ccrc.exception.EntityNotFoundException;
import edu.uga.ccrc.exception.ForbiddenException;
import edu.uga.ccrc.exception.NoResposneException;
import edu.uga.ccrc.exception.SQLException;
import edu.uga.ccrc.view.bean.DatasetBean;
import edu.uga.ccrc.view.bean.DictionaryBean;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
public class DictionaryController {
	@Autowired
	KeywordDAO keywordDAO; 
	
	@Autowired
	SampleDescriptorDAO sampleDescriptorDAO;
	
	@Autowired
	FundingSourceDAO fundingSourcetDAO;
	
	@Autowired
	DatasetToKeywordDAO datasetToKeywordDAO;
	
	@Autowired
	FundingGrantDAO fundingGrantDAO;
	
	@Autowired
	SampleToSampleDescriptorDAO sampleToSampleDescriptorDAO;
	
	@Autowired
	ExperimentTypeDAO experimentTypeDAO;
	
	@Autowired
	DatasetToExperimentTypeDAO datasetToExperimentTypeDAO;
	

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	PermissionsDAO permissionsDAO;
	
	
	@Autowired
	ProviderDAO providerDao;
	
	private boolean userIsAdmin(String username) {
		
		Provider provider = providerDao.findByUsername(username);
		
		Permissions p = permissionsDAO.findByProviderId(provider.getProviderId());
		System.out.println("Provider id : "+p.getPermission_level());
		if(p.getPermission_level().equals("admin"))
			return true;
		
		return false;
		
	}
	
	
	@ApiOperation(value = "View a list of dictionary valyes. Allowed Dictionary Names : 'keywords','experiment_type', 'funding_source', 'sample_descriptors' ", response = Object.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success")})
	@CrossOrigin
	@GetMapping(value = "/getDictionary/{dict_name}", produces = "application/json")
	// http://localhost:8080/datasets
	public Object getDictionart(HttpServletRequest request, @PathVariable String dict_name ) throws NoResposneException, EntityNotFoundException, ForbiddenException{
		
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		
		if(!userIsAdmin(username))
			throw new ForbiddenException("You don't have access to this web service");
			
		
		if(dict_name.equals("keywords"))
			return keywordDAO.findAll();
		

		else if(dict_name.equals("funding_source"))
			return fundingSourcetDAO.findAll();
		

		else if(dict_name.equals("sample_descriptors"))
			return sampleDescriptorDAO.findAll();
		
		else if(dict_name.equals("experiment_type"))
			return experimentTypeDAO.findAll();
		
		
		throw new EntityNotFoundException("Wrong dictionary name");
		
	}
	
	
	@ApiOperation(value = "	Add a value in dictionary. Allowed Dictionary Names : 'keywords','experiment_type', 'funding_source', 'sample_descriptors' ", response = Object.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success")})
	@CrossOrigin
	@PostMapping(value = "/dictionary/{dict_name}", produces = "application/json")
	// http://localhost:8080/datasets
	public String setDictionary(HttpServletRequest request, @PathVariable String dict_name,@RequestBody DictionaryBean dictionary ) throws NoResposneException, EntityNotFoundException, SQLException, ForbiddenException{
		
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		
		
		if(!userIsAdmin(username))
			throw new ForbiddenException("You don't have access to this web service");
			
		if(dictionary.getName()== null)
			throw new SQLException("Name is required field");
		
		if(dictionary.getName().length() > 64)
			throw new SQLException("Name length greater than the allowed length(64)");
		
		if(dictionary.getUrl()!= null && dictionary.getUrl().length() > 256)
			throw new SQLException("URL length greater than the allowed length(257)");

		if(dictionary.getDescription() != null && dictionary.getDescription().length() > 1000)
			throw new SQLException("Description length greater than the allowed length(1000)");
		
		
		if(dictionary.getNamespace()!= null && dictionary.getNamespace().length() > 1000)
			throw new SQLException("Namespace length greater than the allowed length(1000)");
		
		if(dictionary.getLinkPattern()!= null && dictionary.getLinkPattern().length() > 256)
			throw new SQLException("Link Pattern length greater than the allowed length(256)");
		
		if(dictionary.getAbbreviation()!= null && dictionary.getAbbreviation().length() > 16)
			throw new SQLException("Abbreviation length greater than the allowed length(16)");
		
		if(dict_name.equals("keywords"))
		{
			Keyword keyword = new Keyword(dictionary.getName(), dictionary.getAbbreviation(), dictionary.getUrl());
			if(keywordDAO.findAllByName(dictionary.getName()) != null)
				throw new SQLException("Keyword with same name already exists");
			
			
			
			keywordDAO.save(keyword);
			return "{\n\t message: success \n}";
		}
		

		else if(dict_name.equals("funding_source"))
		{
			FundingSource fundingSource = new FundingSource(dictionary.getName(), dictionary.getAbbreviation(), dictionary.getUrl());
			if(fundingSourcetDAO.findAllByName(dictionary.getName()) != null)
				throw new SQLException("Funding Source with same name already exists");
			
			fundingSourcetDAO.save(fundingSource);
			return "{\n\t message: success \n}";
		}
		

		else if(dict_name.equals("sample_descriptors"))
		{
			SampleDescriptor sampleDescriptor = new SampleDescriptor(dictionary.getName(),dictionary.getDescription(), dictionary.getNamespace(), dictionary.getUrl(), dictionary.getLinkPattern());;
			
			if(sampleDescriptorDAO.findAllByName(dictionary.getName()) != null)
				throw new SQLException("Sample Descriptor with same name already exists");
			
			
			sampleDescriptorDAO.save(sampleDescriptor);
			return "{\n\t message: success \n}";
		}
		else if(dict_name.equals("experiment_type")) {
			ExperimentType experimentType = new ExperimentType(dictionary.getName(),dictionary.getDescription(), dictionary.getUrl());
		
			if(experimentTypeDAO.findAllByName(dictionary.getName()).size() > 0)
				throw new SQLException("Experiment Type with same name already exists");
			
			experimentTypeDAO.save(experimentType);
			
			return "{\n\t message: success \n}";
		}
		
		
		throw new EntityNotFoundException("Wrong dictionary name");
		
	}
	
	
	@ApiOperation(value = "	Delete a dictionary entry. Allowed Dictionary Names : 'keywords', 'experiment_type','funding_source', 'sample_descriptors' ", response = Object.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success")})
	@CrossOrigin
	@DeleteMapping(value = "/dictionary/{dict_name}/{id}", produces = "application/json")
	// http://localhost:8080/datasets
	public String delete(HttpServletRequest request, @PathVariable String dict_name, @PathVariable Long id) throws SQLException, EntityNotFoundException, ForbiddenException{
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		
		if(!userIsAdmin(username))
			throw new ForbiddenException("You don't have access to this web service");
		
		if(dict_name.equals("keywords")) {
			if(keywordDAO.findById(id).orElse(null) == null)
				throw new SQLException("Keyword id does not exists");
			
			if(datasetToKeywordDAO.findKeywordPresent(id).size() > 0)
				throw new SQLException("Keyword cannot be deleted. Its been assigned to one or more dataset");
			
			keywordDAO.deleteById(id);
			return "{\n\t message: success \n}";
		}
			
		

		else if(dict_name.equals("funding_source")) {
			if(fundingSourcetDAO.findById(id).orElse(null) == null)
				throw new SQLException("Funding source id does not exists");
			
			if(fundingGrantDAO.findByFundingSouce(id).size() > 0)
				throw new SQLException("Funding source cannot be deleted. Its been assigned to one or more dataset");
			
			fundingSourcetDAO.deleteById(id);
			return "{\n\t message: success \n}";
		}
			

		else if(dict_name.equals("sample_descriptors")) {
			if(sampleDescriptorDAO.findSampleDescriptorById(id) == null)
				throw new SQLException("Sample Descriptors id does not exists");
			
			if(sampleToSampleDescriptorDAO.findBySampleDescriptorId(id).size() > 0)
				throw new SQLException("Sample Descriptor cannot be deleted. Its been assigned to one or more sample");
			sampleDescriptorDAO.deleteById(id);
			return "{\n\t message: success \n}";
		}
		else if(dict_name.equals("experiment_type")) {
			if(experimentTypeDAO.findById(id).orElse(null) == null)
				throw new SQLException("Experiment Type id does not exists");
			
			if(datasetToExperimentTypeDAO.findByExperimentType(id).size() > 0)
				throw new SQLException("Experiment Type cannot be deleted. Its been assigned to one or more sample");
		
			experimentTypeDAO.deleteById(id);
			return "{\n\t message: success \n}";
		}
			
		
		
		throw new EntityNotFoundException("Wrong dictionary name");
		
		
	}
	

}



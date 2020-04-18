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

import edu.uga.ccrc.dao.FundingSourceDAO;
import edu.uga.ccrc.dao.KeywordDAO;
import edu.uga.ccrc.dao.SampleDescriptorDAO;
import edu.uga.ccrc.entity.ExperimentType;
import edu.uga.ccrc.entity.FundingSource;
import edu.uga.ccrc.entity.Keyword;
import edu.uga.ccrc.entity.SampleDescriptor;
import edu.uga.ccrc.exception.EntityNotFoundException;
import edu.uga.ccrc.exception.NoResponeException;
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
	
	
	@ApiOperation(value = "View a list of dictionary valyes. Allowed Dictionary Names : 'keywords', 'funding_source', 'sample_descriptors' ", response = Object.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success")})
	@CrossOrigin
	@GetMapping(value = "/getDictionary/{dict_name}", produces = "application/json")
	// http://localhost:8080/datasets
	public Object getDictionart(@PathVariable String dict_name ) throws NoResponeException, EntityNotFoundException{
		
		if(dict_name.equals("keywords"))
			return keywordDAO.findAll();
		

		else if(dict_name.equals("funding_source"))
			return fundingSourcetDAO.findAll();
		

		else if(dict_name.equals("sample_descriptors"))
			return sampleDescriptorDAO.findAll();
		
		
		throw new EntityNotFoundException("Wrong dictionary name");
		
	}
	
	
	@ApiOperation(value = "	Add a value in dictionary. Allowed Dictionary Names : 'keywords', 'funding_source', 'sample_descriptors' ", response = Object.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success")})
	@CrossOrigin
	@PostMapping(value = "/dictionary/{dict_name}", produces = "application/json")
	// http://localhost:8080/datasets
	public String setDictionary(@PathVariable String dict_name,@RequestBody DictionaryBean dictionary ) throws NoResponeException, EntityNotFoundException, SQLException{
		
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
		
		
		throw new EntityNotFoundException("Wrong dictionary name");
		
	}
	
	
	@ApiOperation(value = "	Add a value in dictionary. Allowed Dictionary Names : 'keywords', 'funding_source', 'sample_descriptors' ", response = Object.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success")})
	@CrossOrigin
	@DeleteMapping(value = "/dictionary/{dict_name}/{id}", produces = "application/json")
	// http://localhost:8080/datasets
	public String delete(@PathVariable String dict_name, @PathVariable Long id) throws SQLException, EntityNotFoundException{
		
		if(dict_name.equals("keywords")) {
			if(keywordDAO.findById(id).orElse(null) == null)
				throw new SQLException("Keyword id does not exists");
			
			keywordDAO.deleteById(id);
			return "{\n\t message: success \n}";
		}
			
		

		else if(dict_name.equals("funding_source")) {
			if(fundingSourcetDAO.findById(id).orElse(null) == null)
				throw new SQLException("Funding source id does not exists");
			
			fundingSourcetDAO.deleteById(id);
			return "{\n\t message: success \n}";
		}
			

		else if(dict_name.equals("sample_descriptors")) {
			if(sampleDescriptorDAO.findSampleDescriptorById(id) == null)
				throw new SQLException("Sample Descriptors id does not exists");
			
			sampleDescriptorDAO.deleteById(id);
			return "{\n\t message: success \n}";
		}
			
		
		
		throw new EntityNotFoundException("Wrong dictionary name");
		
		
	}
	

}



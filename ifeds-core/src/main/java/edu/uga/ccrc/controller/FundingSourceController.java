package edu.uga.ccrc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.dao.FundingSourceDAO;
import edu.uga.ccrc.entity.ExperimentType;
import edu.uga.ccrc.entity.FundingSource;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
public class FundingSourceController {
	

	@Autowired
	FundingSourceDAO fundingSourcetDAO;
	
	@ApiOperation(value = "View a list of funding sources", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success")})
	@CrossOrigin
	@GetMapping(value = "/funding_sources", produces = "application/json")
	public Iterable<FundingSource> getListOfExperimentType(HttpServletRequest request){
		
		
		return fundingSourcetDAO.findAll();
		
		
		
	}

}

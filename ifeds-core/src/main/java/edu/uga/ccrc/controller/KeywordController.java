package edu.uga.ccrc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.dao.KeywordDAO;

import edu.uga.ccrc.entity.Keyword;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
public class KeywordController {
	
	@Autowired
	KeywordDAO keywordDAO; 
	
	
	@ApiOperation(value = "View a list of keywords", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success")})
	@CrossOrigin
	@GetMapping(value = "/keywords", produces = "application/json")
	public Iterable<Keyword> getListOfExperimentType(HttpServletRequest request){
		
		
		return keywordDAO.findAll();
		
		
		
	}

}

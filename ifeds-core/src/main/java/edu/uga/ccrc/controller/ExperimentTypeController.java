package edu.uga.ccrc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.dao.ExperimentTypeDAO;
import edu.uga.ccrc.entity.ExperimentType;
import edu.uga.ccrc.view.bean.DatasetBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
public class ExperimentTypeController {
	
	@Autowired
	ExperimentTypeDAO experimentTypeDAO;
	
	@ApiOperation(value = "View a list of experiment types", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success")})
	@CrossOrigin
	@GetMapping(value = "/experiment_types", produces = "application/json")
	public Iterable<ExperimentType> getListOfExperimentType(HttpServletRequest request){
		
		
		return experimentTypeDAO.findAll();
		
		
		
	}

}

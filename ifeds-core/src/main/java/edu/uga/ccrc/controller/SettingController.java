package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.dao.SampleTypeDAO;
import edu.uga.ccrc.dao.SettingsDAO;
import edu.uga.ccrc.entity.Settings;
import edu.uga.ccrc.exception.NoResposneException;
import edu.uga.ccrc.view.bean.SampleTypeBean;
import edu.uga.ccrc.view.bean.SettingsBean;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
public class SettingController {

	@Autowired
	SettingsDAO settingsDAO;
	
	final static Logger log = LoggerFactory.getLogger(SampleTypeController.class);
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/init", produces="application/json")
	@ApiOperation(value = "Init WebService", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Already initialised"),
			@ApiResponse(code = 404, message = "Error") })
	public String init(@PathVariable String key, @PathVariable String value) throws NoResposneException{
		log.info("In init web service");
		//1. Check if key already exists. If exists, then flush it
		if(settingsDAO.existsByKey(key) != null) {
			settingsDAO.deleteByKey(key);
		}
		//2. If key doesn't exists. Then add the new entry in db
		Settings setting = new Settings(key, value);
		try {
			settingsDAO.save(setting);
		}catch(Exception e) {
			throw new NoResposneException("Cannot save the setting");
		}
		return "Success"; 
	}
	/*
	 * get list of settings
	 * 
	 * */

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/get_settings", produces="application/json")
	@ApiOperation(value = "Get settings", response = SettingsBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "List of settings in key-value pair"),
			@ApiResponse(code = 404, message = "Error") })
	public List<SettingsBean> get_settings() throws NoResposneException{
		List<SettingsBean> resultBean = new ArrayList<>();
		log.info("Get settings");
		for(Settings setting : settingsDAO.findAll()) {
			SettingsBean settingBean = new SettingsBean();
			settingBean.setKey(setting.getKey());
			settingBean.setValue(setting.getValue());
			resultBean.add(settingBean);
		}
		return resultBean;
	}
}

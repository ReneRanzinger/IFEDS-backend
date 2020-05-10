package edu.uga.ccrc.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.PermissionsDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.entity.Permissions;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.exception.EntityNotFoundException;
import edu.uga.ccrc.exception.ForbiddenException;
import edu.uga.ccrc.exception.NoResposneException;
import edu.uga.ccrc.view.bean.PermissionsBean;
import edu.uga.ccrc.view.bean.ProviderBean;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
public class PermissionsController {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	PermissionsDAO permissionsDAO;
	
	
	@Autowired
	ProviderDAO providerDao;
	
	public boolean userIsAdmin(String username) {
		Provider provider = providerDao.findByUsername(username);
		Permissions p = permissionsDAO.findByProviderId(provider.getProviderId());

		if(p.getPermission_level().equals("admin"))
			return true;
		
		return false;
	}
	
	

	@RequestMapping(method = RequestMethod.GET, value = "/permissions", produces="application/json")
	@ApiOperation(value = "Get Permissions of user", response = PermissionsBean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Accessing the Permissions Info is forbidden"),
			 })
	public List<PermissionsBean> findPermissions(HttpServletRequest request, HttpServletResponse response) throws ForbiddenException {
		System.out.println("Retrieving provider information : findByUsername() ");
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		HashSet<Long> providerIdsWithPermissions = new HashSet<Long>();
		List<PermissionsBean> result = new ArrayList<PermissionsBean>();
		
		if(!userIsAdmin(username)) 
			throw new ForbiddenException("You don't have access to this web service");

		for(Permissions permission : permissionsDAO.findAll()) {
			if(username.contentEquals(permission.getProvider().getUsername())) {
				providerIdsWithPermissions.add(permission.getPermissions_id());
				continue;
			}
			PermissionsBean pb = new PermissionsBean();
			pb.setEmail(permission.getProvider().getEmail());
			pb.setUsername(permission.getProvider().getUsername());
			pb.setPermission_level(permission.getPermission_level());
			pb.setProvider_id(permission.getProvider().getProviderId());
			pb.setActive(permission.getProvider().isActive());
			providerIdsWithPermissions.add(pb.getProvider_id());
			result.add(pb);
		}
		
		for(Provider provider : providerDao.findAll()) {
			if(providerIdsWithPermissions.contains(provider.getProviderId()))
				continue;
			PermissionsBean pb = new PermissionsBean();
			pb.setEmail(provider.getEmail());
			pb.setUsername(provider.getUsername());
			pb.setActive(provider.isActive());
			pb.setProvider_id(provider.getProviderId());
			pb.setPermission_level("default");
			result.add(pb);
		}
		
		return result;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/permissions/{action}/{id}", produces="application/json")
	@ApiOperation(value = "Update Permissions of user", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "If update causes no admin user in permissions table"),
			@ApiResponse(code = 404, message = "Wrong action mentioned. Allowed actions are (promote, demote, enable, disable)")
			 })
	public String updatePermissions(HttpServletRequest request, HttpServletResponse response, @PathVariable("action") String action, @PathVariable("id") Long provider_id) throws ForbiddenException, EntityNotFoundException, NoResposneException {
		/*
		 * Prmote : We create the entry in Permissions Table as admin
		 * Demote : We delete the entry in Permissions Table
		 * Enable : We set the active flag in Provider Table
		 * Disable : We reset the active flag in Provider Table
		 * 
		 * */
		System.out.println("Updating provider permission_level : updatePermissions() ");
		final String requestTokenHeader = request.getHeader("Authorization");
		String jwtToken = requestTokenHeader.substring(7);
		String adminUsername = jwtTokenUtil.getUsernameFromToken(jwtToken);
		Provider provider = providerDao.findByProviderId(provider_id);
		Permissions permission = permissionsDAO.findByProviderId(provider_id);
		
		if(!userIsAdmin(adminUsername)) 
			throw new ForbiddenException("You don't have access to this web service");
				
		if(permission == null) {
			 permission = new Permissions();
			 permission.setProvider(provider);
		}
		//incase of demote, make sure that there is atleast one admin the permission table
		if(action.equals("demote")) {
			if(!provider.isActive())
				throw new ForbiddenException("You cannot change the permissions of disabled user");
			
			if(permission.getPermission_level() == null || !permission.getPermission_level().equals("admin"))
				throw new ForbiddenException("You cannot demote non-admin user");
			
			if(permissionsDAO.getAdminOtherThanThisId(provider_id) != null)
				permissionsDAO.deleteByPermissionId(permission.getPermissions_id());
			else
				throw new ForbiddenException("Atleast one user should exist with admin permission level");
		}
		//check if promote, then make the provider admin
		else if(action.equals("promote")) {
			if(!provider.isActive())
				throw new ForbiddenException("You cannot change permissions of disabled user");
			permission.setPermission_level("admin");
		}
		//check if enable, then make the provider active flag - enable
		else if(action.equals("enable"))
			provider.setActive(true);
		//check if disable, then first make sure, that there is atleast on enable admin and then make the provider disable
		else if(action.equals("disable")) {
			if(permissionsDAO.getAdminOtherThanThisId(provider_id) != null) {
				provider.setActive(false);
				if(permission.getPermissions_id() != null && permissionsDAO.findById(permission.getPermissions_id()).isPresent())
					permissionsDAO.deleteByPermissionId(permission.getPermissions_id());
			}
		
			else
				throw new ForbiddenException("You cannot disable admin user if only one admin exists");
		}
		else
			throw new EntityNotFoundException("Wrong action");
		try {
			if(action.equals("promote")) //if promote action, then update permissions table
				permissionsDAO.save(permission);
			else	//else update provider table
				providerDao.save(provider); 
			return "{\n\t message: User permission successfuly updated \n}";
		}catch(Exception e){
			throw new NoResposneException("Error occured while updating to database ERROR:"+e.getMessage());
		}
		
	}
	
	
	

}

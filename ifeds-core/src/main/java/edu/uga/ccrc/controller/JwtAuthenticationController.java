package edu.uga.ccrc.controller;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uga.ccrc.config.JwtTokenUtil;
import edu.uga.ccrc.dao.PermissionsDAO;
import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.entity.Permissions;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.exception.ForbiddenException;
import edu.uga.ccrc.service.JwtUserDetailsService;
import edu.uga.ccrc.view.bean.JwtRequestBean;
import edu.uga.ccrc.view.bean.JwtResponseBean;
import io.jsonwebtoken.MalformedJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	PermissionsDAO permissionsDAO;
	
	
	@Autowired
	ProviderDAO providerDao;
	

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@ApiOperation(value = "Authenicate", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 403, message = "Authentication not allowed"),
			@ApiResponse(code = 404, message = "Authentication failed") })
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestBean authenticationRequest) throws Exception {
		
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
			Provider provider = providerDao.findByUsername(authenticationRequest.getUsername());
			String permission_level = "default";
			
			if(permissionsDAO.findByProviderId(provider.getProviderId()) != null)
				permission_level = permissionsDAO.findByProviderId(provider.getProviderId()).getPermission_level();
			
			return ResponseEntity.ok(new JwtResponseBean(token, permission_level));
	}
	
	private void authenticate(String username, String password) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		}catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}catch (MalformedJwtException e) {
			throw new ForbiddenException("Please login again");
		}
}
}
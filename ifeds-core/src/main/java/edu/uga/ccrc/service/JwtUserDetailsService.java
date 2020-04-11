package edu.uga.ccrc.service;

import java.util.ArrayList;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.uga.ccrc.dao.ProviderDAO;
import edu.uga.ccrc.entity.Provider;
import edu.uga.ccrc.exception.NoResponeException;
import edu.uga.ccrc.view.bean.ProviderBean;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private ProviderDAO providerDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {
		
		Provider user = providerDao.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
		if(!user.isActive())
			throw new UsernameNotFoundException("Username "+username+" not active. Please contact admin ");
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());

	
	}
}
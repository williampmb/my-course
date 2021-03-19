package com.login.service.mycourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.login.service.mycourse.auth.AppUserDao;

@Service
public class AppUserService implements UserDetailsService {
	
	private final AppUserDao appUserDao;
	
	@Autowired
	public AppUserService(@Qualifier("fake") AppUserDao appUserDao) {
		this.appUserDao = appUserDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return appUserDao.selectAppUserByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not foud. "+ username));
	}

	
}

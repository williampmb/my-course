package com.login.service.mycourse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.login.service.mycourse.auth.AppUser;
import com.login.service.mycourse.auth.AppUserDao;
import com.login.service.mycourse.security.UserRole;

@Repository("fake")
public class FakeAppUserDaoService implements AppUserDao {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public FakeAppUserDaoService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<AppUser> selectAppUserByUsername(String username) {
		return getAppUsers().stream().filter(user -> username.equals(user.getUsername())).findFirst();
	}

	private List<AppUser> getAppUsers(){
		List<AppUser> appUsers = Lists.newArrayList(
				new AppUser(
						UserRole.STUDENT.getGrantedAuthorities(),
						"ana",
						passwordEncoder.encode("123"),
						true,
						true,
						true,
						true),
				new AppUser(
						UserRole.ADMIN.getGrantedAuthorities(),
						"linda",
						passwordEncoder.encode("123"),
						true,
						true,
						true,
						true),
				new AppUser(
						UserRole.ADMINTRAINEE.getGrantedAuthorities(),
						"tom",
						passwordEncoder.encode("123"),
						true,
						true,
						true,
						true));
		
		return appUsers;
	}

}

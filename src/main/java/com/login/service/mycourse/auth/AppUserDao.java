package com.login.service.mycourse.auth;

import java.util.Optional;

public interface AppUserDao {
	public Optional<AppUser> selectAppUserByUsername(String username);
}

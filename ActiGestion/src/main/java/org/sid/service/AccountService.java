package org.sid.service;

import org.sid.entity.AppRoles;
import org.sid.entity.AppUser;

public interface AccountService {
	public AppUser saveUser(AppUser user);

	public AppRoles saveRole(AppRoles user);

	public void addRolesToUser(String username, String roleName);

	public AppUser findUserByUsername(String username);

}

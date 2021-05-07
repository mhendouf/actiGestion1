
package org.sid.service;

import org.sid.entity.AppRoles;
import org.sid.entity.AppUser;
import org.sid.repository.RolesRepository;
import org.sid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImplementation implements AccountService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolesRepository rolesRepository;

	@Override
	public AppUser saveUser(AppUser user) {
		String hashpwd = bCryptPasswordEncoder.encode ( user.getPassword ( ) );
		user.setPassword ( hashpwd );
		return userRepository.save ( user );
	}

	@Override
	public AppRoles saveRole(AppRoles rolename) {
		return rolesRepository.save ( rolename );
	}

	@Override
	public void addRolesToUser(String username, String roleName) {
		AppRoles role = rolesRepository.findByRoleName ( roleName );
		AppUser user = userRepository.findByUsername ( username );
		user.getRoles ( ).add ( role );

	}

	@Override
	public AppUser findUserByUsername(String username) {
		return userRepository.findByUsername ( username );
	}

}

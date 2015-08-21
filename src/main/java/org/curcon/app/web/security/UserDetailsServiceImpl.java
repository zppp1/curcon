package org.curcon.app.web.security;

import java.util.HashSet;
import java.util.Set;

import org.curcon.app.data.UserRepository;
import org.curcon.app.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final String DEFAULT_ROLE = "ROLE_USER";

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(DEFAULT_ROLE));
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
	}

}

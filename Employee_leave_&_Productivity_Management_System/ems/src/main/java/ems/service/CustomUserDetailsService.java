package ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ems.entity.User;
import ems.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {



    User user = repo.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("LOGIN ROLE = " + user.getRole());

        String role = user.getRole();

        

if (role == null) {
    role = "EMPLOYEE"; // default
}

    return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .authorities("ROLE_" + role) // IMPORTANT
        .build();
}
   

}
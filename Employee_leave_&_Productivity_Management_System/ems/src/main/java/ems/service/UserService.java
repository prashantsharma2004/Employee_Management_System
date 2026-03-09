package ems.service;

import ems.entity.User;

public interface UserService {

     User registerUser(User user);
     

    User login(String email, String password); //  New
    
}

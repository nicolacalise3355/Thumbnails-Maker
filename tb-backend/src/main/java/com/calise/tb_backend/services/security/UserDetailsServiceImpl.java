package com.calise.tb_backend.services.security;

import com.calise.tb_backend.dao.UserDAO;
import com.calise.tb_backend.models.entities.User;
import com.calise.tb_backend.models.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws BadCredentialsException {
        Optional<User> user = userDAO.findUserByUsername(username);

        if(user.isPresent()) {
            return UserDetailsImpl.build(user.get());
        }
        return null;
    }
}

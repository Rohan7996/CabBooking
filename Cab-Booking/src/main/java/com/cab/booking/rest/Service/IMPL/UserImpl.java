package com.cab.booking.rest.Service.IMPL;

import com.cab.booking.rest.Dto.User_Details;
import com.cab.booking.rest.Repository.UserRepository;
import com.cab.booking.rest.Service.Declare.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImpl implements User {

    @Autowired
    private UserRepository user_repository;
    @Override
    public User_Details add_Users(User_Details user) {
        return user_repository.save(user);
    }
}

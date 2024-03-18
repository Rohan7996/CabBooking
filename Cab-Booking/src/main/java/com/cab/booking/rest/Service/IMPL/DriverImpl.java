package com.cab.booking.rest.Service.IMPL;

import com.cab.booking.rest.Dto.Driver_Details;
import com.cab.booking.rest.Repository.DriverRepository;
import com.cab.booking.rest.Repository.UserRepository;
import com.cab.booking.rest.Service.Declare.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverImpl implements Driver {
    @Autowired
    private DriverRepository driver_repository;
    @Override
    public Driver_Details add_Driver(Driver_Details driver) {
        return driver_repository.save(driver);
    }
}

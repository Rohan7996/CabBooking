package com.cab.booking.rest.Repository;

import com.cab.booking.rest.Dto.Driver_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver_Details,Long> {
}

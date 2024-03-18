package com.cab.booking.rest.Repository;

import com.cab.booking.rest.Dto.User_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User_Details, Long> {
}

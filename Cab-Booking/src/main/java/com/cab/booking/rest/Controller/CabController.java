package com.cab.booking.rest.Controller;

import com.cab.booking.rest.Dto.Driver_Details;
import com.cab.booking.rest.Dto.User_Details;
import com.cab.booking.rest.Service.Declare.Driver;
import com.cab.booking.rest.Service.Declare.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CabController {
    private List<User_Details> users = new ArrayList<>();
    private List<Driver_Details> drivers = new ArrayList<>();

    @Autowired
    private User userService;
    @Autowired
    private Driver driverService;

    @PostMapping("/user")
    public ResponseEntity<User_Details> addUser(@RequestBody User_Details user) {
        User_Details result = userService.add_Users(user);
        users.add(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/driver")
    public ResponseEntity<Driver_Details> addDriver(@RequestBody Driver_Details driver) {
        Driver_Details result = driverService.add_Driver(driver);
        drivers.add(driver);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find-ride")
    public ResponseEntity<String> findRide(@RequestParam String username, @RequestParam double sourceLat, @RequestParam double sourceLong, @RequestParam double destLat, @RequestParam double destLong) {
        // Find available driver nearest to the user's location
        Driver_Details nearestDriver = findNearestDriver(sourceLat, sourceLong);
        System.out.println("nearest :"+nearestDriver.getName()+" "+nearestDriver.getVehicleDetails()+" "+nearestDriver.getCurrentLocation().getLatitude());

        if (nearestDriver != null && isWithinDistance(nearestDriver.getCurrentLocation().getLatitude(), nearestDriver.getCurrentLocation().getLongitude(), sourceLat, sourceLong, 5)) {
            return ResponseEntity.ok(nearestDriver.getName() + " [Available]");
        } else {
            return ResponseEntity.ok("No ride found");
        }
    }

    @GetMapping("/choose-ride")
    public ResponseEntity<String> chooseRide(@RequestParam String username, @RequestParam String driverName) {
        Driver_Details chosenDriver = findDriverByName(driverName);
        if (chosenDriver != null && chosenDriver.isAvailable()) {
                chooseDriver(chosenDriver);
                return ResponseEntity.ok("Ride with " + chosenDriver.getName() + " chosen successfully.");
        } else {
            return ResponseEntity.ok("Driver not available or does not exist.");
        }
    }
    public void chooseDriver(Driver_Details chosenDriver) {
        chosenDriver.setAvailable(false);
    }
    private Driver_Details findDriverByName(String driverName) {
        for (Driver_Details driver : drivers) {
            if (driver.getName().equals(driverName)) {
                return driver;
            }
        }
        return null;
    }

    private boolean isWithinDistance(double driverLat, double driverLong, double sourceLat, double sourceLong, double threshold) {
        double distance = calculateDistance(driverLat, driverLong, sourceLat, sourceLong);
        System.out.println("distance: "+distance);
        return distance <= threshold;
    }

    private Driver_Details findNearestDriver(double sourceLat, double sourceLong) {
        Driver_Details nearestDriver = null;
        double minDistance = Double.MAX_VALUE;

        for (Driver_Details driver : drivers) {
            double distance = calculateDistance(driver.getCurrentLocation().getLatitude(), driver.getCurrentLocation().getLongitude(), sourceLat, sourceLong);
            if (distance < minDistance) {
                nearestDriver = driver;
                minDistance = distance;
            }
        }
        return nearestDriver;
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2));
    }
}
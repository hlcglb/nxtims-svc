package com.hyundaiuni.nxtims.controller.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.service.app.UsersService;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
        Assert.notNull(userId, "userId must not be null");
        
        try {
            return new ResponseEntity<>(usersService.getUser(userId), HttpStatus.OK);
        }
        catch(Exception e) {
            Map<String,String> error = new HashMap<>();
            error.put("CODE", "999");
            error.put("MESSAGE", e.getMessage());
            
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/menus/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMenuByUserId(@PathVariable("userId") String userId) {
        Assert.notNull(userId, "userId must not be null");
        
        try {
            return new ResponseEntity<>(usersService.getMenuByUserId(userId), HttpStatus.OK);
        }
        catch(Exception e) {
            Map<String,String> error = new HashMap<>();
            error.put("CODE", "999");
            error.put("MESSAGE", e.getMessage());
            
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
    }    
}

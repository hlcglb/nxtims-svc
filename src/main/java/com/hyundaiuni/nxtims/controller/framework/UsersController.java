package com.hyundaiuni.nxtims.controller.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.domain.framework.User;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.service.framework.UsersService;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> get(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(usersService.getUser(id), HttpStatus.OK);
        }
        catch(ServiceException e) {
            return new ResponseEntity<>(new User(),HttpStatus.BAD_REQUEST);
        }
    }
}

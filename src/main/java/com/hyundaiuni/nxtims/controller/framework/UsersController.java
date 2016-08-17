package com.hyundaiuni.nxtims.controller.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.service.framework.UsersService;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public com.hyundaiuni.nxtims.domain.framework.User get(@PathVariable("id") String id) {
        return usersService.getUser(id);
    }
}

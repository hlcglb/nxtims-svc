package com.hyundaiuni.nxtims.controller.app;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.service.app.UserService;

@RestController
@RequestMapping("/api/v1/app/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
        Assert.notNull(userId, "userId must not be null");

        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/menus/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMenuByUserId(@PathVariable("userId") String userId) {
        Assert.notNull(userId, "userId must not be null");

        return new ResponseEntity<>(userService.getMenuByUserId(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/onAuthenticationSuccess", method = RequestMethod.POST)
    public ResponseEntity<?> onAuthenticationSuccess(@RequestBody Map<String, Object> request) {
        Assert.notNull(request, "request must not be null");

        String userId = MapUtils.getString(request, "USER_ID");
        String sessionId = MapUtils.getString(request, "SESSION_ID");
        String accessIp = MapUtils.getString(request, "ACCESS_IP");

        userService.onAuthenticationSuccess(userId, sessionId, accessIp);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/onAuthenticationFailure", method = RequestMethod.POST)
    public ResponseEntity<?> onAuthenticationFailure(@RequestBody Map<String, Object> request) {
        Assert.notNull(request, "request must not be null");

        String userId = MapUtils.getString(request, "USER_ID");

        userService.onAuthenticationFailure(userId);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/onLogout", method = RequestMethod.POST)
    public ResponseEntity<?> onLogout(@RequestBody Map<String, Object> request) {
        Assert.notNull(request, "request must not be null");

        String userId = MapUtils.getString(request, "USER_ID");
        String sessionId = MapUtils.getString(request, "SESSION_ID");

        userService.onLogout(userId, sessionId);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}

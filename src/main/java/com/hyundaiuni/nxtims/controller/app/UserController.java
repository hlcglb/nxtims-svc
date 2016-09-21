package com.hyundaiuni.nxtims.controller.app;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.domain.app.User;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.service.app.UserService;
import com.hyundaiuni.nxtims.util.WebUtils;

@RestController
@RequestMapping("/api/v1/app/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @RequestMapping(params = "inquiry=getUserListByParam", method = RequestMethod.GET)
    public ResponseEntity<?> getUserListByParam(@RequestParam("q") String query, @RequestParam("offset") int offset,
        @RequestParam("limit") int limit) {
        Assert.notNull(query, "query must not be null");
        Assert.notNull(offset, "offset must not be null");
        Assert.notNull(limit, "limit must not be null");

        try {
            Map<String, Object> parameter = WebUtils.requestParamtoMap(query, ',', '=', "UTF-8");

            return new ResponseEntity<>(userService.getUserListByParam(parameter, offset, limit),
                HttpStatus.OK);
        }
        catch(UnsupportedEncodingException e) {
            throw new ServiceException("ENCODE_NOT_SUPPORTED", e.getMessage(), null, e);
        }
    }    

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
        Assert.notNull(userId, "userId must not be null");

        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insertUser(@RequestBody User user) {
        Assert.notNull(user, "user must not be null");
        
        return new ResponseEntity<>(userService.insertUser(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("userId") String userId,
        @RequestBody User user) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(user, "user must not be null");
        
        userService.updateUser(userId, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {
        Assert.notNull(userId, "userId must not be null");
        
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.OK);
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

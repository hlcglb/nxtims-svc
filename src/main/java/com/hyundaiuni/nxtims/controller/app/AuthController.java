package com.hyundaiuni.nxtims.controller.app;

import java.io.UnsupportedEncodingException;
import java.util.Map;

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

import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.service.app.AuthService;
import com.hyundaiuni.nxtims.util.WebUtils;

@RestController
@RequestMapping("/api/v1/app/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @RequestMapping(params = "inquiry=getAuthListByParam", method = RequestMethod.GET)
    public ResponseEntity<?> getAuthListByParam(@RequestParam("q") String query, @RequestParam("offset") int offset,
        @RequestParam("limit") int limit) {
        Assert.notNull(query, "query must not be null");
        Assert.notNull(offset, "offset must not be null");
        Assert.notNull(limit, "limit must not be null");

        try {
            Map<String, Object> parameter = WebUtils.requestParamtoMap(query, ',', '=', "UTF-8");

            return new ResponseEntity<>(authService.getAuthListByParam(parameter, offset, limit), HttpStatus.OK);
        }
        catch(UnsupportedEncodingException e) {
            throw new ServiceException("ENCODE_NOT_SUPPORTED", e.getMessage(), null, e);
        }
    }

    @RequestMapping(params = "inquiry=getAuthResourceListByAuthId", method = RequestMethod.GET)
    public ResponseEntity<?> getAuthResourceListByAuthId(@RequestParam("authId") String authId) {
        Assert.notNull(authId, "authId must not be null");

        return new ResponseEntity<>(authService.getAuthResourceListByAuthId(authId), HttpStatus.OK);
    }
    
    @RequestMapping(params = "inquiry=getNotExistsAuthResourceListByAuthId", method = RequestMethod.GET)
    public ResponseEntity<?> getNotExistsAuthResourceListByAuthId(@RequestParam("authId") String authId) {
        Assert.notNull(authId, "authId must not be null");

        return new ResponseEntity<>(authService.getNotExistsAuthResourceListByAuthId(authId), HttpStatus.OK);
    }    

    @RequestMapping(value = "/{authId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAuth(@PathVariable("authId") String authId) {
        Assert.notNull(authId, "authId must not be null");

        return new ResponseEntity<>(authService.getAuth(authId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insertAuth(@RequestBody Auth auth) {
        return new ResponseEntity<>(authService.insertAuth(auth), HttpStatus.OK);
    }

    @RequestMapping(value = "/{authId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAuth(@PathVariable("authId") String authId, @RequestBody Auth auth) {
        authService.updateAuth(authId, auth);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{authId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAuth(@PathVariable("authId") String authId) {
        authService.deleteAuth(authId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

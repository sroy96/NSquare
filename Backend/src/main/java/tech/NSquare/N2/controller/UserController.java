package tech.NSquare.N2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.NSquare.N2.constants.URLConstants;
import tech.NSquare.N2.models.LoginResponse;
import tech.NSquare.N2.models.User;
import tech.NSquare.N2.models.enums.GeneralErrorEnum;
import tech.NSquare.N2.serviceImpl.UserServiceImpl;
import tech.NSquare.N2.util.NsquareException;

import static tech.NSquare.N2.models.enums.GeneralErrorEnum.AUTH_TOKEN_NOT_PRESENT;


@RestController
@RequestMapping((URLConstants.BASE_URL))
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;


    @GetMapping(URLConstants.LOG_IN_URL)
    public ResponseEntity<LoginResponse> isAuthorized(@RequestHeader("auth_token") String authToken) {
        LoginResponse loginResponseResponse = null;
        if (authToken != null) {
            loginResponseResponse = userServiceImpl.newLogin(authToken);
            LOG.info(GeneralErrorEnum.SUCCESS.getErrorCode().concat(":") + GeneralErrorEnum.SUCCESS.getErrorMessage());
        } else {
            LOG.error(String.valueOf(new NsquareException(AUTH_TOKEN_NOT_PRESENT.getErrorCode(), AUTH_TOKEN_NOT_PRESENT.getErrorMessage())));

        }
        return ResponseEntity.ok().body(loginResponseResponse);
    }

    @PostMapping(URLConstants.NEW_USER)
    public ResponseEntity<User> addNewUser(@RequestBody User user){
        User users =userServiceImpl.newUserRegister(user);
        return ResponseEntity.ok().body(users);
    }
}




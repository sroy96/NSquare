package tech.NSquare.N2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import tech.NSquare.N2.configuration.RedisConfig;
import tech.NSquare.N2.constants.URLConstants;
import tech.NSquare.N2.models.ForgetPassword;
import tech.NSquare.N2.models.LoginResponse;
import tech.NSquare.N2.models.User;
import tech.NSquare.N2.models.enums.GeneralErrorEnum;
import tech.NSquare.N2.serviceImpl.UserServiceImpl;
import tech.NSquare.N2.util.NsquareException;

import java.util.UUID;

import static tech.NSquare.N2.constants.CommonContants.REDIS_H_LOGIN;
import static tech.NSquare.N2.models.enums.GeneralErrorEnum.AUTH_TOKEN_NOT_PRESENT;


@RestController
@RequestMapping((URLConstants.BASE_URL))
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    RedisConfig redisConfig;


    /**
     * AuthToken From Backend name : "authSet"
     * @param authToken
     * @return
     */
    @GetMapping(URLConstants.LOG_IN_URL)
    public ResponseEntity<LoginResponse> isAuthorized(@RequestHeader("authToken") String authToken) {
        LoginResponse loginResponseResponse = null;
        if (authToken != null) {
            loginResponseResponse = userServiceImpl.newLogin(authToken);
        } else {
            LOG.error(String.valueOf(new NsquareException(AUTH_TOKEN_NOT_PRESENT.getErrorCode(), AUTH_TOKEN_NOT_PRESENT.getErrorMessage())));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        if(!ObjectUtils.isEmpty(loginResponseResponse)){
            LOG.info(GeneralErrorEnum.SUCCESS.getErrorCode().concat(" : ") + GeneralErrorEnum.SUCCESS.getErrorMessage());
            String uuid= UUID.randomUUID().toString();
            assert false;
            redisConfig.redisTemplate().opsForHash().put(REDIS_H_LOGIN,uuid,authToken);
            return ResponseEntity.ok().header("authset",uuid).body(loginResponseResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

    @PostMapping(URLConstants.NEW_USER)
    public ResponseEntity<User> addNewUser(@RequestBody User user){
        User users =userServiceImpl.newUserRegister(user);
        return ResponseEntity.ok().body(users);
    }

    @PostMapping(URLConstants.FORGET_PASSWORD_REQUEST)
    public ResponseEntity<ForgetPassword>newForgetPasswordRequest(@RequestParam String userEmail) throws Exception {
        ForgetPassword forgetPassword = userServiceImpl.newForgetPasswordRequest(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(forgetPassword);
    }

    @PutMapping(URLConstants.CREATE_NEW_PASSWORD)
    public HttpStatus submitNewPassword(@RequestParam("token") String tokenFromEmail){
        return userServiceImpl.successfullyChangePassword(tokenFromEmail);
    }

    @GetMapping(URLConstants.LOGOUT)
    public HttpStatus logout(@RequestHeader("authset") String auth){
        return userServiceImpl.logoutUser(auth);
    }
}




package tech.NSquare.N2.service;

import org.springframework.http.HttpStatus;
import tech.NSquare.N2.models.LoginResponse;
import tech.NSquare.N2.models.User;

public interface userService {

     LoginResponse newLogin(String userAuthToken);

     User newUserRegister(User user);

     HttpStatus logoutUser(String auth);

//     void forgetPassword(String emailAddress);

}

package tech.NSquare.N2.service;

import tech.NSquare.N2.models.LoginResponse;
import tech.NSquare.N2.models.User;

public interface userService {

     LoginResponse newLogin(String userAuthToken);

     User newUserRegister(User user);

}

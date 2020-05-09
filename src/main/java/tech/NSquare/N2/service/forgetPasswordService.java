package tech.NSquare.N2.service;

import org.springframework.http.HttpStatus;
import tech.NSquare.N2.models.ForgetPassword;
import tech.NSquare.N2.models.User;

public interface forgetPasswordService {

    ForgetPassword newForgetPasswordRequest(String userEmail) throws Exception;

    HttpStatus successfullyChangePassword(String userEmail);
}

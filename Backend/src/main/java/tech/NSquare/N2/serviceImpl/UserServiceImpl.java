package tech.NSquare.N2.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.NSquare.N2.models.LoginResponse;
import tech.NSquare.N2.models.Token;
import tech.NSquare.N2.models.User;
import tech.NSquare.N2.models.enums.GeneralErrorEnum;
import tech.NSquare.N2.repository.TokenRepository;
import tech.NSquare.N2.repository.UserRepository;
import tech.NSquare.N2.service.userService;
import tech.NSquare.N2.util.NsquareException;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static tech.NSquare.N2.models.enums.GeneralErrorEnum.EMAIL_ALREADY_EXISTS;
import static tech.NSquare.N2.models.enums.GeneralErrorEnum.USER_REGISTRATION_ERROR;

@Slf4j
@Service
public class UserServiceImpl implements userService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;


    @Override
    public LoginResponse newLogin(String userAuthToken) {

        String[] decodedString = new String(Base64.getDecoder().decode(userAuthToken)).split(":");
        String userNamePasswordCombination = decodedString[0];
        Token userToken = tokenRepository.findByAuthToken(userNamePasswordCombination);
        if (userToken == null) {
            return new LoginResponse();
        }
        Optional<User> userDetails = userRepository.findById(userToken.get_id());
        return new LoginResponse(userDetails.get());
    }

    @Override
    public User newUserRegister(User user) {
        boolean validate =validateUser(user);
        if(validate) {
            try {
               prepareNewUserToOnBoard(user);
            } catch (NsquareException ex) {
                log.error(USER_REGISTRATION_ERROR.getErrorCode()+USER_REGISTRATION_ERROR.getErrorMessage());
            }
        }
        else{
            log.error(EMAIL_ALREADY_EXISTS.getErrorCode()+EMAIL_ALREADY_EXISTS.getErrorMessage());
        }
        return user;
    }

    /**
     * Check if User Email Address is Already Present or not in User Document
     * @param user {@link-> User}
     * @return boolean
     */
    private boolean validateUser(User user) {
        User userByEmail = userRepository.findByEmailAddress(user.getEmailAddress());
        return  !userByEmail.getEmailAddress().equals(user.getEmailAddress());
    }

    private void prepareNewUserToOnBoard(User user) {
        String randomPassword = RandomStringUtils.random(8,true,true);
        String newUserNamePasswordCombination = user.getEmailAddress().concat("@")+randomPassword;
        userRepository.save(user);
        Token token = new Token();
        token.set_id(user.get_id());
        token.setAuthToken(newUserNamePasswordCombination);
        tokenRepository.save(token);
    }
}


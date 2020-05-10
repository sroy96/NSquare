package tech.NSquare.N2.serviceImpl;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tech.NSquare.N2.configuration.RedisConfig;
import tech.NSquare.N2.constants.CommonContants;
import tech.NSquare.N2.models.ForgetPassword;
import tech.NSquare.N2.models.LoginResponse;
import tech.NSquare.N2.models.Token;
import tech.NSquare.N2.models.User;
import tech.NSquare.N2.models.enums.GeneralErrorEnum;
import tech.NSquare.N2.repository.TokenRepository;
import tech.NSquare.N2.repository.UserRepository;
import tech.NSquare.N2.service.forgetPasswordService;
import tech.NSquare.N2.service.userService;
import tech.NSquare.N2.util.ApplicationUtils;
import tech.NSquare.N2.util.GenerateMail;
import tech.NSquare.N2.util.NsquareException;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static tech.NSquare.N2.constants.CommonContants.REDIS_H_LOGIN;
import static tech.NSquare.N2.models.enums.GeneralErrorEnum.*;
import static tech.NSquare.N2.util.ApplicationUtils.getHttpStatus;

@Slf4j
@Service
public class UserServiceImpl implements userService, forgetPasswordService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    GenerateMail generateMail;

    @Autowired
    RedisConfig redisConfig;


    /**
     * Login User that Exist
     * userAuthToken = Base64 UserNamePasswordCombo : Random
     * Delimiter used ":"
     *
     * @param userAuthToken
     * @return
     */
    @Override
    public LoginResponse newLogin(String userAuthToken) {
        byte[] decodedBytes = Base64.getDecoder().decode(userAuthToken);
        String decodedStringforUserNamePasswordCombination = new String(decodedBytes);
        String[] decodedPartedString = decodedStringforUserNamePasswordCombination.split(":");
        String userNamePasswordCombo = decodedPartedString[0];
        String userNamePasswordCombination = Base64.getEncoder().encodeToString(userNamePasswordCombo.getBytes());
        Optional<User> userDetails = Optional.empty();
        Token userToken = tokenRepository.findByAuthToken(userNamePasswordCombination);
        if (ObjectUtils.isEmpty(userToken)) {
            log.error(USER_DOES_NOT_EXIST.getErrorCode() + USER_DOES_NOT_EXIST.getErrorMessage());
        } else {
            userDetails = Optional.ofNullable(userRepository.findById(userToken.get_id()).orElseThrow(
                    () -> new NsquareException(USER_NOT_FOUND.getErrorCode(),
                            USER_NOT_FOUND.getErrorMessage())));
        }
        try {
            if (userDetails.isPresent() && validateUserAccess(userDetails)) {
                return new LoginResponse(userDetails.get());
            } else {
                log.error(USER_ACCESS_REVOKED.getErrorCode().concat(" -:- ").concat(USER_ACCESS_REVOKED.getErrorMessage()));

                throw new NsquareException(USER_ACCESS_REVOKED.getErrorCode(), USER_ACCESS_REVOKED.getErrorMessage());
            }
        } catch (NullPointerException ex) {

            throw new NsquareException(USER_DOES_NOT_EXIST.getErrorCode(), USER_DOES_NOT_EXIST.getErrorMessage());
        }
    }

    /**
     * Check if user is Active or not .
     *
     * @param userDetails
     * @return
     */
    private boolean validateUserAccess(Optional<User> userDetails) {
        return userDetails.map(User::isActive).orElse(false);
    }

    /**
     * New User Creation
     *
     * @param user {@link User}
     * @return User
     */
    @Override
    public User newUserRegister(User user) {
        boolean validate = validateUser(user);
        if (validate) {
            try {
                obBoardNewUser(user);
                log.info("USER_REGISTERED");
            } catch (NsquareException ex) {
                log.error(USER_REGISTRATION_ERROR.getErrorCode() + USER_REGISTRATION_ERROR.getErrorMessage());
                throw new NsquareException(USER_REGISTRATION_ERROR.getErrorCode(), USER_REGISTRATION_ERROR.getErrorMessage());
            }
        } else {
            log.error(EMAIL_ALREADY_EXISTS.getErrorCode().concat(" ") + EMAIL_ALREADY_EXISTS.getErrorMessage());
            throw new NsquareException(EMAIL_ALREADY_EXISTS.getErrorCode(), EMAIL_ALREADY_EXISTS.getErrorMessage());
        }
        return user;
    }

    /**
     * Check if User Email Address is Already Present or not in User Document
     *
     * @param user {@link User}
     * @return boolean
     */
    private boolean validateUser(User user) {
        User userByEmail = userRepository.findByEmailAddress(user.getEmailAddress());
        if (userByEmail == null) {
            return true;
        }
        return !userByEmail.getEmailAddress().equals(user.getEmailAddress());
    }

    /**
     * Prepare Method to On board new User
     * UserNamePasswordCombination = email | password
     *
     * @param user {@link -> User}
     */
    private void obBoardNewUser(User user) {
        String randomPassword = RandomStringUtils.random(8, true, true);
        String newUserNamePasswordCombination = user.getEmailAddress().concat("_") + randomPassword;
        String newUserNamePasswordCombinationbyte = Base64.getEncoder().encodeToString(newUserNamePasswordCombination.getBytes());
        user.setActive(true);
        userRepository.save(user);
        Token token = new Token();
        token.set_id(user.get_id());
        token.setAuthToken(newUserNamePasswordCombinationbyte);
        tokenRepository.save(token);
    }


    /**
     * Create Random token for respective email Id
     * Save it under Redis Cache and send password reset Auth Link to Email
     * Redis Map = {TOKEN:USERID}
     *
     * @param userEmail {@link String}
     * @return ForgetPassword
     */

    @Override
    public ForgetPassword newForgetPasswordRequest(String userEmail) throws Exception {
        User users = Optional.ofNullable(userRepository.findByEmailAddress(userEmail)).orElseThrow(
                () -> new NsquareException(EMAIL_NOT_FOUND.getErrorCode(), EMAIL_NOT_FOUND.getErrorMessage()));
        ForgetPassword forgetPassword = new ForgetPassword();
        if (users.isActive()) {
            String newToken = RandomStringUtils.randomAlphanumeric(7);
            generateMail.run(userEmail, newToken);
            forgetPassword.setForgetToken(newToken);
            forgetPassword.setUserEmail(userEmail);
            log.info("Email sent to reset Password");
            redisConfig.cacheTemplate().put(newToken, users.get_id());
            return forgetPassword;
        } else {
            log.error("User Not Active Anymore cannot Reset passWord");
            throw new NsquareException(USER_NOT_ACTIVE.getErrorCode(), USER_NOT_ACTIVE.getErrorMessage());
        }
    }

    /**
     * User Entry for new Password
     * Make change to User with Email as value for the token in Redis
     * and saving the Changes || { Delimiter is  token = new_passWord }
     * resetTokenNewPasswordCombo will be in Base64 encoded
     * key of the Redis is the Id of the User
     *
     * @param resetTokenNewPasswordCombo {@link String}
     * @return User
     */
    @Override
    public HttpStatus successfullyChangePassword(String resetTokenNewPasswordCombo) {
        byte[] decodeNewPassword = Base64.getDecoder().decode(resetTokenNewPasswordCombo);
        String decodedCombo = new String(decodeNewPassword);
        String[] partingCombo = decodedCombo.split("=");
        String resetToken = partingCombo[0];
        String newPassword = partingCombo[1];
        try {
            String userIdfromtheKey = redisConfig.cacheTemplate().get(resetToken); //-> return the userId associated from the key.
            Optional<Token> token = tokenRepository.findById(userIdfromtheKey);
            return changeUserData(token, newPassword);
        } catch (NsquareException ex) {
            GeneralErrorEnum enumObj = ApplicationUtils.retrieveEnumObject(new NsquareException(TOKEN_IS_NOT_IN_REDIS.getErrorCode(), TOKEN_IS_NOT_IN_REDIS.getErrorMessage()));
            return ApplicationUtils.getHttpStatus(enumObj.getHttpStatusErrorCode());
        }
    }

    /**
     * @param token       {@link Optional<Token>}
     * @param newPassword {@link String} will be already decoded
     * @return
     */
    private HttpStatus changeUserData(Optional<Token> token, String newPassword) {
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(getHttpStatus(217));
        token.map(Token::getAuthToken).ifPresent(val -> {
                    byte[] decodePrevAuthToken = Base64.getDecoder().decode(val);
                    String decodedCombo = new String(decodePrevAuthToken);
                    String[] partingCombo = decodedCombo.split("_");
                    String prevUserName = partingCombo[0];
                    String prevPassWord = partingCombo[1];
                    prevPassWord = newPassword;
                    String newCombo = prevUserName.concat("_").concat(prevPassWord);
                    String newUserNamePasswordCombinationbyte = Base64.getEncoder().encodeToString(newCombo.getBytes(StandardCharsets.UTF_8));
                    token.get().setAuthToken(newUserNamePasswordCombinationbyte);
                    tokenRepository.save(token.get());
                    httpStatus.set(getHttpStatus(200));
                    log.info("Password Changed Successfully");
                }
        );
        return httpStatus.get();
    }


    /**
     * Logout the user based on the the Header authset
     *
     * @param auth {@link String}
     * @return HttpStatus
     */
    @Override
    public HttpStatus logoutUser(String auth) {
        try {
            String getAuth = redisConfig.cacheTemplate().get(auth);
            if(null!=getAuth) {
                redisConfig.cacheTemplate().remove(auth);
                log.info("REDIS_LOGIN_TOKEN_DELETED");
                return HttpStatus.OK;
            }
            else{
                log.error("REDIS TOKEN NOT FOUND");
                return HttpStatus.BAD_REQUEST;
            }

        } catch (NullPointerException ex) {
            log.error("REDIS TOKEN NOT FOUND");
            return HttpStatus.BAD_REQUEST;
        }
    }


}


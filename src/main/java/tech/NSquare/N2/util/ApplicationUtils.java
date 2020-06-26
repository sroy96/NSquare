package tech.NSquare.N2.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.NSquare.N2.configuration.RedisConfig;
import tech.NSquare.N2.models.User;
import tech.NSquare.N2.models.enums.GeneralErrorEnum;
import tech.NSquare.N2.repository.UserRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static tech.NSquare.N2.models.enums.GeneralErrorEnum.AUTH_TOKEN_NOT_PRESENT;
import static tech.NSquare.N2.models.enums.GeneralErrorEnum.TOKEN_IS_NOT_IN_REDIS;


@Slf4j
@Service
public final class ApplicationUtils {

    @Autowired
    RedisConfig redisConfig;

    @Autowired
    UserRepository userRepository;

    private ApplicationUtils() {

    }
    /**
     * This Method prepare enumObject from exception object.
     *
     * @param httpStatusErrorCode {@link Integer}
     * @return HttpStatus
     */
    public static HttpStatus getHttpStatus(Integer httpStatusErrorCode) {

        HttpStatus defaultStatus = HttpStatus.OK;
        HttpStatus httpResponseStatus;

        try {
            httpResponseStatus = HttpStatus.valueOf(httpStatusErrorCode);

        } catch (IllegalArgumentException ex) {
            httpResponseStatus = defaultStatus;
        }

        return httpResponseStatus;
    }



    /**
     * This Method prepare enumObject from exception object.
     *
     * @param ex
     * @return GeneralErrorEnum
     */
    public static GeneralErrorEnum retrieveEnumObject(Exception ex) {
        GeneralErrorEnum enumObj ;

        if (ex instanceof NsquareException) {
            enumObj = GeneralErrorEnum.getEnumByErrorCode(((NsquareException) ex).getErrorCode());
        }
        else{
            enumObj =  GeneralErrorEnum.UNKNOWN;
        }
        return enumObj;
    }

    /**
     * converting java.util.Date to java.time.LocalDate
     *
     * @param utilDate {@link Date}
     * @return LocalDate
     */
    public static LocalDate convertUtilDateToLocalDate(Date utilDate) {
        LocalDate localDate = null;
        if (utilDate != null) {
            Instant instant = Instant.ofEpochMilli(utilDate.getTime());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            localDate = localDateTime.toLocalDate();
        }
        return localDate;
    }

    /**
     * Method used to convert LocalDate to milliseconds
     *
     * @param localDate
     * @return time in milliseconds
     */

    public static Long convertLocalDateToMillis(LocalDate localDate) {
        Long milliSeconds = 0L;
        if (localDate != null) {
            LocalDateTime localDateTime3 = localDate.atTime(23, 59, 59);
            milliSeconds = Timestamp.valueOf(localDateTime3).getTime();
        }
        return milliSeconds;
    }

    /**
     * Method to get current Date time
     */
    public static LocalDateTime getCurrentDateAndTime() {
        return LocalDateTime.now();
    }

    /**
     * Get Auth Token from the Header and verify for every user.
     * @param authFromHeader {@link String}
     * @return User
     */
    public final User authHeaderUserVerification(String authFromHeader){
        if(null!=authFromHeader){
            String userId = redisConfig.cacheTemplate().get(authFromHeader);
            if(null!=userId) {
                User objectUser = userRepository.findBy_id(userId);
                log.info("USER IS AUTHENTICATED");
                return objectUser;
            }
            else{
            log.info("USER SESSION EXPIRED");
            throw new NsquareException(TOKEN_IS_NOT_IN_REDIS.getErrorCode(),TOKEN_IS_NOT_IN_REDIS.getErrorMessage());
            }
        }
        else{
            log.error(String.valueOf(new NsquareException(AUTH_TOKEN_NOT_PRESENT.getErrorCode(), AUTH_TOKEN_NOT_PRESENT.getErrorMessage())));
            throw  new NsquareException(AUTH_TOKEN_NOT_PRESENT.getErrorCode(), AUTH_TOKEN_NOT_PRESENT.getErrorMessage());
        }
    }

}

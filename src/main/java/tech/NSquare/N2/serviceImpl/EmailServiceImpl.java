package tech.NSquare.N2.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tech.NSquare.N2.models.AwsMail;
import tech.NSquare.N2.models.Guest;
import tech.NSquare.N2.util.NsquareException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import java.util.HashMap;
import java.util.Map;
import static tech.NSquare.N2.models.enums.GeneralErrorEnum.MAIL_AUTHENTICATION_FAILED;

@Slf4j
@Service
public class EmailServiceImpl {

    @Value("${password.reset.url}")
    private String resetUrl;

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    AWSMailServiceImpl awsMailService;


    public void sendMail(String userMailId, String newToken) {
        AwsMail awsMail = createMail(userMailId, newToken);
        try {
            awsMailService.sendEmail(awsMail);
        } catch (MessagingException ex) {
            log.error("MAIL_AUTHENTICATION_FAILED");
            throw new NsquareException(MAIL_AUTHENTICATION_FAILED.getErrorCode(), MAIL_AUTHENTICATION_FAILED.getErrorMessage());
        }
    }

    public void sendJoiningMail(Guest guest) {
        AwsMail awsMail = joinUsmail(guest);
        try {
            awsMailService.sendEmail(awsMail);
        } catch (MessagingException ex) {
            log.error("MAIL_AUTHENTICATION_FAILED");
            throw new NsquareException(MAIL_AUTHENTICATION_FAILED.getErrorCode(), MAIL_AUTHENTICATION_FAILED.getErrorMessage());
        }
    }

    public void notifyUs(Guest guest){
        AwsMail awsMail = notify(guest);
        try {
            awsMailService.sendEmail(awsMail);
        } catch (MessagingException ex) {
            log.error("MAIL_AUTHENTICATION_FAILED");
            throw new NsquareException(MAIL_AUTHENTICATION_FAILED.getErrorCode(), MAIL_AUTHENTICATION_FAILED.getErrorMessage());
        }
    }

    private AwsMail notify(Guest guest) {
        String html = "<h2>Naya Bakra Notification Mail </h2>" +
                "<h3> Hi guys, a new bakra wants to join\n </h3>" +
                "<h3>Name:" + guest.getName() + "\n</h3>" +
                "<h3>Contact:" + guest.getContactNumber() + "\n</h3>" +
                "<h3>Email :" + guest.getEmail() + "\n</h3>";
        AwsMail mail = new AwsMail();
        mail.setFrom(fromEmail);
        mail.setTo(fromEmail);
        mail.setSubject("NSquare : A new joinee notification");
        Map<String, Object> model = new HashMap<>();
        model.put("templateVariable", html);
        mail.setModel(model);
        return mail;
    }

    private AwsMail joinUsmail(Guest guestDetails) {
        String html = "<h2>Welcome to NSquare </h2>" +
                "<h3> Hola!! A warm welcome to the NSquare Family:)\n </h3>" +
                "<h3>We will be contacting you shortly....</h3>";
        AwsMail mail = new AwsMail();
        mail.setFrom(fromEmail);
        mail.setTo(guestDetails.getEmail());
        mail.setSubject("NSquare : Welcomes YOU");
        Map<String, Object> model = new HashMap<>();
        model.put("templateVariable", html);
        mail.setModel(model);
        return mail;

    }

    private AwsMail createMail(String toEmail, String token) {
        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=UTF-8");
        var resetToken = resetUrl + token;
        String html = "<h2>Reset password request </h2>" +
                "<h3> Please click on the button to reset password \n </h3> " +
                " <a target='_blank' href=" + resetToken + "><button style>Reset your password</button></a>";
        AwsMail mail = new AwsMail();
        mail.setFrom(fromEmail);
        mail.setTo(toEmail);
        mail.setSubject("NSquare : Password reset Email");
        Map<String, Object> model = new HashMap<>();
        model.put("templateVariable", html);
        mail.setModel(model);
        return mail;
    }


//    public void sendSimpleMessage(Mail mail,String token) throws MessagingException {
//        InternetHeaders headers = new InternetHeaders();
//        headers.addHeader("Content-type", "text/html; charset=UTF-8");
//        var resetToken = resetUrl +token;
//        String html ="<h2>Reset password request </h2>" +
//                "<h3> Please click on the button to reset password \n </h3> "+
//                " <a target='_blank' href="+resetToken+"><button style>Reset your password</button></a>";
//        MimeMessage message = javaMailSender.createMimeMessage();
//        message.setRecipients(TO, String.valueOf(mail.getTo()));
//        message.setSubject(mail.getSubject());
//        message.setText(html,"UTF-8","html");
//        message.setFrom(mail.getFrom());
//        message.setReplyTo(new InternetAddress[]{
//                new InternetAddress((String) mail.getTo())
//        });
//        try {
//            javaMailSender.send(message);
//        }
//        catch(Exception ex){
//            log.error("MAIL_AUTHENTICATION_FAILED");
//            throw new NsquareException(MAIL_AUTHENTICATION_FAILED.getErrorCode(),MAIL_AUTHENTICATION_FAILED.getErrorMessage());
//        }
//    }
}

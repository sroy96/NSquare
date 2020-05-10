package tech.NSquare.N2.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tech.NSquare.N2.constants.URLConstants;
import tech.NSquare.N2.models.Mail;
import tech.NSquare.N2.util.NsquareException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;


import static javax.mail.Message.RecipientType.TO;
import static tech.NSquare.N2.models.enums.GeneralErrorEnum.MAIL_AUTHENTICATION_FAILED;

@Slf4j
@Service
public class EmailServiceImpl {

    @Value("${password.reset.url}")
    private String resetUrl;

    @Autowired
    JavaMailSender javaMailSender;

    public void sendSimpleMessage(Mail mail,String token) throws MessagingException {
        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=UTF-8");
        var resetToken = resetUrl +token;
        String html ="<h2>Reset password request </h2>" +
                "<h3> Plaese click on the button to reset password \n </h3> "+
                " <a target='_blank' href="+resetToken+"><button>Reset your password</button></a>";
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipients(TO, String.valueOf(mail.getTo()));
        message.setSubject(mail.getSubject());
        message.setText(html,"UTF-8","html");
        message.setFrom(mail.getFrom());
        message.setReplyTo(new InternetAddress[]{
                new InternetAddress((String) mail.getTo())
        });
        try {
            javaMailSender.send(message);
        }
        catch(Exception ex){
            log.error("MAIL_AUTHENTICATION_FAILED");
            throw new NsquareException(MAIL_AUTHENTICATION_FAILED.getErrorCode(),MAIL_AUTHENTICATION_FAILED.getErrorMessage());
        }
    }
}

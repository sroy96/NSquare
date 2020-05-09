package tech.NSquare.N2.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.NSquare.N2.models.Mail;
import tech.NSquare.N2.serviceImpl.EmailServiceImpl;

import javax.mail.internet.InternetHeaders;

@Component
public class GenerateMail {


    @Autowired
    private EmailServiceImpl emailService;
    public void run(String to, String token) throws Exception {
        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=UTF-8");
        Mail mail = new Mail();
        mail.setFrom("sauravarduino@gmail.com");
        mail.setTo(to);
        mail.setSubject("Password Reset Mail @NSquare");
        mail.setContent(token);

        emailService.sendSimpleMessage(mail,token);
    }
}



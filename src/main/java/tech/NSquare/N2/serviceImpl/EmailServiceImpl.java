package tech.NSquare.N2.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tech.NSquare.N2.models.Mail;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendSimpleMessage(Mail mail,String token) throws MessagingException {
        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=UTF-8");
        String html = "Test\n" + token + "\n<a href='http://test.com'>Test.com</a>";
        MimeMessage message = javaMailSender.createMimeMessage();

//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setSubject(mail.getSubject());
//        helper.setText(mail.getContent());
//        helper.setTo(mail.getTo());
//        helper.setFrom(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(html,"UTF-8","html");
        message.setFrom(mail.getFrom());
        message.setReplyTo(new Address[]{(Address) mail.getTo()});
        javaMailSender.send(message);

    }

}

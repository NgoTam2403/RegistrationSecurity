package com.example.TestSecurity.email;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class EmailService implements EmailSender{
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSenderImpl mailSender;

    @Override
    @Async //khong dong bo
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(email);
            helper.setTo(to);
            helper.setSubject("confirm email");
            helper.setFrom("tamminhngo2002@gmail.com");
            mailSender.send(mimeMessage);
        }catch (Exception e){
            LOGGER.error("failed send to email",e);
            throw new IllegalStateException("fail to send email");
        }

    }
}

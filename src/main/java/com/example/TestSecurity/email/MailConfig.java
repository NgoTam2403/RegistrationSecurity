package com.example.TestSecurity.email;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailConfig {
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Cấu hình thông tin SMTP server
        //mailSender.setHost("your-smtp-host");
       // mailSender.setPort(587);
      //  mailSender.setUsername("your-username");
       // mailSender.setPassword("your-password");

        return mailSender;
    }
}

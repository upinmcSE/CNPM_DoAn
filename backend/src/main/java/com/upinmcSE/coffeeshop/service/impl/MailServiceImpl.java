package com.upinmcSE.coffeeshop.service.impl;

import com.upinmcSE.coffeeshop.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String emailFrom;

    @Override
    public String sendEmail(String recipient, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(emailFrom, "Coffee Shop");

        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setTo(recipient);

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
        return "Sent";
    }

    @Override
    public void sendConfirmLink(String emailTo) {

    }
}

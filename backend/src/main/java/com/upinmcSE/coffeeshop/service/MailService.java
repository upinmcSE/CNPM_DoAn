package com.upinmcSE.coffeeshop.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MailService {
    String sendEmail(String recipients, String subject, String content) throws MessagingException, UnsupportedEncodingException;
    void sendConfirmLink(String emailTo);
}

package springbootvinylecommercebackend.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    String sendRegistrationEmail(String toEmail) throws MessagingException;
}

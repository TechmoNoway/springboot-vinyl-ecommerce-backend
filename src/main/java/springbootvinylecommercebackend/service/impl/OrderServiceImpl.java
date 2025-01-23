package springbootvinylecommercebackend.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import springbootvinylecommercebackend.mapper.OrderMapper;
import springbootvinylecommercebackend.model.Order;
import springbootvinylecommercebackend.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;

    private final JavaMailSender mailSender;

    @Override
    public List<Order> getAllOrders() {
        return mapper.getAllOrders();
    }

    @Override
    public void saveOrder(Order order) {
        mapper.saveOrder(order);

    }

    @Override
    public void sendOrderSuccessMail(String userEmail) {
        String from = "nguyentriky0604@gmail";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        try {
            String htmlContent = new String(Files.readAllBytes(Path.of("C:\\Users\\ASUS\\Documents\\workspace-spring-tool-suite-4-4.14.1.RELEASE\\test\\src\\main\\resources\\templates\\MailContent.html")));

            helper = new MimeMessageHelper(message, true);
            helper.setSubject("Thank You For Order Our Product");
            helper.setFrom(from);
            helper.setTo(userEmail);
            helper.setText(htmlContent, true);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

        mailSender.send(message);
    }

}

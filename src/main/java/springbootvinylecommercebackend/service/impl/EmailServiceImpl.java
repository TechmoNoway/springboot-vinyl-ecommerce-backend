package springbootvinylecommercebackend.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import springbootvinylecommercebackend.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSenderImpl mailSender;

    public String sendRegistrationEmail(String toEmail) throws MessagingException {
        String generatedPassword = RandomStringUtils.randomAlphanumeric(8); // Generate random password
        String resetLink = "http://localhost:5173/reset-password?email=" + toEmail;
        String username = toEmail.substring(0, toEmail.indexOf("@"));

        String emailContent = "<p>Chào mừng tới Vọc Records</p>"
                + "<p>Xin chào " + username + ",</p>"
                + "<p>Cảm ơn bạn đã tạo tài khoản ở Vọc Records. </p>"
                + "Tên tài khoản của bạn là " + username + ".</p>"
                + "<p>Mật khẩu tạm thời của bạn là: " + generatedPassword + "</p>"
                + "<p>Bạn có thể truy cập trang tài khoản để xem đơn hàng, đổi mật khẩu, và nhiều thứ khác tại: <a href='http://localhost:5173/profile/'>http://localhost:5173/profile/</a></p>"
                + "<p>Click <a href='" + resetLink + "'>để nhập mật khẩu mới.</a></p>"
                + "<p>We look forward to seeing you soon.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Your Vinyl Store Account Details");
        helper.setText(emailContent, true);

        mailSender.send(message);

        return generatedPassword;
    }



}

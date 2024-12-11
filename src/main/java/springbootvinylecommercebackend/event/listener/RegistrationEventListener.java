package springbootvinylecommercebackend.event.listener;

import com.allxone.mybatisprojectbackend.event.RegistrationEvent;
import com.allxone.mybatisprojectbackend.model.User;
import com.allxone.mybatisprojectbackend.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {
    private final EmailService emailService;
    private User user;

    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        user = event.getUser();
        String name = user.getName();
        String email = user.getEmail();
        String url = "http://localhost:5555/api/auth/verifyEmail?token="+event.getJwtToken();
        try {
            emailService.sendVerificationEmail(name,email,url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

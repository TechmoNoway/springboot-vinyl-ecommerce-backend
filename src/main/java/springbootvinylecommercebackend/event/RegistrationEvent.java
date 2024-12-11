package springbootvinylecommercebackend.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import springbootvinylecommercebackend.model.User;

@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {
    private User user;
    private String jwtToken;
    public RegistrationEvent(springbootvinylecommercebackend.model.User user, String jwtToken) {
        super(user);
        this.user = user;
        this.jwtToken = jwtToken;
    }
}

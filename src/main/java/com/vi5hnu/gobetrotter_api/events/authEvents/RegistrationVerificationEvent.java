package com.vi5hnu.gobetrotter_api.events.authEvents;

import com.vi5hnu.gobetrotter_api.Entity.user.UserModel;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class RegistrationVerificationEvent extends ApplicationEvent {
    private final UserModel userModel;
    private final String token;
    private final String verificationUrl;

    public RegistrationVerificationEvent(UserModel userModel,String token, String verificationUrl) {
        super(userModel);
        this.userModel=userModel;
        this.token=token;
        this.verificationUrl=verificationUrl;
    }
}

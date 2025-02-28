package com.vi5hnu.gobetrotter_api.events.authEvents;

import com.vi5hnu.gobetrotter_api.Entity.user.UserModel;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PasswordUpdateComplete extends ApplicationEvent {
    private final UserModel userModel;

    public PasswordUpdateComplete(UserModel userModel) {
        super(userModel);
        this.userModel=userModel;
    }
}
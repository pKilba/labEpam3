package com.epam.esm.link;

import com.epam.esm.controller.UserController;
import com.epam.esm.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserLinkProvider extends AbstractLinkProvider<User> {

    private static final Class<UserController> CONTROLLER_CLASS = UserController.class;

    public void provideLinks(User user) {
        long id = user.getId();
        provideIdLinks(CONTROLLER_CLASS, user, id, SELF_LINK);
    }
}

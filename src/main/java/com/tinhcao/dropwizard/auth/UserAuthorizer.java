package com.tinhcao.dropwizard.auth;

import com.tinhcao.dropwizard.db.entity.User;
import io.dropwizard.auth.Authorizer;

public class UserAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User principal, String role) {
        return true;
    }
}

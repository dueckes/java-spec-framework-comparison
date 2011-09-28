package net.ueckerman.user;

import org.apache.commons.lang3.Validate;

public class User {

    private final String username;

    public User(String username) {
        Validate.notEmpty(username, "Username must be provided");
        this.username = username;
    }

}

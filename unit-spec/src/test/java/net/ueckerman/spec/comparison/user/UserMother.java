package net.ueckerman.spec.comparison.user;

public class UserMother {

    private UserMother() {
        // Static class
    }

    public static User createUser() {
        return new User("testuser");
    }

}

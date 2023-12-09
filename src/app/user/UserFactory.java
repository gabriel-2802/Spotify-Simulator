package app.user;

import app.utils.Enums;

/**
 * UserFactory class
 */
public class UserFactory {
    private UserFactory() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * instantiates a new user based on the type
     * @param name the name of the user
     * @param age the age of the user
     * @param city the city of the user
     * @param userType the type of the user
     * @return the new user
     */
    public static User createUser(final String name,final int age, final String city,
                                  final Enums.UserType userType) {
        return switch (userType) {
            case ARTIST -> new Artist(name, age, city);
            case HOST -> new Host(name, age, city);
            default -> new User(name, age, city);
        };
    }
}

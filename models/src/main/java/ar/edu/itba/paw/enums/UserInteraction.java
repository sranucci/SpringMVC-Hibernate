package ar.edu.itba.paw.enums;

public enum UserInteraction {
    LIKE("LIKE"),
    DISLIKE("DISLIKE"),
    NO_INTERACTION("NO_INTERACTION");

    private final String value;

    UserInteraction(String value) {
        this.value = value;
    }

    public static UserInteraction getInteraction(String s) {
        for (UserInteraction interaction : UserInteraction.values()) {
            if (interaction.value.equals(s))
                return interaction;
        }
        throw new IllegalArgumentException("Invalid Argument");
    }

    public boolean userLikes() {
        return this.value.equals("LIKE");
    }

    public boolean userDislikes() {
        return this.value.equals("DISLIKE");
    }
}

package model.entity.enums;

public enum UserRole {
    ADMINISTRATOR, REGISTERED;

    @Override
    public String toString() {
        return super.toString();
    }

    public static UserRole setUserRole(String role) {
        switch (role) {
            case "administrator":
                return ADMINISTRATOR;
            case "registered":
                return REGISTERED;
            default:
                return null;
        }
    }
}

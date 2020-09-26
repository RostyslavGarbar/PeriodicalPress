package model.entity.enums;

public enum Status {
    COMPLETED, IN_PROGRESS, PLANNED;

    @Override
    public String toString() {
        return super.toString();
    }

    public static Status setStatus(String status) {
        switch (status) {
            case "completed":
                return COMPLETED;
            case "in_progress":
                return IN_PROGRESS;
            case "planned":
                return PLANNED;
            default:
                return null;
        }
    }
}

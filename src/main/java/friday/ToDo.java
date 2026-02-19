package friday;

/**
 * Represents a to-do task that has a description but no date or time constraints.
 */
public class ToDo extends Task {
    /**
     * Constructs a ToDo task with the given description.
     *
     * @param description Description of the to-do task. Must not be null or blank.
     * @throws FridayException If the description is invalid.
     */
    public ToDo(String description) throws FridayException {
        super(validateDescription(description));
    }

    private static String validateDescription(String description) throws FridayException {
        if (description == null || description.isBlank()) {
            throw new FridayException("Description must not be blank.");
        }
        return description;
    }

    /**
     * Returns the string representation of this to-do task for display to the user.
     *
     * @return A formatted string representing the to-do task.
     */
    @Override
    public String toString() {
        return "[T] " + super.toString();
    }

    /**
     * Returns the string representation of this to-do task for saving to storage.
     * Escapes the delimiter in the description to avoid save/load issues.
     *
     * @return A formatted save string representing the to-do task.
     */
    @Override
    public String toSaveString() {
        String safeDescription = getDescription().replace(" | ", " \\| ");
        return "T | " + (isCompleted() ? "1" : "0") + " | " + safeDescription;
    }
}

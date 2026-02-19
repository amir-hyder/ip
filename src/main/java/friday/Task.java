package friday;

/**
 * Represents a generic task with a description and completion status.
 * This class serves as the base class for all specific task types.
 */
public abstract class Task {
    private final String description;
    private boolean isCompleted;

    /**
     * Constructs a Task with a description and isCompleted field.
     *
     * @param description Description of the task. Must not be null or blank.
     * @throws FridayException If the description is invalid.
     */
    public Task(String description) throws FridayException {
        this.description = validateDescription(description);
        this.isCompleted = false;
    }

    protected String getDescription() {
        return this.description;
    }

    protected boolean isCompleted() {
        return this.isCompleted;
    }

    private static String validateDescription(String description) throws FridayException {
        if (description == null || description.isBlank()) {
            throw new FridayException("Description must not be blank.");
        }
        return description;
    }

    /**
     * Marks this task as isCompleted.
     */
    public void mark() {
        this.isCompleted = true;
    }

    /**
     * Marks this task as not isCompleted.
     */
    public void unmark() {
        this.isCompleted = false;
    }

    /**
     * Returns the string representation of this task for display to the user.
     * Indicates whether the task is isCompleted and shows its description.
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        return (this.isCompleted ? "[X] "
                : "[ ] ") + this.description;
    }

    /**
     * Returns the string representation of this task for saving to storage.
     * Escapes the delimiter in the description to avoid save/load issues.
     * The format encodes the completion status followed by the description.
     *
     * @return A formatted save string representing the task.
     */
    public abstract String toSaveString();
}

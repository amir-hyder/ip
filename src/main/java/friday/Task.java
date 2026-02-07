package friday;

/**
 * Represents a generic task with a description and completion status.
 * This class serves as the base class for all specific task types.
 */
public class Task {
    private final String description;
    private boolean isCompleted;

    /**
     * Constructs an {@code Task} with a description and isCompleted field.
     *
     * @param description Description of the event
     *                    isCompleted is initially set to false
     */
    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
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
     * The format encodes the completion status followed by the description.
     *
     * @return A formatted save string representing the task.
     */
    public String toSaveString() {
        return (this.isCompleted ? "1" : "0") + " | " + this.description;
    }
}

package Friday;

/**
 * Represents a generic task with a description and completion status.
 * This class serves as the base class for all specific task types.
 */
public class Task {
    private final String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        this.completed = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void unmark() {
        this.completed = false;
    }

    /**
     * Returns the string representation of this task for display to the user.
     * Indicates whether the task is completed and shows its description.
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        return (this.completed ? "[X] "
                : "[ ] ") + this.description;
    }

    /**
     * Returns the string representation of this task for saving to storage.
     * The format encodes the completion status followed by the description.
     *
     * @return A formatted save string representing the task.
     */
    public String toSaveString() {
        return (this.completed ? "1" : "0") + " | " + this.description;
    }
}

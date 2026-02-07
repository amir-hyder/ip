package friday;

/**
 * Represents a to-do task that has a description but no date or time constraints.
 */
public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
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
     *
     * @return A formatted save string representing the to-do task.
     */
    @Override
    public String toSaveString() {
        return "T | " + super.toSaveString();
    }
}

package friday;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task that must be completed by a specific date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate deadline;

    /**
     *  Constructs an {@code Deadline} with a description, and deadline.
     *
     * @param description Description of the deadline
     * @param deadline Date the deadline is due
     */
    public Deadline(String description, String deadline) {
        super(description);
        try {
            this.deadline = LocalDate.parse(deadline, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use: yyyy-MM-dd (e.g., 2019-12-02)");
        }
    }

    /**
     * Returns the string representation of this deadline for display to the user.
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + this.deadline.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns the string representation of this deadline for saving to storage.
     *
     * @return A formatted save string representing the deadline task.
     */
    @Override
    public String toSaveString() {
        return "D | " + super.toSaveString() + " | " + this.deadline.format(INPUT_FORMAT);
    }
}

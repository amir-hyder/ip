package friday;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task that must be completed by a specific date.
 */
public class Deadline extends Task {
    private static final String TYPE_CODE = "D";
    private static final String STORAGE_DELIMITER = " | ";

    private static final String INPUT_PATTERN = "yyyy-MM-dd";
    private static final String OUTPUT_PATTERN = "MMM dd yyyy";

    private static final String INVALID_DATE_MESSAGE = "Invalid date format. Use: yyyy-MM-dd (e.g 2019-12-02)";

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern(INPUT_PATTERN);
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern(OUTPUT_PATTERN);

    private final LocalDate deadline;

    /**
     *  Constructs an {@code Deadline} with a description, and deadline.
     *
     * @param description Description of the deadline
     * @param deadline Date the deadline is due
     */
    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = parseDeadline(deadline);
    }

    private static LocalDate parseDeadline(String deadlineRaw) {
        try {
            return LocalDate.parse(deadlineRaw, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }

    /**
     * Returns the string representation of this deadline for display to the user.
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        return "[" + TYPE_CODE + "] " + super.toString() + " (by: " + this.deadline.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns the string representation of this deadline for saving to storage.
     *
     * @return A formatted save string representing the deadline task.
     */
    @Override
    public String toSaveString() {
        return TYPE_CODE + STORAGE_DELIMITER + super.toSaveString()
                + STORAGE_DELIMITER + this.deadline.format(INPUT_FORMAT);
    }
}

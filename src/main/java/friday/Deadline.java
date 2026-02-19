package friday;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a deadline task that must be completed by a specific date.
 */
public class Deadline extends Task {
    private static final String TYPE_CODE = "D";
    private static final String STORAGE_DELIMITER = " | ";

    private static final String OUTPUT_PATTERN = "MMM dd yyyy";

    private static final String INVALID_DATE_MESSAGE = "Invalid date format. Use: yyyy-MM-dd (e.g 2019-12-02)";

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern(OUTPUT_PATTERN, Locale.ENGLISH);

    private final LocalDate deadline;

    /**
     * Constructs a {@code Deadline} with a description and deadline string.
     *
     * @param description Description of the deadline. Must not be null or blank.
     * @param deadline Date the deadline is due, in yyyy-MM-dd format.
     * @throws FridayException If the description or deadline is invalid.
     */
    public Deadline(String description, String deadline) throws FridayException {
        super(validateDescription(description));
        this.deadline = parseDeadlineChecked(deadline);
    }

    /**
     * Constructs a {@code Deadline} with a description and LocalDate deadline.
     *
     * @param description Description of the deadline. Must not be null or blank.
     * @param deadline Date the deadline is due.
     * @throws FridayException If the description or deadline is invalid.
     */
    public Deadline(String description, LocalDate deadline) throws FridayException {
        super(validateDescription(description));
        if (deadline == null) {
            throw new FridayException(INVALID_DATE_MESSAGE);
        }
        this.deadline = deadline;
    }

    private static String validateDescription(String description) throws FridayException {
        if (description == null || description.isBlank()) {
            throw new FridayException("Description must not be blank.");
        }
        return description;
    }

    private static LocalDate parseDeadlineChecked(String deadlineRaw) throws FridayException {
        try {
            return LocalDate.parse(deadlineRaw, INPUT_FORMAT);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new FridayException(INVALID_DATE_MESSAGE);
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
     * Escapes the delimiter in the description to avoid save/load issues.
     *
     * @return A formatted save string representing the deadline task.
     */
    @Override
    public String toSaveString() {
        String safeDescription = getDescription().replace(STORAGE_DELIMITER, " \\| ");
        return TYPE_CODE + STORAGE_DELIMITER
                + (isCompleted() ? "1" : "0") + STORAGE_DELIMITER
                + safeDescription
                + STORAGE_DELIMITER + this.deadline.format(INPUT_FORMAT);
    }

    public LocalDate getDate() {
        return this.deadline;
    }
}

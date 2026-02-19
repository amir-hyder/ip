package friday;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents an event task that occurs on a specific date
 * and has a start and end time.
 */
public class Event extends Task {
    private static final String TYPE_CODE = "E";
    private static final String STORAGE_DELIMITER = " | ";

    private static final String INVALID_EVENT_MESSAGE = "Invalid event format. Use: yyyy-MM-dd HHmm /to HHmm";

    private static final DateTimeFormatter DATE_INPUT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_INPUT = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter DATE_OUTPUT = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter TIME_OUTPUT = DateTimeFormatter.ofPattern("h:mma", Locale.ENGLISH);

    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;

    /**
     * Constructs an {@code Event} with a description, start time, and end time.
     *
     * @param description Description of the event. Must not be null or blank.
     * @param dateRaw Date of the event in yyyy-MM-dd format.
     * @param startRaw Start time of the event in HHmm format.
     * @param endRaw End time of the event in HHmm format.
     * @throws FridayException If any input is invalid.
     */
    public Event(String description, String dateRaw, String startRaw, String endRaw) throws FridayException {
        super(validateDescription(description));
        this.date = parseDateChecked(dateRaw);
        this.start = parseTimeChecked(startRaw);
        this.end = parseTimeChecked(endRaw);
    }

    /**
     * Constructs an {@code Event} with a description, LocalDate, and LocalTime values.
     *
     * @param description Description of the event. Must not be null or blank.
     * @param date Date of the event.
     * @param start Start time of the event.
     * @param end End time of the event.
     * @throws FridayException If any input is invalid.
     */
    public Event(String description, LocalDate date, LocalTime start, LocalTime end) throws FridayException {
        super(validateDescription(description));
        if (date == null || start == null || end == null) {
            throw new FridayException(INVALID_EVENT_MESSAGE);
        }
        this.date = date;
        this.start = start;
        this.end = end;
    }

    private static String validateDescription(String description) throws FridayException {
        if (description == null || description.isBlank()) {
            throw new FridayException("Description must not be blank.");
        }
        return description;
    }

    private static LocalDate parseDateChecked(String dateRaw) throws FridayException {
        try {
            return LocalDate.parse(dateRaw, DATE_INPUT);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new FridayException(INVALID_EVENT_MESSAGE);
        }
    }

    private static LocalTime parseTimeChecked(String timeRaw) throws FridayException {
        try {
            return LocalTime.parse(timeRaw, TIME_INPUT);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new FridayException(INVALID_EVENT_MESSAGE);
        }
    }

    /**
     * Returns the string representation of this event for display to the user.
     * Shows the date and time range of the event.
     *
     * @return A formatted string representing the event.
     */
    @Override
    public String toString() {
        return "[" + TYPE_CODE + "] " + super.toString()
                + " (from: "
                + this.date.format(DATE_OUTPUT) + " "
                + this.start.format(TIME_OUTPUT)
                + " to: "
                + this.end.format(TIME_OUTPUT)
                + ")";
    }

    /**
     * Returns the string representation of this event for saving to storage.
     * Escapes the delimiter in the description to avoid save/load issues.
     *
     * @return A formatted save string representing the event.
     */
    @Override
    public String toSaveString() {
        String safeDescription = getDescription().replace(STORAGE_DELIMITER, " \\| ");
        return TYPE_CODE + STORAGE_DELIMITER
                + (isCompleted() ? "1" : "0") + STORAGE_DELIMITER
                + safeDescription
                + STORAGE_DELIMITER + this.date.format(DATE_INPUT)
                + STORAGE_DELIMITER + this.start.format(TIME_INPUT)
                + STORAGE_DELIMITER + this.end.format(TIME_INPUT);
    }

    public LocalDate getDate() {
        return this.date;
    }
}

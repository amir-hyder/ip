package friday;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task that occurs on a specific date
 * and has a start and end time.
 */
public class Event extends Task {
    private static final String TYPE_CODE = "E";
    private static final String STORAGE_DELIMITER = " | ";

    private static final String INVALID_EVENT_MESSAGE = "Invalid event format. Use: yyyy-MM-dd HHmm /to HHmm";

    private static final DateTimeFormatter DATE_INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_INPUT = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter DATE_OUTPUT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter TIME_OUTPUT = DateTimeFormatter.ofPattern("h:mma");

    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;

    /**
     * Constructs an {@code Event} with a description, start time, and end time.
     *
     * @param description Description of the event.
     * @param dateRaw Date of the event
     * @param startRaw Start time of the event.
     * @param endRaw End time of the event.
     */
    public Event(String description, String dateRaw, String startRaw, String endRaw) {
        super(description);
        this.date = parseDate(dateRaw);
        this.start = parseTime(startRaw);
        this.end = parseTime(endRaw);
    }

    private static LocalDate parseDate(String dateRaw) {
        try {
            return LocalDate.parse(dateRaw, DATE_INPUT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_EVENT_MESSAGE, e);
        }
    }

    private static LocalTime parseTime(String timeRaw) {
        try {
            return LocalTime.parse(timeRaw, TIME_INPUT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_EVENT_MESSAGE, e);
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
     * The format preserves raw date and time values for reconstruction.
     *
     * @return A formatted save string representing the event.
     */
    @Override
    public String toSaveString() {
        return TYPE_CODE + STORAGE_DELIMITER
                + super.toSaveString()
                + STORAGE_DELIMITER + this.date.format(DATE_INPUT)
                + STORAGE_DELIMITER + this.start.format(TIME_INPUT)
                + STORAGE_DELIMITER + this.end.format(TIME_INPUT);
    }

    public LocalDate getDate() {
        return this.date;
    }
}

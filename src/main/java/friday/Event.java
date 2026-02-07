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
     * @param date Date of the event
     * @param start Start time of the event.
     * @param end End time of the event.
     */
    public Event(String description, String date, String start, String end) {
        super(description);
        try {
            this.date = LocalDate.parse(date, DATE_INPUT);
            this.start = LocalTime.parse(start, TIME_INPUT);
            this.end = LocalTime.parse(end, TIME_INPUT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid event format. Use: yyyy-MM-dd HHmm /to HHmm");
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
        return "[E] " + super.toString() + " (from: " + this.date.format(DATE_OUTPUT) + " "
                + this.start.format(TIME_OUTPUT) + " to: " + this.end.format(TIME_OUTPUT) + ")";
    }

    /**
     * Returns the string representation of this event for saving to storage.
     * The format preserves raw date and time values for reconstruction.
     *
     * @return A formatted save string representing the event.
     */
    @Override
    public String toSaveString() {
        return "E | " + super.toSaveString() + " | " + this.date.format(DATE_INPUT) + " | "
                + this.start.format(TIME_INPUT) + " | " + this.end.format(TIME_INPUT);
    }
}

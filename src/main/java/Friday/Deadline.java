package Friday;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final static DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final static DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final LocalDate deadline;

    public Deadline(String description, String deadline) {
        super(description);
        try {
            this.deadline = LocalDate.parse(deadline, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use: yyyy-MM-dd (e.g., 2019-12-02)");
        }
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + this.deadline.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toSaveString() {
        return "D | " + super.toSaveString() + " | " + this.deadline.format(INPUT_FORMAT);
    }
}

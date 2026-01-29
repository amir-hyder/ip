public class Deadline extends Task {
    private final String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + this.deadline + ")";
    }

    @Override
    public String toSaveString() {
        return "D | " + super.toSaveString() + " | " + this.deadline;
    }
}

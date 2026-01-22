public class Task {
    private String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    public void mark() {
        this.completed = true;
    }

    public void unmark() {
        this.completed = false;
    }

    @Override
    public String toString() {
        String completionStatus;
        if (!this.completed) {
            completionStatus = "[ ]";
        } else {
            completionStatus = "[X]";
        }

        return completionStatus + " " + this.description;
    }
}

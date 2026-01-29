public class Task {
    private final String description;
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
        return (this.completed ? "[X] "
                               : "[ ] ") + this.description;
    }

    public String toSaveString() {
        return (this.completed ? "1" : "0") + " | " + this.description;
    }
}

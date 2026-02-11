package friday;

/**
 * Parses user commands and saved task data into usable program objects.
 * Responsible for interpreting task indices from commands and reconstructing
 * {@link Task} objects from stored text.
 */
public class Parser {

    /**
     * Parses the task index from a user command such as {@code "mark 2"}.
     * The index is expected to be 1-based.
     *
     * @param input The full user command.
     * @return The parsed task index.
     * @throws FridayException If the command does not contain exactly one valid integer index.
     */
    public int parseIndex(String input) throws FridayException {
        assert input != null : "parseIndex should not receive null input";
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new FridayException("Please specify which task number");
        }
        if (parts.length > 2) {
            throw new FridayException("You have too many commands! Just include which task number");
        }
        int index;
        try {
            index = Integer.parseInt(parts[1]);
            assert index > 0 : "Index should be positive (1-based)";
        } catch (NumberFormatException e) {
            throw new FridayException("Friday.Task number must be a valid integer");
        }
        return index;
    }

    /**
     * Parses a saved task line into a {@link Task}.
     * <p>
     * Expected format:
     * <pre>
     * TYPE | DONE | DESCRIPTION [| EXTRA_FIELDS]
     * </pre>
     * where TYPE can be {@code T}, {@code D}, or {@code E}.
     *
     * @param line A single line read from the save file.
     * @return The reconstructed {@code Task}.
     * @throws FridayException If the line is empty, corrupted, or contains an unknown task type.
     */
    public Task parseLineToTask(String line) throws FridayException {
        assert line != null : "parseLineToTask should not receive null";
        if (line.isBlank()) {
            throw new FridayException("Empty line in save file");
        }

        String[] parts = line.split(" \\| ");
        assert parts.length >= 3 : "Split result should have at least 3 parts";
        // Expected минимум: TYPE | DONE | DESCRIPTION

        String type = parts[0];
        assert type.equals("T") || type.equals("D") || type.equals("E")
                : "Invalid task type";
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        assert !description.isBlank() : "Task description should not be blank";

        Task task;
        if (type.equals("T")) {
            task = new ToDo(description);
        } else if (type.equals("D")) {
            if (parts.length < 4) {
                throw new FridayException("Corrupted deadline line: " + line);
            }
            task = new Deadline(description, parts[3]);
        } else {
            if (parts.length < 6) {
                throw new FridayException("Corrupted event line: " + line);
            }
            String date = parts[3];
            String start = parts[4];
            String end = parts[5];
            task = new Event(description, date, start, end);
        }

        if (isDone) {
            task.mark();
        }
        return task;
    }
}

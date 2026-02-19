package friday;

/**
 * Parses user commands and saved task data into usable program objects.
 * Responsible for interpreting task indices from commands and reconstructing
 * {@link Task} objects from stored text.
 */
public class Parser {

    /**
     * Parses the task index from a user command such as {@code "mark 2"}.
     * The index is expected to be 1-based. Supports extra spaces and multi-word commands.
     *
     * @param input The full user command.
     * @return The parsed task index.
     * @throws FridayException If the command does not contain a valid integer index.
     */
    public int parseIndex(String input) throws FridayException {
        if (input == null) {
            throw new FridayException("Input cannot be null.");
        }
        String trimmedInput = input.trim();
        String[] parts = trimmedInput.split("\\s+");
        // Find the first integer in the command after the first word
        for (int i = 1; i < parts.length; i++) {
            try {
                int index = Integer.parseInt(parts[i]);
                if (index <= 0) {
                    throw new FridayException("Task number must be a positive integer (1-based).");
                }
                return index;
            } catch (NumberFormatException ignored) {
                // Not an integer, continue searching
            }
        }
        throw new FridayException("Please specify a valid task number after the command.");
    }

    /**
     * Parses a saved task line into a {@link Task}.
     * <p>
     * Expected format:
     * <pre>
     * TYPE | DONE | DESCRIPTION [| EXTRA_FIELDS]
     * </pre>
     * where TYPE can be {@code T}, {@code D}, or {@code E}.
     * Unescapes delimiters in the description.
     *
     * @param line A single line read from the save file.
     * @return The reconstructed {@code Task}.
     * @throws FridayException If the line is empty, corrupted, or contains an unknown task type.
     */
    public Task parseLineToTask(String line) throws FridayException {
        if (line == null) {
            throw new FridayException("Input line cannot be null.");
        }
        String trimmedLine = line.trim();
        if (trimmedLine.isBlank()) {
            throw new FridayException("Save file contains an empty line.");
        }
        String[] parts = trimmedLine.split(" \\| ");
        if (parts.length < 3) {
            throw new FridayException("Corrupted save line: not enough fields.");
        }
        String type = parts[0];
        if (!(type.equals("T") || type.equals("D") || type.equals("E"))) {
            throw new FridayException("Unknown task type: " + type);
        }
        boolean isDone = parts[1].equals("1");
        String description = parts[2].replace(" \\| ", " | ");
        if (description.isBlank()) {
            throw new FridayException("Task description must not be blank.");
        }
        Task task;
        if (type.equals("T")) {
            task = new ToDo(description);
        } else if (type.equals("D")) {
            if (parts.length < 4) {
                throw new FridayException("Corrupted deadline line: missing date.");
            }
            task = new Deadline(description, parts[3]);
        } else {
            if (parts.length < 6) {
                throw new FridayException("Corrupted event line: missing fields.");
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

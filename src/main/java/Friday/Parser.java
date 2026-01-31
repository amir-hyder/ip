package Friday;

public class Parser {

    // Get index of Friday.Task user would like to mark/unmark/delete
    public int parseIndex(String input) throws FridayException {
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
        } catch (NumberFormatException e) {
            throw new FridayException("Friday.Task number must be a valid integer");
        }
        return index;
    }

    public Task parseLineToTask(String line) throws FridayException {
        if (line == null || line.isBlank()) {
            throw new FridayException("Empty line in save file");
        }

        String[] parts = line.split(" \\| ");

        // Expected минимум: TYPE | DONE | DESCRIPTION
        if (parts.length < 3) {
            throw new FridayException("Corrupted save line: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        if (type.equals("T")) {
            task = new ToDo(description);

        } else if (type.equals("D")) {
            if (parts.length < 4) {
                throw new FridayException("Corrupted deadline line: " + line);
            }
            task = new Deadline(description, parts[3]);

        } else if (type.equals("E")) {
            if (parts.length < 6) {
                throw new FridayException("Corrupted event line: " + line);
            }
            String date = parts[3];
            String start = parts[4];
            String end = parts[5];
            task = new Event(description, date, start, end);

        } else {
            throw new FridayException("Unknown task type: " + type);
        }

        if (isDone) {
            task.mark();
        }
        return task;
    }
}

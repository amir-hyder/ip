package Friday;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path filePath;

    public Storage() {
        // This represents ./data/duke.txt
        this.filePath = Paths.get("data", "duke.txt");
    }

    // Load raw saved lines from the file
    public List<String> load() throws FridayException {
        try {
            if (Files.notExists(filePath)) {
                return new ArrayList<>();
            }
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new FridayException("Error loading tasks.");
        }
    }

    // Save raw lines to the file
    public void save(List<String> lines) throws FridayException {
        try {
            // Create ./data folder if missing
            Files.createDirectories(filePath.getParent());
            // Create duke.txt if missing, then write
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new FridayException("Error saving tasks.");
        }
    }

    // Loads all tasks from storage into a Friday.TaskList by using Friday.Parser to interpret each line.
    public TaskList loadTaskList(Parser parser) throws FridayException {
        TaskList list = new TaskList();
        for (String line : load()) {
            // If the save file is corrupted, we fail fast (cleanest behavior for now).
            Task task = parser.parseLineToTask(line);
            list.addTask(task);
        }
        return list;
    }

    // Saves the current Friday.TaskList into storage by converting each Friday.Task to its save string.
    public void saveTaskList(TaskList list) throws FridayException {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toSaveString());
        }
        save(lines);
    }
}

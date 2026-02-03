package Friday;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading tasks from and saving tasks to persistent storage.
 * Tasks are stored as plain text in a file and reconstructed using a {@link Parser}.
 */
public class Storage {
    private final Path filePath;

    public Storage() {
        // This represents ./data/duke.txt
        this.filePath = Paths.get("data", "duke.txt");
    }

    /**
     * Loads raw lines from the save file.
     *
     * @return A list of raw strings read from the save file.
     * @throws FridayException If an I/O error occurs while reading the file.
     */
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

    /**
     * Saves raw lines to the save file.
     *
     * @param lines The list of strings to be written to the save file.
     * @throws FridayException If an I/O error occurs while writing to the file.
     */
    public void save(List<String> lines) throws FridayException{
        try {
            // Create ./data folder if missing
            Files.createDirectories(filePath.getParent());
            // Create duke.txt if missing, then write
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new FridayException("Error saving tasks.");
        }
    }

    /**
     * Loads all tasks from storage and reconstructs them into a {@link TaskList}.
     * Each line in the save file is parsed using the provided {@link Parser}.
     *
     * @param parser The {@code Parser} used to interpret saved task lines.
     * @return A {@code TaskList} containing all loaded tasks.
     * @throws FridayException If the save file is corrupted or cannot be read.
     */    public TaskList loadTaskList(Parser parser) throws FridayException {
        TaskList list = new TaskList();
        for (String line : load()) {
            // If the save file is corrupted, we fail fast (cleanest behavior for now).
            Task task = parser.parseLineToTask(line);
            list.addTask(task);
        }
        return list;
    }

    /**
     * Saves the given {@link TaskList} to storage by converting each task
     * into its save string representation.
     *
     * @param list The {@code TaskList} to be saved.
     * @throws FridayException If an I/O error occurs while saving tasks.
     */    public void saveTaskList(TaskList list) throws FridayException {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            lines.add(list.get(i).toSaveString());
        }
        save(lines);
    }
}

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Files;
import java.util.ArrayList;
import java.io.IOException;

public class Storage {
    private final Path filePath;

    public Storage() {
        // This represents ./data/duke.txt
        this.filePath = Paths.get("data", "duke.txt");
    }

    // Read all saved lines from the file
    public List<String> load() {
        try {
            if (Files.notExists(filePath)) {
                // First run: no file yet
                return new ArrayList<>();
            }
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            // If something goes wrong, start empty
            return new ArrayList<>();
        }
    }

    // Save all task lines to the file
    public void save(List<String> lines) {
        try {
            // Create ./data folder if missing
            Files.createDirectories(filePath.getParent());
            // Create duke.txt if missing, then write
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }
}

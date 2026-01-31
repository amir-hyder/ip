import java.util.Scanner;
import java.util.ArrayList;

public class Friday {
    private static final String INDENTATION = "  ";
    private static final String PAGE_BREAK = "  --------------------------------------------";

    public static void main(String[] args) {
        UI ui = new UI();
        TaskList list = new TaskList();
        Storage storage = new Storage();

        //load the list from memory
        for (String line: storage.load()) {
            try {
                list.addTask(parseLineToTask(line));
            } catch (FridayException e) {
                System.out.println("Skipping bad line: " + e.getMessage());
            }
        }

        ui.greet();

        while (true) {
            String input = ui.readCommand();
            System.out.println(PAGE_BREAK);
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                ui.printList(list);
            } else {
                try {
                    handleCommand(input, list, storage, ui);
                } catch (FridayException e){
                    ui.printException(e);
                }
            }
        }
        ui.bye();
    }

    public static void handleCommand(String input, TaskList list, Storage storage, UI ui) throws FridayException {
        if (input.startsWith("mark")) {
            handleMark(input, list, storage, ui);
        } else if (input.startsWith("unmark")) {
            handleUnmark(input, list, storage, ui);
        } else if (input.startsWith("todo")) {
            handleTodo(input, list, storage, ui);
        } else if (input.startsWith("deadline")) {
            handleDeadline(input, list, storage, ui);
        } else if (input.startsWith("event")) {
            handleEvent(input, list, storage, ui);
        } else if (input.startsWith("delete")) {
            handleDelete(input, list, storage, ui);
        } else {
            throw new FridayException("I don't understand that command");
        }
    }

    public static void handleDelete(String input, TaskList list, Storage storage, UI ui) throws FridayException {
        int index = parseIndex(input);
        Task task = list.get(index - 1);
        list.deleteTask(index - 1);
        saveTasks(list, storage);
        ui.printDelete(task, list);
    }

    public static void handleEvent(String input, TaskList list, Storage storage, UI ui) {
        String noCommand = input.substring(6);
        String[] parts = noCommand.split(" /from ");
        String description = parts[0];
        String[] parts2 = parts[1].split(" /to ");
        String[] dateAndStart = parts2[0].split(" ");
        String date = dateAndStart[0];
        String start = dateAndStart[1];
        String end = parts2[1];
        Event item = new Event(description, date, start, end);
        list.addTask(item);
        ui.printAddTask(item, list);
        saveTasks(list, storage);
    }

    public static void handleDeadline(String input, TaskList list, Storage storage, UI ui) {
        String noCommand = input.substring(9);
        String[] parts = noCommand.split(" /by ");
        String description = parts[0];
        String deadline = parts[1];
        Deadline item = new Deadline(description, deadline);
        list.addTask(item);
        ui.printAddTask(item, list);
        saveTasks(list, storage);
    }

    public static void handleTodo(String input, TaskList list, Storage storage, UI ui) {
        String todoItem = input.substring(5);
        ToDo item = new ToDo(todoItem);
        list.addTask(item);
        ui.printAddTask(item, list);
        saveTasks(list, storage);
    }

    public static void handleMark(String input, TaskList list, Storage storage, UI ui) throws FridayException {
        int index = parseIndex(input);
        Task task = list.get(index - 1);
        task.mark();
        ui.printMarkTask(task);
        saveTasks(list, storage);
    }

    public static void handleUnmark(String input, TaskList list, Storage storage, UI ui) throws FridayException {
        int index = parseIndex(input);
        Task task = list.get(index - 1);
        task.unmark();
        ui.printUnmarkTask(task);
        saveTasks(list, storage);
    }

    public static int parseIndex(String input) throws FridayException {
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
            throw new FridayException("Task number must be a valid integer");
        }
        return index;
    }

    public static void saveTasks(TaskList list, Storage storage) {
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);
            lines.add(task.toSaveString());
        }
        storage.save(lines);
    }

    public static Task parseLineToTask(String line) throws FridayException {
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

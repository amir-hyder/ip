package friday;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

/**
 * The main entry point of the Friday task management application.
 * <p>
 * This class coordinates user interaction, command handling, task management,
 * and persistent storage by delegating responsibilities to {@link UI},
 * {@link Parser}, {@link TaskList}, and {@link Storage}.
 */
public class Friday {
    // Command keywords as constants
    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_FIND = "find";
    private static final String CMD_REMIND = "remind";

    // Shared components
    private final UI ui;
    private final Storage storage;
    private final Parser parser;
    private TaskList list;

    private boolean isExit = false;

    /**
     * Constructs a new Friday instance and initializes
     * the internal application state.
     */
    public Friday() {
        ui = new UI();
        storage = new Storage();
        parser = new Parser();

        try {
            list = storage.loadTaskList(parser);
        } catch (FridayException e) {
            // For GUI, we don't want to crash; start fresh.
            list = new TaskList();
        }
    }

    public boolean isExit() {
        return this.isExit;
    }

    /** Optional: show greeting in GUI at startup. */
    public String getWelcomeMessage() {
        return captureOutput(() -> ui.greet()).trim();
    }

    /**
     * GUI calls this for every user input.
     * Trims and validates input, and dispatches to the appropriate handler.
     *
     * @param input The user input string.
     * @return The response string to display in the GUI.
     */
    public String getResponse(String input) {
        String trimmed = (input == null) ? "" : input.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        return captureOutput(() -> {
            if (trimmed.equals(CMD_BYE)) {
                isExit = true;
                ui.bye();
                return;
            }
            if (trimmed.equals(CMD_LIST)) {
                ui.printList(list);
                return;
            }
            try {
                handleCommand(trimmed, list, storage, ui, parser);
            } catch (FridayException e) {
                ui.printException(e);
            } catch (RuntimeException e) {
                // Print stack trace for debugging
                e.printStackTrace();
                ui.printException(new FridayException("Something went wrong: " + e.getMessage()));
            }
        }).trim();
    }

    /**
     * Starts the Friday application.
     * Initializes required components, loads saved tasks, and processes user commands
     * until the user exits the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Friday friday = new Friday();
        friday.ui.greet();
        while (true) {
            String input = friday.ui.readCommand();
            if (input.trim().equals(CMD_BYE)) {
                break;
            } else if (input.trim().equals(CMD_LIST)) {
                friday.ui.printList(friday.list);
            } else {
                try {
                    handleCommand(input.trim(), friday.list, friday.storage, friday.ui, friday.parser);
                } catch (FridayException e) {
                    friday.ui.printException(e);
                }
            }
        }
        friday.ui.bye();
    }

    /** Captures anything printed to System.out during action.run() and returns it. */
    private String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream tempOut = new PrintStream(baos);

        try {
            System.setOut(tempOut);
            action.run();
        } finally {
            System.out.flush();
            System.setOut(originalOut);
        }
        return baos.toString();
    }

    /**
     * Dispatches the given user command to the appropriate handler method.
     *
     * @param input   The full user command.
     * @param list    The {@link TaskList} containing current tasks.
     * @param storage The {@link Storage} used for saving tasks.
     * @param ui      The {@link UI} used to display messages.
     * @param parser  The {@link Parser} used to parse command details.
     * @throws FridayException If the command is invalid or cannot be processed.
     */
    public static void handleCommand(String input, TaskList list, Storage storage,
                                     UI ui, Parser parser) throws FridayException {
        if (input.startsWith(CMD_MARK)) {
            handleMark(input, list, storage, ui, parser);
        } else if (input.startsWith(CMD_UNMARK)) {
            handleUnmark(input, list, storage, ui, parser);
        } else if (input.startsWith(CMD_TODO)) {
            handleTodo(input, list, storage, ui);
        } else if (input.startsWith(CMD_DEADLINE)) {
            handleDeadline(input, list, storage, ui);
        } else if (input.startsWith(CMD_EVENT)) {
            handleEvent(input, list, storage, ui);
        } else if (input.startsWith(CMD_DELETE)) {
            handleDelete(input, list, storage, ui, parser);
        } else if (input.startsWith(CMD_FIND)) {
            handleFind(input, list, ui);
        } else if (input.startsWith(CMD_REMIND)) {
            handleRemind(input, list, ui);
        } else {
            throw new FridayException("I don't understand that command");
        }
    }

    /**
     * Handles the {@code find} command.
     * Searches for tasks whose descriptions contain the given keyword
     * and displays the matching tasks without modifying the task list.
     *
     * @param input The full user input string.
     * @param list The current {@link TaskList}.
     * @param ui The {@link UI} responsible for displaying output.
     * @throws FridayException If the keyword is missing.
     */
    public static void handleFind(String input, TaskList list, UI ui) throws FridayException {
        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            throw new FridayException("Please provide a keyword to search for.");
        }
        ui.printFindResults(list, keyword);
    }

    /**
     * Handles deletion of a task specified by the user command.
     *
     * @param input   The delete command entered by the user.
     * @param list    The {@link TaskList} containing current tasks.
     * @param storage The {@link Storage} used to save changes.
     * @param ui      The {@link UI} used to display output.
     * @param parser  The {@link Parser} used to parse the task index.
     * @throws FridayException If the command format or index is invalid.
     */
    public static void handleDelete(String input, TaskList list, Storage storage,
                                    UI ui, Parser parser) throws FridayException {
        int index = parser.parseIndex(input);
        if (index < 1 || index > list.size()) {
            throw new FridayException("Task number is out of range");
        }
        Task task = list.get(index - 1);
        list.deleteTask(index - 1); // Now TaskList expects 0-based index
        storage.saveTaskList(list);
        ui.printDelete(task, list);
    }

    /**
     * Handles creation of an event task from the user command.
     *
     * @param input   The event command entered by the user.
     * @param list    The {@link TaskList} to add the event to.
     * @param storage The {@link Storage} used to save changes.
     * @param ui      The {@link UI} used to display output.
     * @throws FridayException If the command format is invalid.
     */
    public static void handleEvent(String input, TaskList list, Storage storage, UI ui) throws FridayException {
        String noCommand = input.substring(CMD_EVENT.length()).trim();
        if (noCommand.isEmpty()) {
            throw new FridayException("Please include an task name for the event.");
        }
        if (!noCommand.contains(" /from ") || !noCommand.contains(" /to ")) {
            throw new FridayException(
                "Invalid event format.\n"
                + "Usage: event <description> /from <yyyy-mm-dd HHmm> /to <HHmm>");
        }
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
        storage.saveTaskList(list);
    }

    /**
     * Handles creation of a deadline task from the user command.
     *
     * @param input   The deadline command entered by the user.
     * @param list    The {@link TaskList} to add the deadline to.
     * @param storage The {@link Storage} used to save changes.
     * @param ui      The {@link UI} used to display output.
     * @throws FridayException If the command format is invalid.
     */
    public static void handleDeadline(String input, TaskList list, Storage storage, UI ui) throws FridayException {
        String noCommand = input.substring(CMD_DEADLINE.length()).trim();
        if (noCommand.isEmpty()) {
            throw new FridayException("Please include a task name for the deadline.");
        }
        if (!noCommand.contains(" /by ")) {
            throw new FridayException(
                "Invalid deadline format.\n"
                + "Usage: deadline <description> /by <yyyy-mm-dd>");
        }
        String[] parts = noCommand.split(" /by ");
        String description = parts[0];
        String deadline = parts[1];
        Deadline item = new Deadline(description, deadline);
        list.addTask(item);
        ui.printAddTask(item, list);
        storage.saveTaskList(list);
    }

    /**
     * Handles creation of a to-do task from the user command.
     *
     * @param input   The to-do command entered by the user.
     * @param list    The {@link TaskList} to add the task to.
     * @param storage The {@link Storage} used to save changes.
     * @param ui      The {@link UI} used to display output.
     * @throws FridayException If the command format is invalid.
     */
    public static void handleTodo(String input, TaskList list, Storage storage, UI ui) throws FridayException {
        String todoItem = input.substring(CMD_TODO.length()).trim();
        if (todoItem.trim().isEmpty()) {
            throw new FridayException("Please include a task name for todo.");
        }
        ToDo item = new ToDo(todoItem);
        list.addTask(item);
        ui.printAddTask(item, list);
        storage.saveTaskList(list);
    }

    /**
     * Handles marking a task as completed.
     *
     * @param input   The mark command entered by the user.
     * @param list    The {@link TaskList} containing current tasks.
     * @param storage The {@link Storage} used to save changes.
     * @param ui      The {@link UI} used to display output.
     * @param parser  The {@link Parser} used to parse the task index.
     * @throws FridayException If the command format or index is invalid.
     */
    public static void handleMark(String input, TaskList list, Storage storage,
                                  UI ui, Parser parser) throws FridayException {
        int index = parser.parseIndex(input);
        Task task = list.get(index - 1);
        task.mark();
        ui.printMarkTask(task);
        storage.saveTaskList(list);
    }

    /**
     * Handles unmarking a completed task.
     *
     * @param input   The unmark command entered by the user.
     * @param list    The {@link TaskList} containing current tasks.
     * @param storage The {@link Storage} used to save changes.
     * @param ui      The {@link UI} used to display output.
     * @param parser  The {@link Parser} used to parse the task index.
     * @throws FridayException If the command format or index is invalid.
     */
    public static void handleUnmark(String input, TaskList list, Storage storage,
                                    UI ui, Parser parser) throws FridayException {
        int index = parser.parseIndex(input);
        Task task = list.get(index - 1);
        task.unmark();
        ui.printUnmarkTask(task);
        storage.saveTaskList(list);
    }

    /**
     * Handles the {@code remind} command.
     * <p>
     * Displays tasks occurring within the specified number of days
     * starting from the current date. If no number is provided,
     * a default reminder window of 7 days is used.
     *
     * @param input The full user input string.
     * @param list  The {@link TaskList} containing current tasks.
     * @param ui    The {@link UI} used to display output.
     * @throws FridayException If the input format is invalid.
     */
    public static void handleRemind(String input, TaskList list, UI ui) throws FridayException {
        String rest = input.length() > 6 ? input.substring(6).trim() : "";
        int days = 7;

        if (!rest.isEmpty()) {
            try {
                days = Integer.parseInt(rest);
            } catch (NumberFormatException e) {
                throw new FridayException("Usage: remind OR remind <days>");
            }
            if (days < 0) {
                throw new FridayException("Days must be non-negative.");
            }
        }

        var upcoming = list.getUpcomingTasks(LocalDate.now(), days);
        ui.printReminders(upcoming, days);
    }
}

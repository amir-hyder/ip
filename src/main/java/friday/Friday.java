package friday;

/**
 * The main entry point of the Friday task management application.
 * <p>
 * This class coordinates user interaction, command handling, task management,
 * and persistent storage by delegating responsibilities to {@link UI},
 * {@link Parser}, {@link TaskList}, and {@link Storage}.
 */
public class Friday {
    private static final String PAGE_BREAK = "  --------------------------------------------";

    /**
     * Starts the Friday application.
     * Initializes required components, loads saved tasks, and processes user commands
     * until the user exits the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        UI ui = new UI();
        Storage storage = new Storage();
        Parser parser = new Parser();

        //load the list from memory
        TaskList list;
        try {
            list = storage.loadTaskList(parser);
        } catch (FridayException e) {
            ui.printException(e);
            list = new TaskList();
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
                    handleCommand(input, list, storage, ui, parser);
                } catch (FridayException e) {
                    ui.printException(e);
                }
            }
        }
        ui.bye();
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
        if (input.startsWith("mark")) {
            handleMark(input, list, storage, ui, parser);
        } else if (input.startsWith("unmark")) {
            handleUnmark(input, list, storage, ui, parser);
        } else if (input.startsWith("todo")) {
            handleTodo(input, list, storage, ui);
        } else if (input.startsWith("deadline")) {
            handleDeadline(input, list, storage, ui);
        } else if (input.startsWith("event")) {
            handleEvent(input, list, storage, ui);
        } else if (input.startsWith("delete")) {
            handleDelete(input, list, storage, ui, parser);
        } else if (input.startsWith("find")) {
            handleFind(input, list, ui);
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
        Task task = list.get(index - 1);
        list.deleteTask(index - 1);
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
        String noCommand = input.substring(9);
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
        String todoItem = input.substring(5);
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
}

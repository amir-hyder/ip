package Friday;

public class Friday {
    private static final String PAGE_BREAK = "  --------------------------------------------";

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

    public static void handleCommand(String input, TaskList list, Storage storage, UI ui, Parser parser) throws FridayException {
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
        } else {
            throw new FridayException("I don't understand that command");
        }
    }

    public static void handleDelete(String input, TaskList list, Storage storage, UI ui, Parser parser) throws FridayException {
        int index = parser.parseIndex(input);
        Task task = list.get(index - 1);
        list.deleteTask(index - 1);
        storage.saveTaskList(list);
        ui.printDelete(task, list);
    }

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

    public static void handleTodo(String input, TaskList list, Storage storage, UI ui) throws FridayException {
        String todoItem = input.substring(5);
        ToDo item = new ToDo(todoItem);
        list.addTask(item);
        ui.printAddTask(item, list);
        storage.saveTaskList(list);
    }

    public static void handleMark(String input, TaskList list, Storage storage, UI ui, Parser parser) throws FridayException {
        int index = parser.parseIndex(input);
        Task task = list.get(index - 1);
        task.mark();
        ui.printMarkTask(task);
        storage.saveTaskList(list);
    }

    public static void handleUnmark(String input, TaskList list, Storage storage, UI ui, Parser parser) throws FridayException {
        int index = parser.parseIndex(input);
        Task task = list.get(index - 1);
        task.unmark();
        ui.printUnmarkTask(task);
        storage.saveTaskList(list);
    }
}

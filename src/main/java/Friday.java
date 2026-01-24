import java.util.Scanner;
import java.util.ArrayList;

public class Friday {
    private static final String LINE = "--------------------------------------------";
    private static final String INDENTATION = "  ";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

       greet();

        while (true) {
            String input = sc.nextLine();
            System.out.println(INDENTATION + LINE);
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                printList(list);
            } else if (input.startsWith("unmark")) {
                try {
                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        throw new FridayException("Please specify which task number you would like to unmark");
                    }
                    if (parts.length > 2) {
                        throw new FridayException("You have too many commands! Just include which task number you would like us to unmark");
                    }
                    int index;
                    try {
                        index = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        throw new FridayException("Task number must be a valid integer");
                    }
                    Task task = list.get(index - 1);
                    unmark(task, index);
                } catch (FridayException e) {
                    System.out.println(e.getMessage());
                    System.out.println(INDENTATION + LINE);
                }
            } else if (input.contains("mark")) {
                try {
                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        throw new FridayException("Please specify which task number you would like to mark");
                    }
                    if (parts.length > 2) {
                        throw new FridayException("You have too many commands! Just include which task number you would like us to mark");
                    }
                    int index;
                    try {
                        index = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        throw new FridayException("Task number must be a valid integer");
                    }
                    Task task = list.get(index - 1);
                    mark(task, index);
                } catch (FridayException e){
                    System.out.println(e.getMessage());
                    System.out.println(INDENTATION + LINE);
                }
            } else if (input.contains("todo")) {
                String todoItem = input.substring(5);
                ToDo item = new ToDo(todoItem);
                list.add(item);
                int size = list.size();
                printAddTask(item, size);
            } else if (input.contains("deadline")) {
                String noCommand = input.substring(9);
                String[] parts = noCommand.split(" /by ");
                String description = parts[0];
                String deadline = parts[1];
                Deadline item = new Deadline(description, deadline);
                list.add(item);
                int size = list.size();
                printAddTask(item, size);
            } else if (input.contains("event")) {
                String noCommand = input.substring(6);
                String[] parts = noCommand.split(" /from ");
                String description = parts[0];
                String[] parts2 = parts[1].split(" /to ");
                String start = parts2[0];
                String end = parts2[1];
                Event item = new Event(description, start, end);
                list.add(item);
                int size = list.size();
                printAddTask(item, size);
            } else if (input.startsWith("delete")) {
                try {
                    String[] parts = input.split(" ");
                    if (parts.length < 2) {
                        throw new FridayException("Specify which task number you would like to delete");
                    }
                    if (parts.length > 2) {
                        throw new FridayException("You have too many commands! Just include which task number you would like to delete");
                    }
                    int index;
                    try {
                        index = Integer.parseInt(parts[1]) - 1;
                    } catch (NumberFormatException e) {
                        throw new FridayException("Task number must be a valid integer");
                    }
                    Task task = list.get(index);
                    list.remove(index);
                    System.out.println(INDENTATION + "Noted. I've removed this task:");
                    System.out.println(INDENTATION + INDENTATION + task.toString());
                    System.out.println(INDENTATION + "Now you have " + list.size() + " tasks in the list.");
                    System.out.println(INDENTATION + LINE);
                } catch (FridayException e) {
                    System.out.println(e.getMessage());
                    System.out.println(INDENTATION + LINE);
                }
            } else {
                return;
            }
        }
        goodbye();
    }

    public static void greet() {
        System.out.println(INDENTATION + LINE);
        System.out.println(INDENTATION + "Hello! I'm Friday");
        System.out.println(INDENTATION + "What can I do for you?");
        System.out.println(INDENTATION + LINE);
    }

    public static void goodbye() {
        System.out.println(INDENTATION + "Bye. Hope to see you again soon!");
        System.out.println(INDENTATION + LINE);
    }

    public static void printList(ArrayList<Task> list) {
        System.out.println(INDENTATION + "Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(INDENTATION + (i + 1) + ". " + list.get(i));
        }
        System.out.println(INDENTATION + LINE);
    }

    public static void unmark(Task task, int index) {
        task.unmark();
        System.out.println(INDENTATION + "OK, I've marked this task as not done yet:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(INDENTATION + LINE);
    }

    public static void mark(Task task, int index) {
        task.mark();
        System.out.println(INDENTATION + "Nice! I've marked this task as done:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(INDENTATION + LINE);
    }

    public static void printAddTask(Task task, int size) {
        System.out.println(INDENTATION + "Got it. I've added this task:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(INDENTATION + "Now you have " + size + " tasks in the list.");
        System.out.println(INDENTATION + LINE);
    }
}

package friday;

import java.util.List;
import java.util.Scanner;

/**
 * Represents the user interface of the chatbot.
 * Handles reading commands from the user and printing output messages.
 */
public class UI {
    private static final String INDENTATION = "    ";
    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return The full command line as a {@code String}.
     */
    public String readCommand() {
        return this.scanner.nextLine();
    }

    /**
     * Prints the greeting message when the application starts.
     */
    public void greet() {
        System.out.println("Hello! I'm Friday.");
        System.out.println("What can I do for you?");
    }

    /**
     * Prints the farewell message when the application exits.
     */
    public void bye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints the list of tasks currently stored in the task list.
     *
     * @param list The {@code TaskList} containing all tasks to be displayed. Must not be null.
     */
    public void printList(TaskList list) {
        if (list == null) {
            System.out.println("No tasks to display (list is empty).");
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            try {
                System.out.println((i + 1) + ". " + list.get(i));
            } catch (FridayException e) {
                System.out.println("Error displaying task at index " + i + ": " + e.getMessage());
            }
        }
    }

    /**
     * Prints a confirmation message after a task is added.
     *
     * @param task The {@code Task} that was added. Must not be null.
     * @param list The {@code TaskList} containing the updated list of tasks. Must not be null.
     */
    public void printAddTask(Task task, TaskList list) {
        if (task == null || list == null) {
            System.out.println("Error: Task or list is null.");
            return;
        }
        System.out.println("Got it. I've added this task:");
        System.out.println(INDENTATION + task.toString());
        System.out.println("Now you have " + list.size() + " tasks in the list.");
    }

    /**
     * Prints a confirmation message after a task is marked as done.
     *
     * @param task The {@code Task} that was marked as completed. Must not be null.
     */
    public void printMarkTask(Task task) {
        if (task == null) {
            System.out.println("Error: Task is null.");
            return;
        }
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(INDENTATION + task.toString());
    }

    /**
     * Prints a confirmation message after a task is marked as not done.
     *
     * @param task The {@code Task} that was unmarked. Must not be null.
     */
    public void printUnmarkTask(Task task) {
        if (task == null) {
            System.out.println("Error: Task is null.");
            return;
        }
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(INDENTATION + task.toString());
    }

    /**
     * Prints a confirmation message after a task is deleted.
     *
     * @param task The {@code Task} that was removed. Must not be null.
     * @param list The {@code TaskList} after deletion. Must not be null.
     */
    public void printDelete(Task task, TaskList list) {
        if (task == null || list == null) {
            System.out.println("Error: Task or list is null.");
            return;
        }
        System.out.println("Noted. I've removed this task:");
        System.out.println(INDENTATION + task.toString());
        System.out.println("Now you have " + list.size() + " tasks in the list.");
    }

    /**
     * Prints an error message to inform the user of an exception.
     *
     * @param e The {@code Exception} containing the error message. Must not be null.
     */
    public void printException(Exception e) {
        if (e == null) {
            System.out.println("Unknown error occurred (exception is null).");
            return;
        }
        System.out.println(e.getMessage());
    }

    /**
     * Prints all tasks whose descriptions match the given keyword.
     *
     * @param list The {@link TaskList} to search from. Must not be null.
     * @param keyword The keyword to search for. Must not be null.
     */
    public void printFindResults(TaskList list, String keyword) {
        if (list == null || keyword == null) {
            System.out.println("Error: List or keyword is null.");
            return;
        }
        System.out.println("Here are the matching tasks in your list:");
        List<Task> matches = list.findTasks(keyword);
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i));
        }
    }

    /**
     * Displays a list of upcoming tasks occurring within the specified number of days.
     *
     * @param tasks The list of upcoming tasks. Must not be null.
     * @param days  The number of days used for the reminder window.
     */
    public void printReminders(List<Task> tasks, int days) {
        if (tasks == null) {
            System.out.println("Error: Task list is null.");
            return;
        }
        if (tasks.isEmpty()) {
            System.out.println("No upcoming tasks in the next " + days + " days.");
            return;
        }
        System.out.println("Here are your upcoming tasks in the next " + days + " days:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

}

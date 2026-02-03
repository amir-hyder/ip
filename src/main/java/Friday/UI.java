package Friday;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Represents the user interface of the chatbot.
 * Handles reading commands from the user and printing output messages.
 */
public class UI {
    private static final String INDENTATION = "  ";
    private static final String PAGE_BREAK = "  --------------------------------------------";

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
        System.out.println(PAGE_BREAK);
        System.out.println(INDENTATION + "Hello! I'm Friday.");
        System.out.println(INDENTATION + "What can I do for you?");
        System.out.println(PAGE_BREAK);
    }

    /**
     * Prints the farewell message when the application exits.
     */
    public void bye() {
        System.out.println(INDENTATION + "Bye. Hope to see you again soon!");
        System.out.println(PAGE_BREAK);
    }

    /**
     * Prints the list of tasks currently stored in the task list.
     *
     * @param list The {@code TaskList} containing all tasks to be displayed.
     */
    public void printList(TaskList list) {
        System.out.println(INDENTATION + "Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(INDENTATION + (i + 1) + ". " + list.get(i));
        }
        System.out.println(PAGE_BREAK);
    }

    /**
     * Prints a confirmation message after a task is added.
     *
     * @param task The {@code Task} that was added.
     * @param list The {@code TaskList} containing the updated list of tasks.
     */
    public void printAddTask(Task task, TaskList list) {
        System.out.println(INDENTATION + "Got it. I've added this task:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(INDENTATION + "Now you have " + list.size() + " tasks in the list.");
        System.out.println(PAGE_BREAK);
    }

    /**
     * Prints a confirmation message after a task is marked as done.
     *
     * @param task The {@code Task} that was marked as completed.
     */
    public void printMarkTask(Task task) {
        System.out.println(INDENTATION + "Nice! I've marked this task as done:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(PAGE_BREAK);
    }

    /**
     * Prints a confirmation message after a task is marked as not done.
     *
     * @param task The {@code Task} that was unmarked.
     */
    public void printUnmarkTask(Task task) {
        System.out.println(INDENTATION + "OK, I've marked this task as not done yet:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(PAGE_BREAK);
    }

    /**
     * Prints a confirmation message after a task is deleted.
     *
     * @param task The {@code Task} that was removed.
     * @param list The {@code TaskList} after deletion.
     */
    public void printDelete(Task task, TaskList list) {
        System.out.println(INDENTATION + "Noted. I've removed this task:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(INDENTATION + "Now you have " + list.size() + " tasks in the list.");
        System.out.println(PAGE_BREAK);
    }

    /**
     * Prints an error message to inform the user of an exception.
     *
     * @param e The {@code Exception} containing the error message.
     */
    public void printException(Exception e) {
        System.out.println(e.getMessage());
        System.out.println(PAGE_BREAK);
    }

    // existing fields + methods unchanged

    /**
     * Prints all tasks whose descriptions match the given keyword.
     *
     * @param list The {@link TaskList} to search from.
     * @param keyword The keyword to search for.
     */
    public void printFindResults(TaskList list, String keyword) {
        System.out.println(PAGE_BREAK);
        System.out.println(INDENTATION + "Here are the matching tasks in your list:");

        ArrayList<Task> matches = list.findTasks(keyword);

        for (int i = 0; i < matches.size(); i++) {
            System.out.println(INDENTATION + (i + 1) + "." + matches.get(i));
        }

        System.out.println(PAGE_BREAK);
    }
}

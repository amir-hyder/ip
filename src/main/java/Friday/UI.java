package Friday;

import java.util.Scanner;
import java.util.ArrayList;

public class UI {
    private static final String INDENTATION = "  ";
    private static final String PAGE_BREAK = "  --------------------------------------------";

    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return this.scanner.nextLine();
    }

    public void greet() {
        System.out.println(PAGE_BREAK);
        System.out.println(INDENTATION + "Hello! I'm Friday.");
        System.out.println(INDENTATION + "What can I do for you?");
        System.out.println(PAGE_BREAK);
    }

    public void bye() {
        System.out.println(INDENTATION + "Bye. Hope to see you again soon!");
        System.out.println(PAGE_BREAK);
    }

    public void printList(TaskList list) {
        System.out.println(INDENTATION + "Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(INDENTATION + (i + 1) + ". " + list.get(i));
        }
        System.out.println(PAGE_BREAK);
    }

    public void printAddTask(Task task, TaskList list) {
        System.out.println(INDENTATION + "Got it. I've added this task:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(INDENTATION + "Now you have " + list.size() + " tasks in the list.");
        System.out.println(PAGE_BREAK);
    }

    public void printMarkTask(Task task) {
        System.out.println(INDENTATION + "Nice! I've marked this task as done:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(PAGE_BREAK);
    }

    public void printUnmarkTask(Task task) {
        System.out.println(INDENTATION + "OK, I've marked this task as not done yet:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(PAGE_BREAK);
    }

    public void printDelete(Task task, TaskList list) {
        System.out.println(INDENTATION + "Noted. I've removed this task:");
        System.out.println(INDENTATION + INDENTATION + task.toString());
        System.out.println(INDENTATION + "Now you have " + list.size() + " tasks in the list.");
        System.out.println(PAGE_BREAK);
    }

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

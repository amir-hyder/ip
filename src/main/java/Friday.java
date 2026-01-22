import java.util.Scanner;
import java.util.ArrayList;

public class Friday {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        String line = "--------------------------------------------";
        String indentation = "  ";
        System.out.println(indentation + line);
        System.out.println(indentation + "Hello! I'm Friday");
        System.out.println(indentation + "What can I do for you?");
        System.out.println(indentation + line);

        while (true) {
            String input = sc.nextLine();
            System.out.println(indentation + line);
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.println(indentation + "Here are the tasks in your list:");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(indentation + (i + 1) + ". " + list.get(i));
                }
                System.out.println(indentation + line);
            } else if (input.contains("unmark")) {
                int index = Integer.parseInt(input.split(" ")[1]);
                Task task = list.get(index - 1);
                task.unmark();
                System.out.println(indentation + "OK, I've marked this task as not done yet:");
                System.out.println(indentation + indentation + task.toString());
                System.out.println(indentation + line);
            } else if (input.contains("mark")) {
                int index = Integer.parseInt(input.split(" ")[1]);
                Task task = list.get(index - 1);
                task.mark();
                System.out.println(indentation + "Nice! I've marked this task as done:");
                System.out.println(indentation + indentation + task.toString());
                System.out.println(indentation + line);
            } else {
                System.out.println(indentation + "added: " + input);
                System.out.println(indentation + line);
                Task task = new Task(input);
                list.add(task);
            }
        }
        System.out.println(indentation + "Bye. Hope to see you again soon!");
        System.out.println(indentation + line);
    }
}

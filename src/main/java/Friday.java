import java.util.Scanner;

public class Friday {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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
            } else {
                System.out.println(indentation + input);
                System.out.println(indentation + line);
            }
        }
        System.out.println(indentation + "Bye. Hope to see you again soon!");
        System.out.println(indentation + line);
    }
}

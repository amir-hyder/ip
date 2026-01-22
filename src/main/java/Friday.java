import java.util.Scanner;

public class Friday {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] list = new String[100];
        int index = 0;

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
                for (int i = 0; i < list.length; i++) {
                    if (list[i] != null) {
                        System.out.println(indentation + (i + 1) + ". " + list[i]);
                    }
                }
                System.out.println(indentation + line);
            } else {
                System.out.println(indentation + "added: " + input);
                System.out.println(indentation + line);
                list[index] = input;
                index++;
            }
        }
        System.out.println(indentation + "Bye. Hope to see you again soon!");
        System.out.println(indentation + line);
    }
}

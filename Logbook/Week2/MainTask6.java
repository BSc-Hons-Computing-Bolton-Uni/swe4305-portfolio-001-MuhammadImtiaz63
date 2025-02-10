package Logbook.Week2;
import java.util.Scanner;

public class MainTask6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char choice;

        do {
            System.out.print("Enter the number to see its multiplication table: ");
            int number = scanner.nextInt();

            for (int i = 1; i <= 12; i++) {
                System.out.println(i + " x " + number + " = " + (i * number));
            }

            System.out.print("Would you like to see another multiplication table? (y/n): ");
            choice = scanner.next().toLowerCase().charAt(0);
        } while (choice == 'y');

        System.out.println("Goodbye! Have a great day!");
    }
}

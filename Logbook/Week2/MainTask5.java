package Logbook.Week2;
import java.util.Scanner;

public class MainTask5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number to see its multiplication table: ");
        int number = scanner.nextInt();

        for (int i = 1; i <= 12; i++) {
            System.out.println(i + " x " + number + " = " + (i * number));
        }
    }
}

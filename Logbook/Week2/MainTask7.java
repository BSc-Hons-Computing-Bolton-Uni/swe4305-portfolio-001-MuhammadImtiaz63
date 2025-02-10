package Logbook.Week2;
import java.util.Scanner;

public class MainTask7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your exam mark (0-100): ");
        int mark = scanner.nextInt();

        if (mark >= 90 && mark <= 100) {
            System.out.println("Grade: A");
        } else if (mark >= 80 && mark < 90) {
            System.out.println("Grade: B");
        } else if (mark >= 70 && mark < 80) {
            System.out.println("Grade: C");
        } else if (mark >= 60 && mark < 70) {
            System.out.println("Grade: D");
        } else if (mark >= 50 && mark < 60) {
            System.out.println("Grade: E");
        } else if (mark >= 0 && mark < 50) {
            System.out.println("Grade: F (Fail)");
        } else {
            System.out.println("Invalid mark entered. Please enter a value between 0 and 100.");
        }
    }
}

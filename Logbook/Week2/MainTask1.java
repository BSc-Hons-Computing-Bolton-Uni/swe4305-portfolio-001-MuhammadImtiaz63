package Logbook.Week2;
import java.util.Scanner;

public class MainTask1 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        int currentYear = java.time.Year.now().getValue();
        int birthYear = currentYear - age;

        System.out.println("You were born in: " + birthYear);

        if (age >= 18) {
            System.out.println("You are 18 or older.");
        } else {
            System.out.println("You are under 18.");
        }
    }
}

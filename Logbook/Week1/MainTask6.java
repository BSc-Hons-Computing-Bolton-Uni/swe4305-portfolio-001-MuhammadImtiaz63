package Logbook.Week1;
import java.util.Scanner;

public class MainTask6 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        int currentYear = java.time.Year.now().getValue();
        int birthYear = currentYear - age;

        System.out.println("You were born in: " + birthYear);
    }
}

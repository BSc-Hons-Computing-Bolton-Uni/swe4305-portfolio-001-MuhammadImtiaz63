package Logbook.Week2;
import java.util.Scanner;

public class MainTask2  {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your letter grade (A, B, C, D, E, or F): ");
        char grade = scanner.next().toUpperCase().charAt(0);

        String classification;
        switch (grade) {
            case 'A':
                classification = "1st";
                break;
            case 'B':
                classification = "2:1";
                break;
            case 'C':
                classification = "2:2";
                break;
            case 'D':
                classification = "3rd";
                break;
            case 'E':
                classification = "Ordinary";
                break;
            case 'F':
                classification = "Fail";
                break;
            default:
                classification = "Invalid grade entered";
        }

        System.out.println("Your classification is: " + classification);
    }
}

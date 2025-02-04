package Logbook.Week1;
import java.util.Scanner;


public class MainTask9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the deposit amount: ");
        double deposit = scanner.nextDouble();

        double interestRate = 0.01;
        double interest = deposit * interestRate;
        double totalAmount = deposit + interest;

        System.out.println("After 1 year at 1% interest, you will have: " + totalAmount);
    }
}

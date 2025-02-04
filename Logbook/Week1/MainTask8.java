package Logbook.Week1;
import java.util.Scanner;

public class MainTask8 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of feet: ");
        double feet = scanner.nextDouble();

        double miles = feet / 5280;
        System.out.println(feet + " feet is " + miles + " miles.");
    }
}

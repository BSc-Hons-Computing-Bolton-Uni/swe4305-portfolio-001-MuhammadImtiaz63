package Logbook.Week1;
import java.util.Scanner;

public class MainTask3 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the length of the rectangle: ");
        double length = scanner.nextDouble();
        System.out.print("Enter the height of the rectangle: ");
        double height = scanner.nextDouble();

        double perimeter = 2 * (length + height);
        double area = length * height;

        System.out.println("Perimeter: " + perimeter);
        System.out.println("Area: " + area);
    }
}

package Logbook.Week1;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


public class MainTask7 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your date of birth (dd-mm-yyyy): ");
        String dobInput = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateOfBirth = LocalDate.parse(dobInput, formatter);
        LocalDate currentDate = LocalDate.now();

        Period period = Period.between (dateOfBirth, currentDate);
        int daysOld = period.getYears() * 365 + period.getMonths() * 30+ period.getDays();

        System.out.println("You are approximately " + daysOld + " days old.");

        scanner.close();

    }
}

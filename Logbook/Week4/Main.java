package Logbook.Week4;

import java.util.ArrayList;
import java.util.Random;

enum Grade {
    A, B, C, D, F
}

class Module {
    private String name;
    private String code;

    public Module(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Grade getGrade(int mark) {
        if (mark >= 85) {
            return Grade.A;
        } else if (mark >= 70) {
            return Grade.B;
        } else if (mark >= 50) {
            return Grade.C;
        } else if (mark >= 40) {
            return Grade.D;
        } else {
            return Grade.F;
        }
    }

    public void printModule(int mark) {
        Grade grade = getGrade(mark);
        System.out.println("Module: " + name + " (" + code + ") - Mark: " + mark + " Grade: " + grade);
    }
}

class Course {
    private String code;
    private String name;
    private ArrayList<Module> modules;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
        modules = new ArrayList<>();


        modules.add(new Module("Mathematics", "MATH101"));
        modules.add(new Module("Computer Science", "CS101"));
        modules.add(new Module("Physics", "PHY101"));
        modules.add(new Module("Chemistry", "CHEM101"));
    }

    public void printCourse() {
        System.out.println("Course Code: " + code);
        System.out.println("Course Name: " + name);
        System.out.println("Modules:");
        for (Module module : modules) {
            System.out.println("- " + module.getName() + " (" + module.getCode() + ")");
        }
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
}

class Student {
    private int ID;
    private String name;
    private int[] marks = new int[4];  // Array to store marks
    private Course course;

    public Student(int ID, String name, Course course) {
        this.ID = ID;
        this.name = name;
        this.course = course;

        // Assign random marks to each module
        Random random = new Random();
        for (int i = 0; i < marks.length; i++) {
            marks[i] = random.nextInt(101);  // Random mark between 0 and 100
        }
    }

    public void print() {
        System.out.println("Student ID: " + ID);
        System.out.println("Student Name: " + name);
        System.out.println("Course: " + course.getModules().get(0).getName()); // Just print the course name
        System.out.println("Modules and Marks:");
        ArrayList<Module> modules = course.getModules();
        for (int i = 0; i < modules.size(); i++) {
            modules.get(i).printModule(marks[i]);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Course course1 = new Course("CS100", "Computer Science");

        Student student1 = new Student(100167880, "Paul Sanders", course1);

        student1.print();
    }
}

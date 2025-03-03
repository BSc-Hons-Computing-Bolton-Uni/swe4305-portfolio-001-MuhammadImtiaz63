package Logbook.Week3;

class Student {
    private int ID;
    private String name;
    private Course course;

    public Student(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public void enrol(Course course) {
        this.course = course;
    }

    public void print() {
        System.out.println("Student ID: " + ID);
        System.out.println("Student Name: " + name);
        if (course != null) {
            System.out.println("Enrolled Course:");
            course.print();
        } else {
            System.out.println("No course enrolled.");
        }
    }
}

class Course {
    private String code;
    private String name;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public void print() {
        System.out.println("Course Code: " + code);
        System.out.println("Course Name: " + name);
    }
}

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student(100167880, "Paul Sanders");
        Course course1 = new Course("CS100", "Computer Science");

        student1.enrol(course1);
        student1.print();
    }
}



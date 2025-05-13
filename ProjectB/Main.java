package ProjectB;
import java.util.*;

public class Main {
    // Student class to store student details and their modules
    static class Student {
        private int studentId;
        private String name;
        private List<Module> modules = new ArrayList<>();
        public Student(int studentId, String name) {this.studentId = studentId; this.name = name;}
        public int getStudentId() {return studentId;}
        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        public void setStudentId(int studentId) {this.studentId = studentId;}
        public List<Module> getModules() {return modules;}
        public void addModule(Module module) {modules.add(module);}
        public void removeModuleById(int moduleId) {modules.removeIf(m -> m.getModuleId() == moduleId);}
    }

    // Module class to store module details and calculate grades
    static class Module {
        private int moduleId;
        private String moduleCode;
        private String moduleName;
        private double mark;
        public Module(int moduleId, String moduleCode, String moduleName, double mark) {this.moduleId = moduleId; this.moduleCode = moduleCode; this.moduleName = moduleName; this.mark = mark;}
        public int getModuleId() {return moduleId;}
        public String getModuleCode() {return moduleCode;}
        public String getModuleName() {return moduleName;}
        public double getMark() {return mark;}
        public void setMark(double mark) {this.mark = mark;}
        public String calculateGrade() {
            if (mark >= 70) return "A (First Class)";
            else if (mark >= 60) return "B (Upper Second Class)";
            else if (mark >= 50) return "C (Lower Second Class)";
            else if (mark >= 40) return "D (Third Class)";
            else return "F (Fail)";
        }
    }

    static List<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static Map<String, String> validatedModules = Map.ofEntries(
            Map.entry("COM4301", "Maths for Computing"), Map.entry("COM4302", "Computer Science Fundamentals"),
            Map.entry("SWE4303", "Computing Infrastructure"), Map.entry("SWE4304", "Databases"),
            Map.entry("SWE4305", "Object Oriented Programming"), Map.entry("SWE5306", "Systems Analysis and Design"),
            Map.entry("SWE5307", "Web Design and Programming"), Map.entry("SWE5308", "Cloud Technologies"),
            Map.entry("SWE5304", "Advanced Databases and Big Data"), Map.entry("SEC5304", "Advanced Operating Systems"),
            Map.entry("AIN5301", "Introduction to AI"), Map.entry("COM6300", "Research and Professional Issues"),
            Map.entry("COM6301", "Undergraduate Project"), Map.entry("AIN6301", "Natural Language Processing"),
            Map.entry("SEC6302", "Information Security Management"), Map.entry("SEC6305", "Operations Management"),
            Map.entry("SWE6302", "Applied Machine Learning"), Map.entry("SWE6303", "Software Quality Management"),
            Map.entry("SWE6304", "Emerging Technologies")
    );

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n= Student Grade System Menu =");
                System.out.println("1. Add Student\n2. Edit Student or Modules\n3. Delete Modules\n4. Delete Student\n5. Update Student Marks for a Module\n6. Calculate and Display Student Grades\n7. Calculate and Display Mean, Min, Max for Each Module\n8. Display Grade Profile per Module\n9. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (students.isEmpty() && choice > 1 && choice < 9) {System.out.println("No students added yet! Please add a student first."); continue;}
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> editStudentOrModule();
                    case 3 -> deleteModules();
                    case 4 -> deleteStudent();
                    case 5 -> updateMarks();
                    case 6 -> displayStudentGrades();
                    case 7 -> displayMeanMinMaxForModules();
                    case 8 -> displayGradeProfile();
                    case 9 -> {System.out.println("Exiting"); return;}
                    default -> System.out.println("Invalid choice! Please select a number between 1 and 9.");
                }
            } catch (NumberFormatException e) {System.out.println("Invalid input! Please enter a number.");}
        }
    }

    // Adds a new student with modules
    private static void addStudent() {
        int studentId = 0;
        boolean validId = false;
        int maxAttempts = 3;
        int attempts = 0;
        while (!validId && attempts < maxAttempts) {
            System.out.print("Enter student ID: ");
            try {
                studentId = Integer.parseInt(scanner.nextLine());
                if (findStudentById(studentId) != null) {System.out.println("ID already in use! Please enter a different ID."); attempts++; continue;}
                validId = true;
            } catch (NumberFormatException e) {System.out.println("Invalid ID! Please enter a number."); attempts++;}
        }
        if (!validId) {System.out.println("Too many invalid attempts. Cancelling student addition."); return;}
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();
        Student student = new Student(studentId, studentName);
        int numModules = -1;
        int maxModules = validatedModules.size();
        while (numModules < 0 || numModules > maxModules) {
            System.out.print("How many modules do you want to add for this student (0 to " + maxModules + ")? ");
            try {
                numModules = Integer.parseInt(scanner.nextLine());
                if (numModules < 0 || numModules > maxModules) {System.out.println("Invalid number! You can only add between 0 and " + maxModules + " modules.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input! Please enter a number."); numModules = -1;}
        }
        for (int i = 0; i < numModules; i++) {
            System.out.println("Available Modules:");
            for (Map.Entry<String, String> entry : validatedModules.entrySet()) {System.out.println(entry.getKey() + " - " + entry.getValue());}
            String moduleCode = null;
            while (moduleCode == null) {
                System.out.print("Enter module code from the above list: ");
                moduleCode = scanner.nextLine().toUpperCase();
                if (!validatedModules.containsKey(moduleCode)) {System.out.println("Invalid module code! Please choose a valid module."); moduleCode = null; continue;}
                if (isModuleAlreadyAdded(student, moduleCode)) {System.out.println("Module " + moduleCode + " is already added. Please choose another one."); moduleCode = null; continue;}
            }
            String moduleName = validatedModules.get(moduleCode);
            double mark = -1;
            while (mark < 0 || mark > 100) {
                System.out.print("Enter mark for " + moduleCode + " (between 0 and 100): ");
                try {
                    mark = Double.parseDouble(scanner.nextLine());
                    if (mark < 0 || mark > 100) {System.out.println("Invalid mark. Please enter a mark between 0 and 100.");}
                } catch (NumberFormatException e) {System.out.println("Invalid mark! Please enter a valid number.");}
            }
            int moduleId = moduleCode.hashCode();
            student.addModule(new Module(moduleId, moduleCode, moduleName, mark));
            System.out.println("Module " + moduleName + " added.");
        }
        students.add(student);
        System.out.println("Student added along with " + numModules + " module(s).");
    }

    // Checks if a module is already added to a student
    private static boolean isModuleAlreadyAdded(Student student, String moduleCode) {
        for (Module module : student.getModules()) {if (module.getModuleCode().equalsIgnoreCase(moduleCode)) return true;}
        return false;
    }

    // Adds a module to an existing student
    private static boolean addModuleToStudent(Student student) {
        System.out.println("Available Modules:");
        for (Map.Entry<String, String> entry : validatedModules.entrySet()) {System.out.println(entry.getKey() + " - " + entry.getValue());}
        String moduleCode = null;
        while (moduleCode == null) {
            System.out.print("Enter module code from the above list: ");
            moduleCode = scanner.nextLine().toUpperCase();
            if (!validatedModules.containsKey(moduleCode)) {System.out.println("Invalid module code! Please choose a valid module."); moduleCode = null; continue;}
            if (isModuleAlreadyAdded(student, moduleCode)) {System.out.println("Module " + moduleCode + " is already added. Please choose another one."); moduleCode = null; continue;}
        }
        String moduleName = validatedModules.get(moduleCode);
        double mark = -1;
        while (mark < 0 || mark > 100) {
            System.out.print("Enter mark for " + moduleName + " (between 0 and 100): ");
            try {
                mark = Double.parseDouble(scanner.nextLine());
                if (mark < 0 || mark > 100) {System.out.println("Error: Mark should be between 0 and 100. Please enter again.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input! Please enter a valid number between 0 and 100.");}
        }
        int moduleId = moduleCode.hashCode();
        Module module = new Module(moduleId, moduleCode, moduleName, mark);
        student.addModule(module);
        System.out.println("Module " + moduleName + " added.");
        return true;
    }

    // Handles editing of students or modules
    private static void editStudentOrModule() {
        System.out.println("Current students:");
        for (Student student : students) {System.out.println("ID: " + student.getStudentId() + ", Name: " + student.getName());}
        Student student = null;
        while (student == null) {
            System.out.println("Do you want to edit a student or a module?");
            System.out.println("1. Edit Student\n2. Edit Module");
            System.out.print("Choose an option: ");
            int choice;
            try {choice = Integer.parseInt(scanner.nextLine());}
            catch (NumberFormatException e) {System.out.println("Invalid input! Please enter a number."); continue;}
            if (choice == 1) {editStudent(); break;}
            else if (choice == 2) {
                while (true) {
                    System.out.print("Enter student ID to edit modules: ");
                    try {
                        int studentId = Integer.parseInt(scanner.nextLine());
                        student = findStudentById(studentId);
                        if (student == null) {System.out.println("Student not found! Please enter a valid student ID."); continue;}
                        editModule(student);
                        break;
                    } catch (NumberFormatException e) {System.out.println("Invalid ID! Please enter a number.");}
                }
                break;
            }
            else {System.out.println("Invalid choice!");}
        }
    }

    // Edits student details
    private static void editStudent() {
        System.out.println("Current students:");
        for (Student student : students) {System.out.println("ID: " + student.getStudentId() + ", Name: " + student.getName());}
        Student student = null;
        while (student == null) {
            System.out.print("Enter student ID to edit (or 'cancel' to return to menu): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {System.out.println("Returning to menu."); return;}
            try {
                int id = Integer.parseInt(input);
                student = findStudentById(id);
                if (student == null) {System.out.println("Student not found! Please enter a valid student ID.");}
            } catch (NumberFormatException e) {System.out.println("Invalid ID! Please enter a number or 'cancel' to return to menu.");}
        }
        boolean updated = false;
        System.out.print("Do you want to change the student's name (y/n)? ");
        String changeName;
        while (true) {
            changeName = scanner.nextLine().toLowerCase();
            if (changeName.equals("y") || changeName.equals("n")) break;
            System.out.print("Invalid input! Please enter 'y' or 'n': ");
        }
        if (changeName.equals("y")) {System.out.print("Enter new student name: "); student.setName(scanner.nextLine()); updated = true;}
        System.out.print("Do you want to change the student ID (y/n)? ");
        String changeId;
        while (true) {
            changeId = scanner.nextLine().toLowerCase();
            if (changeId.equals("y") || changeId.equals("n")) break;
            System.out.print("Invalid input! Please enter 'y' or 'n': ");
        }
        if (changeId.equals("y")) {
            int newId = -1;
            while (true) {
                System.out.print("Enter new student ID: ");
                try {
                    newId = Integer.parseInt(scanner.nextLine());
                    if (findStudentById(newId) != null) {System.out.println("ID already in use! Please enter a different ID."); continue;}
                    break;
                } catch (NumberFormatException e) {System.out.println("Invalid ID! Please enter a number.");}
            }
            student.setStudentId(newId);
            System.out.println("Student ID updated.");
            updated = true;
        }
        if (updated) System.out.println("Student updated.");
        else System.out.println("No changes made to the student.");
    }

    // Edits module details for a student
    private static void editModule(Student student) {
        System.out.println("Modules for " + student.getName() + ":");
        if (student.getModules().isEmpty()) System.out.println("No modules available to edit.");
        else for (Module module : student.getModules()) System.out.println(module.getModuleCode() + " - " + module.getModuleName() + " - " + module.getMark());
        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1. Add a new module\n2. Replace an existing module\n3. Exit to menu");
            System.out.print("Choose an option: ");
            int choice;
            try {choice = Integer.parseInt(scanner.nextLine());}
            catch (NumberFormatException e) {System.out.println("Invalid input! Please enter a number."); continue;}
            if (choice == 1) {addModuleToStudent(student); System.out.println("Module addition complete."); break;}
            else if (choice == 2) {
                if (student.getModules().isEmpty()) {System.out.println("No modules available to replace."); break;}
                System.out.println("Select a module to replace:");
                for (Module module : student.getModules()) System.out.println(module.getModuleCode() + " - " + module.getModuleName());
                Module moduleToReplace = null;
                while (moduleToReplace == null) {
                    System.out.print("Enter the module code to replace: ");
                    String moduleCode = scanner.nextLine().toUpperCase();
                    for (Module module : student.getModules()) {
                        if (module.getModuleCode().equalsIgnoreCase(moduleCode)) {moduleToReplace = module; break;}
                    }
                    if (moduleToReplace == null) {System.out.println("Module not found! Please enter a valid module code."); continue;}
                    System.out.println("Replacing " + moduleToReplace.getModuleName() + " with a new module.");
                    System.out.println("Available Modules to replace with:");
                    for (Map.Entry<String, String> entry : validatedModules.entrySet()) System.out.println(entry.getKey() + " - " + entry.getValue());
                    String newModuleCode = null;
                    while (newModuleCode == null) {
                        System.out.print("Enter new module code from the above list: ");
                        newModuleCode = scanner.nextLine().toUpperCase();
                        if (!validatedModules.containsKey(newModuleCode)) {System.out.println("Invalid module code! Please choose a valid module."); newModuleCode = null; continue;}
                        if (isModuleAlreadyAdded(student, newModuleCode) && !newModuleCode.equals(moduleToReplace.getModuleCode())) {System.out.println("Module " + newModuleCode + " is already added. Please choose another one."); newModuleCode = null; continue;}
                    }
                    double newMark = -1;
                    while (newMark < 0 || newMark > 100) {
                        System.out.print("Enter mark for " + newModuleCode + " (between 0 and 100): ");
                        try {
                            newMark = Double.parseDouble(scanner.nextLine());
                            if (newMark < 0 || newMark > 100) System.out.println("Error: Mark should be between 0 and 100. Please enter again.");
                        } catch (NumberFormatException e) {System.out.println("Invalid input! Please enter a valid number between 0 and 100.");}
                    }
                    student.removeModuleById(moduleToReplace.getModuleId());
                    String newModuleName = validatedModules.get(newModuleCode);
                    int newModuleId = newModuleCode.hashCode();
                    student.addModule(new Module(newModuleId, newModuleCode, newModuleName, newMark));
                    System.out.println("Module " + moduleToReplace.getModuleName() + " replaced with " + newModuleName + ".");
                }
                break;
            }
            else if (choice == 3) {System.out.println("Returning to menu."); break;}
            else System.out.println("Invalid choice! Please select 1, 2, or 3.");
        }
    }

    // Deletes modules from a student
    private static void deleteModules() {
        if (students.isEmpty()) {System.out.println("No students available to delete modules from."); return;}
        System.out.println("Available students:");
        for (Student student : students) System.out.println("ID: " + student.getStudentId() + ", Name: " + student.getName());
        Student student = null;
        while (student == null) {
            System.out.print("Enter student ID to delete modules from (or 'cancel' to return to menu): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {System.out.println("Returning to menu."); return;}
            try {
                int studentId = Integer.parseInt(input);
                student = findStudentById(studentId);
                if (student == null) System.out.println("Student not found! Please enter a valid student ID.");
            } catch (NumberFormatException e) {System.out.println("Invalid ID! Please enter a number or 'cancel' to return to menu.");}
        }
        System.out.println("Modules for " + student.getName() + ":");
        if (student.getModules().isEmpty()) {System.out.println("No modules available to delete."); return;}
        for (Module module : student.getModules()) System.out.println(module.getModuleCode() + " - " + module.getModuleName() + " - " + module.getMark());
        Module moduleToDelete = null;
        while (moduleToDelete == null) {
            System.out.print("Enter module code to delete: ");
            String moduleCode = scanner.nextLine().toUpperCase();
            for (Module module : student.getModules()) {
                if (module.getModuleCode().equalsIgnoreCase(moduleCode)) {moduleToDelete = module; break;}
            }
            if (moduleToDelete == null) System.out.println("Module not found! Please enter a valid module code.");
        }
        student.removeModuleById(moduleToDelete.getModuleId());
        System.out.println("Module " + moduleToDelete.getModuleName() + " deleted.");
    }

    // Deletes a student
    private static void deleteStudent() {
        if (students.isEmpty()) {System.out.println("No students available to delete."); return;}
        System.out.println("Available students to delete:");
        for (Student student : students) System.out.println("ID: " + student.getStudentId() + ", Name: " + student.getName());
        Student student = null;
        while (student == null) {
            System.out.print("Enter student ID to delete (or 'cancel' to return to menu): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {System.out.println("Returning to menu."); return;}
            try {
                int studentId = Integer.parseInt(input);
                student = findStudentById(studentId);
                if (student == null) System.out.println("Student not found! Please enter a valid student ID.");
            } catch (NumberFormatException e) {System.out.println("Invalid ID! Please enter a number or 'cancel' to return to menu.");}
        }
        students.remove(student);
        System.out.println("Student removed.");
    }

    // Finds a student by their ID
    private static Student findStudentById(int studentId) {
        for (Student student : students) if (student.getStudentId() == studentId) return student;
        return null;
    }

    // Updates marks for a student's module
    private static void updateMarks() {
        if (students.isEmpty()) {System.out.println("No students available."); return;}
        System.out.println("Available students:");
        for (Student student : students) System.out.println("ID: " + student.getStudentId() + " - Name: " + student.getName());
        Student student = null;
        while (student == null) {
            System.out.print("Enter student ID (or 'cancel' to return to menu): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {System.out.println("Returning to menu."); return;}
            try {
                int studentId = Integer.parseInt(input);
                student = findStudentById(studentId);
                if (student == null) System.out.println("Student not found! Please enter a valid student ID.");
            } catch (NumberFormatException e) {System.out.println("Invalid ID! Please enter a number or 'cancel' to return to menu.");}
        }
        System.out.println("Modules added for " + student.getName() + ":");
        for (Module module : student.getModules()) System.out.println(module.getModuleCode() + " - " + module.getModuleName());
        Module moduleToUpdate = null;
        while (moduleToUpdate == null) {
            System.out.print("Enter module code to update marks: ");
            String moduleCode = scanner.nextLine().toUpperCase();
            for (Module module : student.getModules()) {
                if (module.getModuleCode().equalsIgnoreCase(moduleCode)) {moduleToUpdate = module; break;}
            }
            if (moduleToUpdate == null) System.out.println("Module not found! Please enter a valid module code.");
        }
        System.out.println("Current mark for " + moduleToUpdate.getModuleName() + ": " + moduleToUpdate.getMark());
        double newMark = -1;
        while (newMark < 0 || newMark > 100) {
            System.out.print("Enter new mark (between 0 and 100): ");
            try {
                newMark = Double.parseDouble(scanner.nextLine());
                if (newMark < 0 || newMark > 100) System.out.println("Error: Mark should be between 0 and 100. Please enter again.");
            } catch (NumberFormatException e) {System.out.println("Invalid input! Please enter a valid number between 0 and 100.");}
        }
        moduleToUpdate.setMark(newMark);
        System.out.println("Mark updated for " + moduleToUpdate.getModuleName() + " to: " + newMark);
    }

    // Displays grades for all students
    private static void displayStudentGrades() {
        for (Student student : students) {
            System.out.println("Grades for " + student.getName() + ":");
            for (Module module : student.getModules()) System.out.println(module.getModuleCode() + " - " + module.getModuleName() + ": " + module.calculateGrade());
        }
    }

    // Displays mean, min, and max marks for each module
    private static void displayMeanMinMaxForModules() {
        Map<String, List<Double>> moduleMarks = new HashMap<>();
        for (Student student : students) {
            for (Module module : student.getModules()) moduleMarks.computeIfAbsent(module.getModuleCode(), k -> new ArrayList<>()).add(module.getMark());
        }
        for (Map.Entry<String, List<Double>> entry : moduleMarks.entrySet()) {
            String moduleCode = entry.getKey();
            List<Double> marks = entry.getValue();
            double mean = marks.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            double min = marks.stream().min(Double::compareTo).orElse(0.0);
            double max = marks.stream().max(Double::compareTo).orElse(0.0);
            System.out.println("Module " + moduleCode + ":\nMean: " + mean + "\nMin: " + min + "\nMax: " + max);
        }
    }

    // Displays grade profile for each module
    private static void displayGradeProfile() {
        List<String> allGrades = Arrays.asList("A (First Class)", "B (Upper Second Class)", "C (Lower Second Class)", "D (Third Class)", "F (Fail)");
        Map<String, Map<String, Integer>> gradeCountPerModule = new HashMap<>();
        for (Student student : students) {
            for (Module module : student.getModules()) {
                String grade = module.calculateGrade();
                gradeCountPerModule.putIfAbsent(module.getModuleCode(), new HashMap<>());
                Map<String, Integer> gradeCounts = gradeCountPerModule.get(module.getModuleCode());
                gradeCounts.put(grade, gradeCounts.getOrDefault(grade, 0) + 1);
            }
        }
        System.out.println("Grade Profile:");
        for (Map.Entry<String, Map<String, Integer>> entry : gradeCountPerModule.entrySet()) {
            String moduleCode = entry.getKey();
            Map<String, Integer> gradeCounts = entry.getValue();
            int totalStudentsInModule = (int) students.stream().filter(s -> s.getModules().stream().anyMatch(m -> m.getModuleCode().equals(moduleCode))).count();
            System.out.println(moduleCode + " - " + validatedModules.get(moduleCode) + ":");
            for (String grade : allGrades) {
                int gradeCount = gradeCounts.getOrDefault(grade, 0);
                double gradePercentage = totalStudentsInModule > 0 ? ((double) gradeCount / totalStudentsInModule) * 100 : 0.0;
                System.out.println(grade + ": " + String.format("%.2f", gradePercentage) + "%");
            }
        }
    }
}


package ProjectB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentGradeSystemTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final ByteArrayInputStream originalIn = new ByteArrayInputStream(new byte[0]);

    private List<Main.Student> students;
    private Map<String, String> validatedModules;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        // Redirect System.out for capturing output
        System.setOut(new PrintStream(outContent));

        // Reset the students list before each test
        Field studentsField = Main.class.getDeclaredField("students");
        studentsField.setAccessible(true);
        students = (List<Main.Student>) studentsField.get(null);
        students.clear();

        // Get validatedModules for use in tests
        Field validatedModulesField = Main.class.getDeclaredField("validatedModules");
        validatedModulesField.setAccessible(true);
        validatedModules = (Map<String, String>) validatedModulesField.get(null);
    }

    @AfterEach
    public void tearDown() {
        // Restore System.out and System.in
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testStudentConstructorAndGetters() {
        Main.Student student = new Main.Student(1001, "John Doe");
        assertEquals(1001, student.getStudentId(), "Student ID should be 1001");
        assertEquals("John Doe", student.getName(), "Student name should be John Doe");
        assertTrue(student.getModules().isEmpty(), "New student should have no modules");
    }

    @Test
    public void testStudentSetters() {
        Main.Student student = new Main.Student(1001, "John Doe");
        student.setStudentId(1002);
        student.setName("Jane Doe");
        assertEquals(1002, student.getStudentId(), "Student ID should be updated to 1002");
        assertEquals("Jane Doe", student.getName(), "Student name should be updated to Jane Doe");
    }

    @Test
    public void testModuleConstructorAndGetters() {
        Main.Module module = new Main.Module(1, "COM4301", "Maths for Computing", 85.5);
        assertEquals(1, module.getModuleId(), "Module ID should be 1");
        assertEquals("COM4301", module.getModuleCode(), "Module code should be COM4301");
        assertEquals("Maths for Computing", module.getModuleName(), "Module name should be Maths for Computing");
        assertEquals(85.5, module.getMark(), 0.01, "Module mark should be 85.5");
    }

    @Test
    public void testModuleCalculateGrade() {
        Main.Module module = new Main.Module(1, "COM4301", "Maths for Computing", 85.5);
        assertEquals("A (First Class)", module.calculateGrade(), "Grade should be A for mark >= 70");
        module.setMark(65.0);
        assertEquals("B (Upper Second Class)", module.calculateGrade(), "Grade should be B for mark >= 60");
        module.setMark(55.0);
        assertEquals("C (Lower Second Class)", module.calculateGrade(), "Grade should be C for mark >= 50");
        module.setMark(45.0);
        assertEquals("D (Third Class)", module.calculateGrade(), "Grade should be D for mark >= 40");
        module.setMark(35.0);
        assertEquals("F (Fail)", module.calculateGrade(), "Grade should be F for mark < 40");
    }

    @Test
    public void testAddStudent() throws Exception {
        // Simulate user input for adding a student
        String input = "1001\nJohn Doe\n2\nCOM4301\n75.0\nSWE4304\n60.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Reset Scanner to use new input
        Field scannerField = Main.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(null, new Scanner(System.in));

        // Call addStudent method
        Method addStudentMethod = Main.class.getDeclaredMethod("addStudent");
        addStudentMethod.setAccessible(true);
        addStudentMethod.invoke(null);

        assertEquals(1, students.size(), "One student should be added");
        Main.Student student = students.get(0);
        assertEquals(1001, student.getStudentId(), "Student ID should be 1001");
        assertEquals("John Doe", student.getName(), "Student name should be John Doe");
        assertEquals(2, student.getModules().size(), "Student should have 2 modules");

        Main.Module module1 = student.getModules().get(0);
        assertEquals("COM4301", module1.getModuleCode(), "First module code should be COM4301");
        assertEquals(75.0, module1.getMark(), 0.01, "First module mark should be 75.0");

        Main.Module module2 = student.getModules().get(1);
        assertEquals("SWE4304", module2.getModuleCode(), "Second module code should be SWE4304");
        assertEquals(60.0, module2.getMark(), 0.01, "Second module mark should be 60.0");
    }

    @Test
    public void testIsModuleAlreadyAdded() throws Exception {
        Main.Student student = new Main.Student(1001, "John Doe");
        Main.Module module = new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 75.0);
        student.addModule(module);

        Method isModuleAlreadyAddedMethod = Main.class.getDeclaredMethod("isModuleAlreadyAdded", Main.Student.class, String.class);
        isModuleAlreadyAddedMethod.setAccessible(true);

        boolean result = (boolean) isModuleAlreadyAddedMethod.invoke(null, student, "COM4301");
        assertTrue(result, "Module COM4301 should be detected as already added");

        result = (boolean) isModuleAlreadyAddedMethod.invoke(null, student, "SWE4304");
        assertFalse(result, "Module SWE4304 should not be detected as added");
    }

    @Test
    public void testUpdateMarks() throws Exception {
        // Add a student with a module
        Main.Student student = new Main.Student(1001, "John Doe");
        Main.Module module = new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 75.0);
        student.addModule(module);
        students.add(student);

        // Simulate user input for updating marks
        String input = "1001\nCOM4301\n85.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Reset Scanner
        Field scannerField = Main.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(null, new Scanner(System.in));

        // Call updateMarks method
        Method updateMarksMethod = Main.class.getDeclaredMethod("updateMarks");
        updateMarksMethod.setAccessible(true);
        updateMarksMethod.invoke(null);

        assertEquals(85.0, student.getModules().get(0).getMark(), 0.01, "Module mark should be updated to 85.0");
    }

    @Test
    public void testDisplayMeanMinMaxForModules() throws Exception {
        // Add students with modules
        Main.Student student1 = new Main.Student(1001, "John Doe");
        student1.addModule(new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 75.0));
        student1.addModule(new Main.Module("SWE4304".hashCode(), "SWE4304", "Databases", 60.0));
        Main.Student student2 = new Main.Student(1002, "Jane Doe");
        student2.addModule(new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 85.0));
        students.add(student1);
        students.add(student2);

        // Call displayMeanMinMaxForModules
        Method displayMeanMinMaxMethod = Main.class.getDeclaredMethod("displayMeanMinMaxForModules");
        displayMeanMinMaxMethod.setAccessible(true);
        displayMeanMinMaxMethod.invoke(null);

        String output = outContent.toString();
        assertTrue(output.contains("Module COM4301"), "Output should include COM4301 statistics");
        assertTrue(output.contains("Mean: 80.0"), "COM4301 mean should be 80.0");
        assertTrue(output.contains("Min: 75.0"), "COM4301 min should be 75.0");
        assertTrue(output.contains("Max: 85.0"), "COM4301 max should be 85.0");
        assertTrue(output.contains("Module SWE4304"), "Output should include SWE4304 statistics");
        assertTrue(output.contains("Mean: 60.0"), "SWE4304 mean should be 60.0");
    }

    @Test
    public void testDisplayGradeProfile() throws Exception {
        // Add students with modules
        Main.Student student1 = new Main.Student(1001, "John Doe");
        student1.addModule(new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 75.0)); // A
        student1.addModule(new Main.Module("SWE4304".hashCode(), "SWE4304", "Databases", 65.0)); // B
        Main.Student student2 = new Main.Student(1002, "Jane Doe");
        student2.addModule(new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 55.0)); // C
        students.add(student1);
        students.add(student2);

        // Call displayGradeProfile
        Method displayGradeProfileMethod = Main.class.getDeclaredMethod("displayGradeProfile");
        displayGradeProfileMethod.setAccessible(true);
        displayGradeProfileMethod.invoke(null);

        String output = outContent.toString();
        assertTrue(output.contains("COM4301 - Maths for Computing"), "Output should include COM4301 profile");
        assertTrue(output.contains("A (First Class): 50.00%"), "COM4301 should have 50% A grades");
        assertTrue(output.contains("C (Lower Second Class): 50.00%"), "COM4301 should have 50% C grades");
        assertTrue(output.contains("SWE4304 - Databases"), "Output should include SWE4304 profile");
        assertTrue(output.contains("B (Upper Second Class): 100.00%"), "SWE4304 should have 100% B grades");
    }

    @Test
    public void testDeleteStudent() throws Exception {
        // Add a student
        Main.Student student = new Main.Student(1001, "John Doe");
        students.add(student);

        // Simulate user input for deleting the student
        String input = "1001\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Reset Scanner
        Field scannerField = Main.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(null, new Scanner(System.in));

        // Call deleteStudent method
        Method deleteStudentMethod = Main.class.getDeclaredMethod("deleteStudent");
        deleteStudentMethod.setAccessible(true);
        deleteStudentMethod.invoke(null);

        assertTrue(students.isEmpty(), "Student should be deleted");
    }

    @Test
    public void testDeleteModules() throws Exception {
        // Add a student with a module
        Main.Student student = new Main.Student(1001, "John Doe");
        student.addModule(new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 75.0));
        students.add(student);

        // Simulate user input for deleting the module
        String input = "1001\nCOM4301\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Reset Scanner
        Field scannerField = Main.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(null, new Scanner(System.in));

        // Call deleteModules method
        Method deleteModulesMethod = Main.class.getDeclaredMethod("deleteModules");
        deleteModulesMethod.setAccessible(true);
        deleteModulesMethod.invoke(null);

        assertTrue(student.getModules().isEmpty(), "Module should be deleted");
    }
}
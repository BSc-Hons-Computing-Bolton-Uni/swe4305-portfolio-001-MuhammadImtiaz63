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

    // Set up test environment
    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        Field studentsField = Main.class.getDeclaredField("students");
        studentsField.setAccessible(true);
        students = (List<Main.Student>) studentsField.get(null);
        students.clear();
        Field validatedModulesField = Main.class.getDeclaredField("validatedModules");
        validatedModulesField.setAccessible(true);
        validatedModules = (Map<String, String>) validatedModulesField.get(null);
    }

    // Clean up after each test
    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    // Test student constructor and getters
    @Test
    public void testStudentConstructorAndGetters() {
        Main.Student student = new Main.Student(1001, "John Doe");
        assertEquals(1001, student.getStudentId());
        assertEquals("John Doe", student.getName());
        assertTrue(student.getModules().isEmpty());
    }

    // Test student setters
    @Test
    public void testStudentSetters() {
        Main.Student student = new Main.Student(1001, "John Doe");
        student.setStudentId(1002);
        student.setName("Jane Doe");
        assertEquals(1002, student.getStudentId());
        assertEquals("Jane Doe", student.getName());
    }

    // Test module constructor and getters
    @Test
    public void testModuleConstructorAndGetters() {
        Main.Module module = new Main.Module(1, "COM4301", "Maths for Computing", 85.5);
        assertEquals(1, module.getModuleId());
        assertEquals("COM4301", module.getModuleCode());
        assertEquals("Maths for Computing", module.getModuleName());
        assertEquals(85.5, module.getMark(), 0.01);
    }

    // Test module grade calculation
    @Test
    public void testModuleCalculateGrade() {
        Main.Module module = new Main.Module(1, "COM4301", "Maths for Computing", 85.5);
        assertEquals("A (First Class)", module.calculateGrade());
        module.setMark(65.0);
        assertEquals("B (Upper Second Class)", module.calculateGrade());
        module.setMark(55.0);
        assertEquals("C (Lower Second Class)", module.calculateGrade());
        module.setMark(45.0);
        assertEquals("D (Third Class)", module.calculateGrade());
        module.setMark(35.0);
        assertEquals("F (Fail)", module.calculateGrade());
    }

    // Test adding a student
    @Test
    public void testAddStudent() throws Exception {
        String input = "1001\nJohn Doe\n2\nCOM4301\n75.0\nSWE4304\n60.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Field scannerField = Main.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(null, new Scanner(System.in));
        Method addStudentMethod = Main.class.getDeclaredMethod("addStudent");
        addStudentMethod.setAccessible(true);
        addStudentMethod.invoke(null);
        assertEquals(1, students.size());
        Main.Student student = students.get(0);
        assertEquals(1001, student.getStudentId());
        assertEquals("John Doe", student.getName());
        assertEquals(2, student.getModules().size());
        Main.Module module1 = student.getModules().get(0);
        assertEquals("COM4301", module1.getModuleCode());
        assertEquals(75.0, module1.getMark(), 0.01);
        Main.Module module2 = student.getModules().get(1);
        assertEquals("SWE4304", module2.getModuleCode());
        assertEquals(60.0, module2.getMark(), 0.01);
    }

    // Test module already added check
    @Test
    public void testIsModuleAlreadyAdded() throws Exception {
        Main.Student student = new Main.Student(1001, "John Doe");
        Main.Module module = new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 75.0);
        student.addModule(module);
        Method isModuleAlreadyAddedMethod = Main.class.getDeclaredMethod("isModuleAlreadyAdded", Main.Student.class, String.class);
        isModuleAlreadyAddedMethod.setAccessible(true);
        boolean result = (boolean) isModuleAlreadyAddedMethod.invoke(null, student, "COM4301");
        assertTrue(result);
        result = (boolean) isModuleAlreadyAddedMethod.invoke(null, student, "SWE4304");
        assertFalse(result);
    }

    // Test updating module marks
    @Test
    public void testUpdateMarks() throws Exception {
        Main.Student student = new Main.Student(1001, "John Doe");
        Main.Module module = new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 75.0);
        student.addModule(module);
        students.add(student);
        String input = "1001\nCOM4301\n85.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Field scannerField = Main.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(null, new Scanner(System.in));
        Method updateMarksMethod = Main.class.getDeclaredMethod("updateMarks");
        updateMarksMethod.setAccessible(true);
        updateMarksMethod.invoke(null);
        assertEquals(85.0, student.getModules().get(0).getMark(), 0.01);
    }

    // Test displaying mean, min, max for modules
    @Test
    public void testDisplayMeanMinMaxForModules() throws Exception {
        Main.Student student1 = new Main.Student(1001, "John Doe");
        student1.addModule(new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 75.0));
        student1.addModule(new Main.Module("SWE4304".hashCode(), "SWE4304", "Databases", 60.0));
        Main.Student student2 = new Main.Student(1002, "Jane Doe");
        student2.addModule(new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 85.0));
        students.add(student1);
        students.add(student2);
        Method displayMeanMinMaxMethod = Main.class.getDeclaredMethod("displayMeanMinMaxForModules");
        displayMeanMinMaxMethod.setAccessible(true);
        displayMeanMinMaxMethod.invoke(null);
        String output = outContent.toString();
        assertTrue(output.contains("Module COM4301"));
        assertTrue(output.contains("Mean: 80.0"));
        assertTrue(output.contains("Min: 75.0"));
        assertTrue(output.contains("Max: 85.0"));
        assertTrue(output.contains("Module SWE4304"));
        assertTrue(output.contains("Mean: 60.0"));
    }

    // Test displaying grade profile
    @Test
    public void testDisplayGradeProfile() throws Exception {
        Main.Student student1 = new Main.Student(1001, "John Doe");
        student1.addModule(new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 75.0));
        student1.addModule(new Main.Module("SWE4304".hashCode(), "SWE4304", "Databases", 65.0));
        Main.Student student2 = new Main.Student(1002, "Jane Doe");
        student2.addModule(new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 55.0));
        students.add(student1);
        students.add(student2);
        Method displayGradeProfileMethod = Main.class.getDeclaredMethod("displayGradeProfile");
        displayGradeProfileMethod.setAccessible(true);
        displayGradeProfileMethod.invoke(null);
        String output = outContent.toString();
        assertTrue(output.contains("COM4301 - Maths for Computing"));
        assertTrue(output.contains("A (First Class): 50.00%"));
        assertTrue(output.contains("C (Lower Second Class): 50.00%"));
        assertTrue(output.contains("SWE4304 - Databases"));
        assertTrue(output.contains("B (Upper Second Class): 100.00%"));
    }

    // Test deleting a student
    @Test
    public void testDeleteStudent() throws Exception {
        Main.Student student = new Main.Student(1001, "John Doe");
        students.add(student);
        String input = "1001\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Field scannerField = Main.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(null, new Scanner(System.in));
        Method deleteStudentMethod = Main.class.getDeclaredMethod("deleteStudent");
        deleteStudentMethod.setAccessible(true);
        deleteStudentMethod.invoke(null);
        assertTrue(students.isEmpty());
    }

    // Test deleting modules
    @Test
    public void testDeleteModules() throws Exception {
        Main.Student student = new Main.Student(1001, "John Doe");
        student.addModule(new Main.Module("COM4301".hashCode(), "COM4301", "Maths for Computing", 75.0));
        students.add(student);
        String input = "1001\nCOM4301\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Field scannerField = Main.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(null, new Scanner(System.in));
        Method deleteModulesMethod = Main.class.getDeclaredMethod("deleteModules");
        deleteModulesMethod.setAccessible(true);
        deleteModulesMethod.invoke(null);
        assertTrue(student.getModules().isEmpty());
    }
}
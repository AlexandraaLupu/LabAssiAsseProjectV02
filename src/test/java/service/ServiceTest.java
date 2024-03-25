package service;
//import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.*;

import domain.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import repository.StudentXMLRepo;
import validation.StudentValidator;
import validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServiceTest {
    private StudentXMLRepo studentFileRepository;
    private StudentValidator studentValidator;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() {
        this.studentFileRepository = new StudentXMLRepo(
                "fisiere/studentiTest.xml");
        this.studentValidator = new StudentValidator();
        this.service = new Service(
                this.studentFileRepository,
                this.studentValidator,
                null,
                null,
                null,
                null
        );
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/studentiTest.xml").delete();
    }

    @Test
    public void testAddStudentDuplicate() {
        Student newStudent1 = new Student("1", "Alex", 934, "alex@gmail.com");

        Student stud1 = this.service.addStudent(newStudent1);
        assertNull(stud1);

        Student stud2 = this.service.addStudent(newStudent1);
        assertEquals(newStudent1.getID(), stud2.getID());

        this.service.deleteStudent("1");
    }

    @Test
    public void testAddStudentNonDuplicate(){
        Student newStudent1 = new Student("1", "Alex", 934, "alex@gmail.com");
        Student newStudent2 = new Student("2", "Alex", 934, "alex@gmail.com");


        Student stud1 = this.service.addStudent(newStudent1);
        assertNull(stud1);

        Student stud2 = this.service.addStudent(newStudent2);
        assertNull(stud2);

        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());
        assertEquals(students.next().getID(), newStudent2.getID());

        this.service.deleteStudent("1");
        this.service.deleteStudent("2");
    }

    @Test
    public void testAddStudentValidName(){
        Student newStudent1 = new Student("1", "Alex", 934, "alex@gmail.com");
        this.service.addStudent(newStudent1);
        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());
        this.service.deleteStudent("1");
    }

    @Test
    public void testAddStudentEmptyName(){
        Student newStudent2 = new Student("2", "", 934, "alex@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));

    }

    @Test
    public void testAddStudentNullName(){
        Student newStudent3 = new Student("3", null, 934, "alex@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent3));
    }



    @Test
    public void testAddStudentValidGroup() {
        Student newStudent1 = new Student("1", "Alex", 934, "alex@gmail.com");

        this.service.addStudent(newStudent1);
        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());

        this.service.deleteStudent("1");
    }

    @Test
    public void testAddStudentInvalidGroup() {
        Student newStudent2 = new Student("2", "Alex", -6, "alex@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
    }

    @Test
    public void testAddStudentValidEmail() {
        Student newStudent1 = new Student("1", "Alex", 934, "alex@gmail.com");
        this.service.addStudent(newStudent1);
        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());
        this.service.deleteStudent("1");
    }

    @Test
    public void testAddStudentEmptyEmail() {
        Student newStudent2 = new Student("2", "Alex", 934, "");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
    }

    @Test
    public void testAddStudentNullEmail() {
        Student newStudent3 = new Student("3", "Alex", 934, null);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent3));
    }

    @Test
    public void testAddStudentValidId() {
        Student newStudent1 = new Student("2345", "Alex", 934, "alex@gmail.com");
        this.service.addStudent(newStudent1);
        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());
        this.service.deleteStudent("2345");
    }

    @Test
    public void testAddStudentEmptyId() {
        Student newStudent2 = new Student("", "Alex", 934, "alex@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
    }

    @Test
    public void testAddStudentNullId() {
        Student newStudent3 = new Student(null, "Alex", 934, "alex@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent3));
    }

    /**
     * BVA Test case
     */
    @Test
    public void testAddStudentGroupLowerBVABound(){
        Student newStudent1 = new Student("1", "Alex", 0, "alex@gmail.com");
        this.service.addStudent(newStudent1);
        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());
        this.service.deleteStudent("1");
    }

}
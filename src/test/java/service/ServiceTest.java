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

    public ServiceTest() {
        createXML();
    }

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
    static void removeXML() {
        new File("fisiere/studentiTest.xml").delete();
    }

    @Test
    public void testAddStudentDuplicate() {
        setup();
        Student newStudent1 = new Student("1", "Alex", 934, "alex@gmail.com");

        Student stud1 = this.service.addStudent(newStudent1);
        assertNull(stud1);

        Student stud2 = this.service.addStudent(newStudent1);
        assertEquals(newStudent1.getID(), stud2.getID());

        this.service.deleteStudent("1");
        removeXML();
    }

    @Test
    public void testAddStudentNonDuplicate(){
        setup();
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
        removeXML();
    }

    @Test
    public void testAddStudentValidName(){
        setup();
        Student newStudent1 = new Student("1", "Alex", 934, "alex@gmail.com");
        this.service.addStudent(newStudent1);
        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());
        this.service.deleteStudent("1");
        removeXML();
    }

    @Test
    public void testAddStudentEmptyName(){
        setup();
        Student newStudent2 = new Student("2", "", 934, "alex@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
    removeXML();
    }

    @Test
    public void testAddStudentNullName(){
        setup();
        Student newStudent3 = new Student("3", null, 934, "alex@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent3));
        removeXML();
    }



    @Test
    public void testAddStudentValidGroup() {
        setup();
        Student newStudent1 = new Student("1", "Alex", 934, "alex@gmail.com");

        this.service.addStudent(newStudent1);
        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());

        this.service.deleteStudent("1");
        removeXML();
    }

    @Test
    public void testAddStudentInvalidGroup() {
        setup();
        Student newStudent2 = new Student("2", "Alex", -6, "alex@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
        removeXML();
    }

    @Test
    public void testAddStudentValidEmail() {
        setup();
        Student newStudent1 = new Student("1", "Alex", 934, "alex@gmail.com");
        this.service.addStudent(newStudent1);
        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());
        this.service.deleteStudent("1");
        removeXML();
    }

    @Test
    public void testAddStudentEmptyEmail() {
        setup();
        Student newStudent2 = new Student("2", "Alex", 934, "");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
        removeXML();
    }

    @Test
    public void testAddStudentNullEmail() {
        setup();
        Student newStudent3 = new Student("3", "Alex", 934, null);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent3));
        removeXML();
    }

    @Test
    public void testAddStudentValidId() {
        setup();
        Student newStudent1 = new Student("2345", "Alex", 934, "alex@gmail.com");
        this.service.addStudent(newStudent1);
        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());
        this.service.deleteStudent("2345");
        removeXML();
    }

    @Test
    public void testAddStudentEmptyId() {
        setup();
        Student newStudent2 = new Student("", "Alex", 934, "alex@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
        removeXML();
    }

    @Test
    public void testAddStudentNullId() {
        setup();
        Student newStudent3 = new Student(null, "Alex", 934, "alex@gmail.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent3));
        removeXML();
    }

    /**
     * BVA Test case
     */
    @Test
    public void testAddStudentGroupLowerBVABound(){
        setup();
        Student newStudent1 = new Student("1", "Alex", 0, "alex@gmail.com");
        this.service.addStudent(newStudent1);
        var students = this.service.getAllStudenti().iterator();
        assertEquals(students.next().getID(), newStudent1.getID());
        this.service.deleteStudent("1");
        removeXML();
    }
}
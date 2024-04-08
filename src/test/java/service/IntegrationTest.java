package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class IntegrationTest {
    private Service service;

    public IntegrationTest() {
        createXML();
    }

    static void createXML() {
        File xmlStudent = new File("fisiere/studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlStudent))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File xmlAssignment = new File("fisiere/temeTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlAssignment))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File xmlGrade = new File("fisiere/noteTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlGrade))) {
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
        StudentValidator studentValidator = new StudentValidator();
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo("fisiere/studentiTest.xml");

        TemaValidator temaValidator = new TemaValidator();
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo("fisiere/temeTest.xml");

        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo("fisiere/noteTest.xml");

        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    static void removeXML() {
        new File("fisiere/studentiTest.xml").delete();
        new File("fisiere/temeTest.xml").delete();
        new File("fisiere/noteTest.xml").delete();
    }

    @Test
    public void testAddStudent() {
        setup();
        Student student = new Student("333", "Ana", 931, "ana@gmail.com");
        assertNull(service.addStudent(student));
        removeXML();
    }

    @Test
    public void testAddTema() {
        setup();
        Tema tema = new Tema("333", "a", 1, 1);
        assertNull(service.addTema(tema));
        removeXML();
    }

    @Test
    public void testAddStudentTemaGrade() {
        setup();
        Student student = new Student("222", "Ana", 931, "ana@gmail.com");
        Tema tema = new Tema("222", "a", 1, 1);
        Nota nota = new Nota("222", "222", "222", 10, LocalDate.now());

        assertNull(service.addStudent(student));
        assertNull(service.addTema(tema));
        assertEquals(service.addNota(nota, "Foarte bine"), 10.0);

        service.deleteNota("333");
        service.deleteStudent("333");
        service.deleteTema("333");
        removeXML();
    }
}

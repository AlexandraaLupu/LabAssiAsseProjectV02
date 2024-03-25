package service;

import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertThrows;

public class ServiceTestWBT {
    private TemaXMLRepo temaFileRepository;
    private TemaValidator temaValidator;
    private Service service;

    public ServiceTestWBT() {
        createXML();
    }

    static void createXML() {
        File xml = new File("fisiere/temeTest.xml");
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
        this.temaFileRepository = new TemaXMLRepo(
                "fisiere/temeTest.xml");
        this.temaValidator = new TemaValidator();
        this.service = new Service(
                null,
                null,
                this.temaFileRepository,
                this.temaValidator,
                null,
                null
        );
    }
    static void removeXML() {
        new File("fisiere/temeTest.xml").delete();
    }

    @Test
    public void testAddAssignmentEmptyId() {
        setup();
        Tema newTema = new Tema("", "a", 12, 10);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
        removeXML();
    }

    @Test
    public void testAddAssignmentEmptyDescription() {
        setup();
        Tema newTema = new Tema("1", "", 12, 10);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
        removeXML();
    }
}

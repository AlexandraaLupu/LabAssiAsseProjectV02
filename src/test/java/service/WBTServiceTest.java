package service;


import domain.Tema;
import org.junit.Test;
import repository.TemaXMLRepo;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;


public class WBTServiceTest {
    private TemaXMLRepo temaFileRepository;
    private TemaValidator temaValidator;
    private Service service;

    public WBTServiceTest() {
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
    public void testAddAssignmentEmptyID() {
        setup();
        Tema newTema = new Tema("", "a", 1, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
        removeXML();
    }

    @Test
    public void testAddAssignmentNullID() {
        setup();
        Tema newTema = new Tema(null, "a", 1, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
        removeXML();
    }

    @Test
    public void testAddAssignmentEmptyDescription() {
        setup();
        Tema newTema = new Tema("1", "", 1, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
        removeXML();
    }

    @Test
    public void testAddAssignmentDeadlineTooLarge() {
        setup();
        Tema newTema = new Tema("1", "a", 15, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
        removeXML();
    }

    @Test
    public void testAddAssignmentDeadlineTooSmall() {
        setup();
        Tema newTema = new Tema("1", "a", 0, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
        removeXML();
    }

    @Test
    public void testAddAssignmentReceivedTooSmall() {
        setup();
        Tema newTema = new Tema("1", "a", 1, 0);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
        removeXML();
    }

    @Test
    public void testAddAssignmentReceivedTooLarge() {
        setup();
        Tema newTema = new Tema("1", "a", 1, 15);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
        removeXML();
    }

    @Test
    public void testAddAssignmentValidAssignment() {
        setup();
        Tema newTema = new Tema("1", "a", 1, 1);
        this.service.addTema(newTema);
        assertEquals(this.service.getAllTeme().iterator().next(), newTema);
        removeXML();
    }

    @Test
    public void testAddAssignmentDuplicateAssignment() {
        setup();
        Tema newTema = new Tema("1", "a", 1, 1);
        this.service.addTema(newTema);

        Tema newTema2 = new Tema("1", "a", 1, 1);

        assertEquals(this.service.addTema(newTema2).getID(), newTema.getID());
        removeXML();
    }
}

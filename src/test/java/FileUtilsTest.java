
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FileUtilsTest {

    FileUtils instance;

    @Before
    public void setUp() throws Exception {
        FileUtils instance = new FileUtils("src/test/temp/files/staff.txt");
        this.instance = instance;
    }

    @Test
    public void createFile() {
        instance.createFile("./src/test/temp/html/createFile.txt", "toto");
        File target = new File("src/test/temp/html/createFile.txt");
        assertTrue(target.exists());
        target.delete();
    }

    @Test
    public void readFile() {
        assertEquals(instance.getLines().size(), 2);
    }

    @Test
    public void getLine() {
        assertEquals(instance.getLine(0), "sconnor");
        assertEquals(instance.getLine(1), "jmichel");
    }

    @Test
    public void getMateriel() {
        FileUtils instanceAgent = new FileUtils("src/test/temp/files/agents/jmichel.txt");
        ArrayList<String> result = instanceAgent.getMateriel();
        assertEquals(result.get(0), "cyno");
        assertEquals(result.get(1), "gants");
        assertEquals(result.get(2), "brassard");
        assertEquals(result.get(3), "taser");
    }

    @Test
    public void copyDirectory() throws IOException {
        File from = new File("src/test/temp/files/ressources");
        File to = new File("src/test/temp/html/ressources");
        assertEquals(false, to.exists());
        FileUtils.copyDirectory(from, to);
        assertEquals(true, to.exists());
        assertEquals(true, to.isDirectory());
        File target1 = new File("src/test/temp/html/ressources/goSecuriIcon.png");
        File target2 = new File("src/test/temp/html/ressources/logoSecuri.png");
        assertTrue(target1.exists());
        assertTrue(target2.exists());
        target1.delete();
        target2.delete();
        to.delete();
    }

    @Test(expected = FileNotFoundException.class)
    public void copyException() throws IOException {
        File fromFail = new File("src/test/temp/files/ressourcesquinexistepas/goSecuriIcon.jpg");
        File to = new File("src/test/temp/html/ressources");
        FileUtils.copy(fromFail, to);
    }

    @Test
    public void copyFile() throws IOException {
        File from = new File("src/test/temp/files/ressources/goSecuriIcon.png");
        File to = new File("src/test/temp/html/goSecuriIcon.png");
        assertEquals(false, to.exists());
        FileUtils.copyFile(from, to);
        assertEquals(true, to.exists());
        to.delete();
    }

}

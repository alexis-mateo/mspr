import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileUtilsTest {

    FileUtils instance;

    @Before
    public void setUp() throws Exception {
        FileUtils instance = new FileUtils("src/test/temp/files/staff.txt");
        this.instance = instance;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createFile() {
        instance.createFile("./src/test/temp/files/index.html","");
    }

    @Test
    public void generateIndex() {
        assertEquals(false,true);
    }

    @Test
    public void readFile() {
    }

    @Test
    public void getLines() {
    }

    @Test
    public void getLine() {
    }

    @Test
    public void getMateriel() {
    }

    @Test
    public void copyDirectory() {
    }

    @Test
    public void copy() {
    }

    @Test
    public void copyFile() {
    }

    @Test
    public void testCopy() {
    }
}

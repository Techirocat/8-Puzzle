import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PuzzleUnitTests {
    @Test
    public void testConstructor() {
        Board b = new Board("023145678");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println(" 23");
        pw.println("145");
        pw.println("678");
        // JUnit expects the expected value first, then the actual value.
        assertEquals(writer.toString(), b.toString());
        pw.close();
    }

    @Test
    public void testConstructor2() {
        Board b = new Board("123485670");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("123");
        pw.println("485");
        pw.println("67 ");
        assertEquals(writer.toString(), b.toString());
        pw.close();
    }

    @Test
    public void testEquals1() {
        Board b1 = new Board("123485670");

        assertEquals(b1, b1);
    }

    @Test
    public void testEquals2() {
        Board b1 = new Board("123485670");
        Board b2 = new Board("123485670");

        assertEquals(b1, b2);
    }

    @Test
    public void testEquals3() {
        Board b1 = new Board("123485670");

        assertNotEquals(null, b1);
    }

    @Test
    public void testEquals4() {
        Board b1 = new Board("123485670");
        Board b2 = new Board("123480567");

        assertNotEquals(b1, b2);
    }

    @Test
    public void testHashCode1() {
        Board b1 = new Board("123485670");
        Board b2 = new Board("123485670");
        assertEquals(b1.hashCode(), b2.hashCode());
    }


    @Test
    public void testHashCode2() {
        Board b1 = new Board("123485670");
        Board b2 = new Board("123485607");
        assertNotEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    public void testChildren1() {
        Board b1 = new Board("123405678");
        List<Ilayout> children = b1.children();

        assertEquals(children, b1.children());

        assertTrue(children.contains(new Board("103425678")));
        assertTrue(children.contains(new Board("123475608")));
        assertTrue(children.contains(new Board("123045678")));
        assertTrue(children.contains(new Board("123450678")));
    }

    @Test
    public void testChildren2() {
        Board b1 = new Board("012345678");
        List<Ilayout> children = b1.children();

        assertEquals(2, children.size());
        assertTrue(children.contains(new Board("312045678")));
        assertTrue(children.contains(new Board("102345678")));
    }

    @Test
    public void testIsGoal1(){
        Board board = new Board("123456780");
        Board  goal = new Board("123456780");
        Board notGoal = new Board("123456078");

        assertTrue(board.isGoal(goal));
        assertFalse(board.isGoal(notGoal));
    }

    @Test
    public void testGetK1(){
        Board board = new Board("123456780");
        assertEquals(1.0, board.getK());
    }
}

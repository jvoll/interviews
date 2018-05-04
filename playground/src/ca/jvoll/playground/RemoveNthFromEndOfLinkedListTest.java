package ca.jvoll.playground;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

import static ca.jvoll.playground.RemoveNthFromEndOfLinkedList.Node;

public class RemoveNthFromEndOfLinkedListTest {

    RemoveNthFromEndOfLinkedList testClass;

    @Before
    public void setup() {
        testClass = new RemoveNthFromEndOfLinkedList();
    }

    // List Node equality tests
    @Test
    public void testListNodeEquals() {
        Node a = new Node(1);

        assertTrue(a.equals(a));
        assertTrue(a.equals(new Node(1)));
        assertTrue((new Node(1)).equals(a));

        Node b = new Node(2);
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));

        assertFalse(a.equals(null));
        assertFalse(a.equals(1));

        Node c = testClass.linkedListFromList(Arrays.asList(1, 2, 3));
        Node d = testClass.linkedListFromList(Arrays.asList(1, 2, 3));
        assertTrue(c.equals(c));
        assertTrue(c.equals(d));
        assertTrue(d.equals(c));

        Node e = testClass.linkedListFromList(Arrays.asList(1, 2));
        assertFalse(d.equals(e));
        assertFalse(e.equals(d));

        Node f = testClass.linkedListFromList(Arrays.asList(1, 2, 4));
        assertFalse(d.equals(f));
    }

    @Test
    public void testNullList() {
        assertEquals(null, testClass.removeNthFromEnd(null, 2));
    }

    @Test
    public void testSingleElementList() {
        Node listHead = testClass.linkedListFromList(Arrays.asList(1));
        assertEquals(null, testClass.removeNthFromEnd(listHead, 1));
    }

    @Test
    public void testNGreaterThanListLength() {
        Node listHead = testClass.linkedListFromList(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(listHead, testClass.removeNthFromEnd(listHead, 6));
    }

    @Test
    public void testDeleteFirstElement() {
        Node listHead = testClass.linkedListFromList(Arrays.asList(1, 2, 3));
        Node expected = testClass.linkedListFromList(Arrays.asList(2, 3));
        assertEquals(expected, testClass.removeNthFromEnd(listHead, 3));
    }

    @Test
    public void testDeleteLastElement() {
        Node listHead = testClass.linkedListFromList(Arrays.asList(1, 2, 3));
        Node expected = testClass.linkedListFromList(Arrays.asList(1, 2));
        assertEquals(expected, testClass.removeNthFromEnd(listHead, 1));
    }

    @Test
    public void testBasicCase() {
        Node listHead = testClass.linkedListFromList(Arrays.asList(1, 2, 3, 6, 7));
        Node expected = testClass.linkedListFromList(Arrays.asList(1, 2, 6, 7));
        assertEquals(expected, testClass.removeNthFromEnd(listHead, 3));
    }
}
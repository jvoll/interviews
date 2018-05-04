package ca.jvoll.playground;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShortestPalindromeTest {

    ShortestPalindrome p;
    @Before
    public void setup() {
        p = new ShortestPalindrome();
    }

    @Test
    public void testEmptyNull() {
        assertEquals("", p.shortestPalindrome(""));
        assertEquals("", p.shortestPalindrome(null));
    }

    @Test
    public void testBaseOdd() {
        assertEquals("a", p.shortestPalindrome("a"));
    }

    @Test
    public void testBaseEven() {
        assertEquals("aa", p.shortestPalindrome("aa"));
    }

    @Test
    public void testWorstOddStart() {
        assertEquals("cbabc", p.shortestPalindrome("abc"));
    }

    @Test
    public void testWorstEvenStart() {
        assertEquals("dcbabcd", p.shortestPalindrome("abcd"));
    }

    @Test
    public void testEvenResult() {
        assertEquals("dcbaabcd", p.shortestPalindrome("aabcd"));
    }

    @Test
    public void testOddResult() {
        assertEquals("aaacecaaa", p.shortestPalindrome("acecaaa"));
    }
}
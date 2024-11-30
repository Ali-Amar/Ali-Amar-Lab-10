package expressivo;

import static org.junit.Assert.*;
import org.junit.Test;

public class ExpressionTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; 
    }
    
    // Tests for numbers
    @Test
    public void testParseInteger() {
        Expression e = Expression.parse("123");
        assertEquals("123", e.toString());
    }
    
    @Test
    public void testParseWithWhitespace() {
        Expression e = Expression.parse(" 123 ");
        assertEquals("123", e.toString());
    }
    
    // Tests for addition
    @Test
    public void testParseSimpleAddition() {
        Expression e = Expression.parse("1 + 2");
        assertEquals("(1 + 2)", e.toString());
    }
    
    @Test
    public void testParseParenthesizedAddition() {
        Expression e = Expression.parse("(1 + 2) + 3");
        assertEquals("((1 + 2) + 3)", e.toString());
    }
    
    // Tests for equals() and hashCode()
    @Test
    public void testEqualsTrue() {
        Expression e1 = Expression.parse("1 + 2");
        Expression e2 = Expression.parse("1 + 2");
        assertTrue(e1.equals(e2));
        assertEquals(e1.hashCode(), e2.hashCode());
    }
    
    @Test
    public void testEqualsFalse() {
        Expression e1 = Expression.parse("1 + 2");
        Expression e2 = Expression.parse("2 + 1");
        assertFalse(e1.equals(e2));
    }
    
    @Test
    public void testNumberEqualsWithWhitespace() {
        Expression e1 = Expression.parse("1");
        Expression e2 = Expression.parse(" 1 ");
        assertTrue(e1.equals(e2));
        assertEquals(e1.hashCode(), e2.hashCode());
    }
    
    @Test
    public void testParenthesesEquivalence() {
        Expression e1 = Expression.parse("1 + 2");
        Expression e2 = Expression.parse("(1 + 2)");
        assertTrue(e1.equals(e2));
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
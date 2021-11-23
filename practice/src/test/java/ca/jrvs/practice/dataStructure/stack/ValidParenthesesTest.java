package ca.jrvs.practice.dataStructure.stack;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidParenthesesTest {

  ValidParentheses parentheses;
  @Before
  public void setUp() throws Exception {
    parentheses = new ValidParentheses();
  }

  @Test
  public void isValidParentheses() {
    assertTrue(parentheses.isValidParentheses("[]"));
    assertTrue(parentheses.isValidParentheses("[{()}]"));
    assertTrue(parentheses.isValidParentheses("()"));

    assertFalse(parentheses.isValidParentheses("]"));
    assertFalse(parentheses.isValidParentheses("])}"));
    assertFalse(parentheses.isValidParentheses("[]{"));
    assertFalse(parentheses.isValidParentheses("[{("));
  }

}
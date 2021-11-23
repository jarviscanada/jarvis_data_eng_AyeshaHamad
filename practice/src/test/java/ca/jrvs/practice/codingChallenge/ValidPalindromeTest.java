package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidPalindromeTest {

  ValidPalindrome p;

  @Before
  public void setUp() throws Exception {
    p = new ValidPalindrome();
  }

  @Test
  public void isPalindrome() {
    assertTrue(p.isPalindrome("abcba"));
    assertTrue(p.isPalindrome("abccba"));
    assertFalse(p.isPalindrome("abcaeb"));
  }

  @Test
  public void isPalindromeRecursion() {
    assertTrue(p.isPalindromeRecursion("abcba"));
    assertFalse(p.isPalindromeRecursion("abcde"));
  }
}
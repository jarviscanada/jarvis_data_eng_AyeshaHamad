package ca.jrvs.practice.dataStructure.map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PrintLetterWithNumberTest {

  private PrintLetterWithNumber obj;

  @Before
  public void setUp() throws Exception {
    obj = new PrintLetterWithNumber();
  }

  @Test
  public void printNumber() {
    assertEquals("A27b2c3d4e5g7", obj.printNumber("Abcdeg"));
  }
}
package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StringRotateTest {

  private StringRotate obj;

  @Before
  public void setUp() throws Exception {
    obj = new StringRotate();
  }

  @Test
  public void rotateString() {
    //fail();
    //assertTrue(obj.rotateString("deabc", "ayesha abcde hamad"));
    assertEquals(Boolean.TRUE,obj.rotateString("deabc", "ayesha abcde hamad"));
  }
}
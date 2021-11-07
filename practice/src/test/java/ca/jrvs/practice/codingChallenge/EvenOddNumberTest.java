package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EvenOddNumberTest {

  private EvenOddNumber obj;

  @Before
  public void setUp(){
    obj = new EvenOddNumber();
  }
  @Test
  public void evenOddMod() {
    assertEquals("Even",obj.evenOddMod(4));
    assertEquals("Odd",obj.evenOddMod(3));
    assertEquals("Even",obj.evenOddMod(0));
  }

  @Test
  public void evenOddBit() {
    assertEquals("Even",obj.evenOddMod(10));
    assertEquals("Odd",obj.evenOddMod(23));
    assertEquals("Even",obj.evenOddMod(154));
  }
}
package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FindLargeSmallTest {

  FindLargeSmall obj;

  @Before
  public void setUp() throws Exception {
    obj = new FindLargeSmall();
  }

  @Test
  public void usingForLoop() {
    assertEquals(100, obj.usingForLoop(new int[] {9,1,5,100,10,3,0,4}));
  }

  @Test
  public void usingJavaAPI() {
    assertEquals(20, obj.usingJavaAPI(new int[] {9,1,5,20,10,3,0,4}));
  }

  @Test
  public void usingStreamAPI() {
    assertEquals(50, obj.usingStreamAPI(new int[] {9,1,5,10,50,3,0,4}));
  }
}
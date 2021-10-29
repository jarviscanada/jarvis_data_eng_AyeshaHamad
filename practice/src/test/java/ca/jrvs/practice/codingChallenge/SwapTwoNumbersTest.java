package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SwapTwoNumbersTest {

  private SwapTwoNumbers obj;
  private int [] num;
  private int n1, n2;

  @Before
  public void setUp() {
    obj = new SwapTwoNumbers();
    n1 = 10;
    n2 = 15;
    num = new int[]{n1,n2};
  }

  @Test
  public void swap2Numbers_Math() {
    obj.swap2Numbers_Math(num);
    assertEquals(n1,num[1]);
    assertEquals(n2,num[0]);
  }

  @Test
  public void swap2Numbers_Bitwise() {
    obj.swap2Numbers_Bitwise(num);
    assertEquals(n1,num[1]);
    assertEquals(n2,num[0]);
  }
}
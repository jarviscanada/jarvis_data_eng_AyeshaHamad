package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TwoSumTest {

  private TwoSum obj;

  @Before
  public void setUp() throws Exception {
    obj = new TwoSum();
  }

  @Test
  public void twoSum_Approach1() {
    assertArrayEquals(new int[]{0,1},obj.twoSum_Approach1(new int[]{2, 7, 1, 8},9));
    assertArrayEquals(new int[]{0,0},obj.twoSum_Approach1(new int []{2, 7, 11, 15},25));
  }

  @Test
  public void twoSum_Approach3() {
    assertArrayEquals(new int[]{0,1},obj.twoSum_Approach3(new int[]{2, 7, 1, 8},9));
    assertArrayEquals(new int[]{2,3},obj.twoSum_Approach3(new int []{2, 7, 11, 15},26));
  }
}
package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class RemoveElementTest {

  RemoveElement obj ;
  @Before
  public void setUp() throws Exception {
    obj = new RemoveElement();
  }

  @Test
  public void removeElement() {
    int [] nums = {1,2,5,2,3};
    int [] numsExpected = {1,5,3};
    int value = 2;

    int k = obj.removeElement(nums, value);
    Arrays.stream(nums).forEach(System.out::println);
    assertEquals(numsExpected.length, k);
  }
}
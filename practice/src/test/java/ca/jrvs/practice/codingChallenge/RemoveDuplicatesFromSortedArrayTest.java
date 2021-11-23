package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class RemoveDuplicatesFromSortedArrayTest {

  RemoveDuplicatesFromSortedArray obj;
  @Before
  public void setUp() throws Exception {
    obj = new RemoveDuplicatesFromSortedArray();
  }

  @Test
  public void removeDuplicates() {
    int [] nums = {1,2,3,3,3,4,4,5};
    int [] numsExpected = {1,2,3,4,5};
    int k = obj.removeDuplicates(nums);
    assertEquals(numsExpected.length, k);
    Arrays.stream(nums).forEach(System.out::println);
  }
  @Test
  public void removeDup_oneLoop() {
    int [] nums = {1,2,3,3,3,4,4,5};
    int [] numsExpected = {1,2,3,4,5};
    int k = obj.removeDuplicates_oneLoop(nums);
    Arrays.stream(nums).forEach(System.out::println);
  }
}
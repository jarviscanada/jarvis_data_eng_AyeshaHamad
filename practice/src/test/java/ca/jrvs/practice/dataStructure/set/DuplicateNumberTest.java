package ca.jrvs.practice.dataStructure.set;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DuplicateNumberTest {

  DuplicateNumber obj;

  @Before
  public void setUp() throws Exception {
    obj = new DuplicateNumber();
  }

  @Test
  public void findDuplicate_usingSet() {
    assertEquals(5, obj.findDuplicate_usingSet(new int[] {1,5,2,3,5,9}));
    assertEquals(2, obj.findDuplicate_usingSet(new int[] {1,3,4,2,2}));
    assertEquals(-1, obj.findDuplicate_usingSet(new int[] {1,3,4,2,5}));
  }

  @Test
  public void findDuplicate_sorting() {
    obj.findDuplicate_sorting(new int[] {1,5,2,3,5,9});

  }
}
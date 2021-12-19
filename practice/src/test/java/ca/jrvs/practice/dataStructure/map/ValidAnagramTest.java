package ca.jrvs.practice.dataStructure.map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidAnagramTest {

  ValidAnagram obj ;
  @Before
  public void setUp() throws Exception {
    obj = new ValidAnagram();
  }

  @Test
  public void isAnagram_Sorting() {
    assertTrue(obj.isAnagram_Sorting( "anagram", "nagaram"));
    assertFalse(obj.isAnagram_Sorting( "rat", "car"));
  }

  @Test
  public void isAnagram_Map() {
    assertTrue(obj.isAnagram_Map( "anagram", "nagaram"));
    assertFalse(obj.isAnagram_Map( "rat", "car"));
  }
}
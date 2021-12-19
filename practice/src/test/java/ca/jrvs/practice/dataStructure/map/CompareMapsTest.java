package ca.jrvs.practice.dataStructure.map;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CompareMapsTest {

  CompareMaps compareMaps;

  @Before
  public void setUp() throws Exception {
    compareMaps = new CompareMaps();
  }

  @Test
  public void compareMaps() {
    Map obj1 = new HashMap();
    obj1.put('A', 'a');
    Map obj2 = new HashMap();
    obj2.put('A', 'a');
    Map obj3 = new HashMap();
    obj3.put('B', 'b');

    assertTrue(compareMaps.compareMaps(obj1, obj2));
    assertFalse(compareMaps.compareMaps(obj1, obj3));
  }
}
package ca.jrvs.practice.dataStructure.set;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;
import org.junit.Test;

public class DuplicateCharacterTest {

  @Test
  public void duplicateChar() {
    DuplicateCharacter obj = new DuplicateCharacter();
    Set set = obj.duplicateChar("A black cat");
    set.stream().sorted().forEach(System.out::print);

    System.out.println(Arrays.toString(set.toArray()));
  }
}
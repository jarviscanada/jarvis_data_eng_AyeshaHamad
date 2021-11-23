package ca.jrvs.practice.dataStructure.set;

import java.util.HashSet;

/**
 * ticket: https://www.notion.so/jarvisdev/Duplicate-Characters-b2139b1b71a74d2895cbcc480b25204a
 */
public class DuplicateCharacter {

  HashSet<Character> duplicateChar(String s) {
    HashSet<Character> set = new HashSet<>();
    HashSet<Character> duplicates = new HashSet<>();
    for(char c : s.toCharArray()) {
      if(set.add(c) == false) {
        duplicates.add(c);
      }
    }
    return duplicates;
  }

}

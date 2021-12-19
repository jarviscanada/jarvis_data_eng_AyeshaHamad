package ca.jrvs.practice.dataStructure.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ticket : https://www.notion.so/jarvisdev/Valid-Anagram-7d0095d9b4564530a67157a4a5971e89
 */
public class ValidAnagram {

  public boolean isAnagram_Sorting(String s, String t) {
    char[] sArray = s.toCharArray();
    char[] tArray = t.toCharArray();
    Arrays.sort(sArray);
    Arrays.sort(tArray);

    if(s.length() != t.length()) {
      return false;
    }

    return Arrays.equals(sArray,tArray);
  }

  public boolean isAnagram_Map(String s, String t) {
    char[] sArray = s.toCharArray();
    char[] tArray = t.toCharArray();
    Map<Character, Integer> map = new HashMap<>();

    if(s.length() != t.length()) {
      return false;
    }

    for (int i = 0; i < sArray.length; i++) {
      map.put(sArray[i], i);
    }

    for(int i = 0; i < tArray.length; i++) {
      if(map.containsKey(tArray[i]) == false) {
        return false;
      }
    }
    return true;
  }
}

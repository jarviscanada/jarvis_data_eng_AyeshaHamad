package ca.jrvs.practice.dataStructure.map;

import java.util.HashMap;
import java.util.Map;

/**
 * ticket: https://www.notion.so/jarvisdev/Print-letter-with-number-45bf7989065a4655a6aef2f4837d4074
 */
public class PrintLetterWithNumber {

  /**
   * Big-O : O(n)
   * Justification : It is single loop that runs according to string length
   * 
   * @param input String of alphabets uppercase or lower case
   * @return String appended with numbers mapped.
   */
  public String printNumber(String input) {
    StringBuilder output = new StringBuilder();

    //creating a map to save values [key,value]= [a,1]...
    Map<Character, Integer> map = new HashMap<>();
    iniHashMapWithValues(map);
    char _char;

    //finding string char values in hash map
    for (int i = 0; i < input.length(); i++) {
      _char = input.charAt(i);
      if (map.containsKey(_char)) {
        output.append(_char).append(map.get(_char));
      }
    }
    return output.toString();
  }

  public void iniHashMapWithValues(Map<Character, Integer> map) {
    char ch = 'a';
    for (int i = 0; i < 52; i++) {
      if (i == 26) {
        ch = 'A';
      }
      map.put(ch, i + 1);
      ch++;
    }
  }

}

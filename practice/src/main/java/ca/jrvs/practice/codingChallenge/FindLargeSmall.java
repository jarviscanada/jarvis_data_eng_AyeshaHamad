package ca.jrvs.practice.codingChallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ticket: https://www.notion.so/jarvisdev/Find-Largest-Smallest-1c8c4a8939e943d8b478ec5058c2cd7f
 */
public class FindLargeSmall {

  public int usingForLoop(int [] num) {
    int max = num[0];
    for(int i = 0; i < num.length; i++) {
      if(num[i] > max) {
        max = num[i];
      }
    }
    return max;
  }

  public int usingJavaAPI(int [] num) {
    List<Integer> intList = new ArrayList<>(num.length);
    for(int n : num) {
      intList.add(n);
    }
    return Collections.max(intList);
  }

  public int usingStreamAPI(int [] num) {
    return Arrays.stream(num).max().getAsInt();
  }

}

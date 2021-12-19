package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;
import java.util.Set;

/**
 * https://www.notion.so/jarvisdev/Duplicates-from-Sorted-Array-7049352e2ee24c5ba092cb11e1fab80a
 */
public class RemoveDuplicatesFromSortedArray {

  public int removeDuplicates(int[] nums) {
    Set set = new HashSet();
    int lenght = nums.length-1;
    for(int i =0; i < lenght; i++){
      if(set.add(nums[i]) == false) {
        for(int j = i; j < lenght ; j++) {
          nums[j] = nums[j+1];
        }
        lenght --;
        i--;
      }
    }
    return lenght+1;
  }
  public int removeDuplicates_oneLoop(int[] nums) {
    Set<Integer> set = new HashSet();
    int j =0;
    for(int i=1; i < nums.length; i++) {
      if(set.add(nums[i]) == true) {
        j++;
        nums[j] = nums[i];
      }
    }
    return j+1;
  }
}

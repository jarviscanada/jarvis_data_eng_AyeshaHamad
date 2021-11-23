package ca.jrvs.practice.dataStructure.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ticket : https://www.notion.so/jarvisdev/Find-the-Duplicate-Number-c59fa63aed91460790fc21c96fc12182
 */
public class DuplicateNumber {

  public int findDuplicate_usingSet(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for(int n : nums) {
      if(set.add(n) == false) {
        return n;
      }
    }
    return -1;
  }

  public int findDuplicate_sorting(int[] nums) {
    Arrays.sort(nums);
    System.out.println(Arrays.toString(nums));
    int duplicateNum = 0;

    for (int i = 0; i < nums.length - 1; i++) {
      if (nums[i] == nums[i + 1]) {
        duplicateNum = nums[i];
      }
    }
    return duplicateNum;
  }

}

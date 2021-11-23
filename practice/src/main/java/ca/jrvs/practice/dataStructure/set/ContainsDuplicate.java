package ca.jrvs.practice.dataStructure.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ticket : https://www.notion.so/jarvisdev/Contains-Duplicate-d35ab0ffe13046cd9d3661b5388f9145
 */
public class ContainsDuplicate {
  public boolean containsDuplicate_usingSet(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for(int n : nums) {
      if(set.add(n) == false) {
        return true;
      }
    }
    return false;
  }

  public boolean containsDuplicate_sorting(int [] nums) {
    Arrays.sort(nums);
    for(int i = 0; i < nums.length ; i++) {
      if(nums[i] == nums[i+1]) {
        return true;
      }
    }
    return false;
  }
}

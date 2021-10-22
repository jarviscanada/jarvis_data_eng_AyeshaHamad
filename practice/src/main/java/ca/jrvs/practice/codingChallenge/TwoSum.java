package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;

/**
 * ticket : https://www.notion.so/jarvisdev/Two-Sum-d6f88a494c954b68be7606a03b145ef4
 */
public class TwoSum {

  /**
   * Big-O : O(n^2)
   * Justification : In worst case scenario our target could be last 2 indices
   * 1st loop = O(n)
   * 2nd loop = O(n)
   * O(n^2)
   *
   * Approach # 1 - Using brute force (two loops)
   *
   * @param nums   - array of int - 2 <= nums.length <= 10^4
   * @param target - sum of two number should be equal to the target set here
   * @return - array containing a pair of indices of nums array. Returns [0,0] if target not found
   */
  int[] twoSum_Approach1(int[] nums, int target) {
    int[] output = new int[2];
    boolean indicesFound = false;

    for (int i = 0; i < nums.length - 1; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        if (nums[i] + nums[j] == target) {
          output[0] = i;
          output[1] = j;
          indicesFound = true;
          break;
        }
      }
      if (indicesFound) {
        break;
      }
    }
    return output;
  }

  /**
   * Big-O : O(n)
   * Justification: Single loop iterates till lenght of array
   * Approach # 3 - Implementing for O(n)
   *
   * @param nums   - array of int - 2 <= nums.length <= 10^4
   * @param target - sum of two number should be equal to the target set here
   * @return - array of int containing a pair of indices of array.
   */
  int[] twoSum_Approach3(int[] nums, int target) {
    int[] output = new int[2];
    //Hashmap stores the [key,val] = [nums[i],i]
    HashMap<Integer, Integer> map = new HashMap<>();
    int compliment = 0;
    for (int i = 0; i < nums.length; i++) {
      compliment = target - nums[i];
      if (map.containsKey(compliment)) {
        output[0] = map.get(compliment);
        output[1] = i;
        break;
      }
      map.put(nums[i], i);
    }
    return output;
  }

}

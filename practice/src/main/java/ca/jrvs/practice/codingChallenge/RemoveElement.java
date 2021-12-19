package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/jarvisdev/Remove-Element-e70c020b97e34e61adc20cd9e4e337ca
 */
public class RemoveElement {

  public int removeElement(int[] nums, int val) {
    int j = -1;

    for(int i = 0 ; i < nums.length ; i++) {

      if(nums[i] != val ) {
        j++;
        nums[j] = nums[i];
      }
    }
    return j+1;
  }

}

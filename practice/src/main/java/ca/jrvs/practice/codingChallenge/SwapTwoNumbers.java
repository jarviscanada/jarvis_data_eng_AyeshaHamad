package ca.jrvs.practice.codingChallenge;

/**
 * ticket : https://www.notion.so/jarvisdev/Swap-two-numbers-fa537535c4c446bebee4e7e4f384bd72
 */
public class SwapTwoNumbers {

  /**
   * Big O : O(1) Justification : Each line of code complexity O(1). = O(1+1+1) = O(3) = O(1)
   *
   * @param num input array of size 2
   */
  public void swap2Numbers_Math(int[] num) {
    num[0] = num[0] + num[1];
    num[1] = num[0] - num[1];
    num[0] = num[0] - num[1];
  }

  /**
   * Big O : O(1) Justification : Each line of code complexity O(1). = O(1+1+1) = O(3) = O(1)
   *
   * @param num input array of size 2
   */
  public void swap2Numbers_Bitwise(int[] num) {
    num[0] = num[0] ^ num[1];
    num[1] = num[0] ^ num[1];
    num[0] = num[0] ^ num[1];
  }

}

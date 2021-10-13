package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Sample-Check-if-a-number-is-even-or-odd-41d69fdd372d4347b40324a129434bb4
 */
public class EvenOddNumber {

  /**
   * Big O : O(1)
   * Justification : It is simple arithmetic operation using conditional operator
   *
   * @param number that you want to check
   * @return result
   */
  public String evenOddMod(int number){
    return ( number%2 == 0 ) ? "Even" : "Odd";
  }

  /**
   * Big O : O(1)
   * Justification : Simple bitwise operation using conditional operator
   *
   * @param number that you want to check
   * @return result
   */
  public String evenOddBit(int number){
    return ( (number^1) == (number+1) ) ? "Even" : "Odd";
  }
}

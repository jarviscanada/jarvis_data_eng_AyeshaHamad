package ca.jrvs.practice.codingChallenge;

/**
 * ticket : https://www.notion.so/jarvisdev/Rotate-String-cd09ac83235c4c6b9432e396272ce672
 */
public class StringRotate {

  /**
   * Big(O) : O(n)
   * Justification : simple statement = O(1)
   *                 for loop = O(n)
   *                 O(1) + O(n)
   *                 O(n)
   *
   * @param s    : input string that will be rotated
   * @param goal : input string is searched in this goal string
   * @return : returns true if input string sequence is matched(contains) with goal string
   */
  public boolean rotateString(String s, String goal) {

    boolean flag = false;
    int lenght = s.length();

    if (lenght > goal.length()) {
      return false;
    }

    for (int i = 0; i < lenght; i++) {
      if (goal.contains(s)) {
        flag = true;
        break;
      }
      s = s.substring(lenght - 1) + s.substring(0, lenght - 1);
    }

    return flag;
  }

  /**
   * In following implementation I just wanted to rotate string with the for loop index (i).
   * Otherwise both implementations are same
   *
   * @param s : input string that will be rotated
   * @param goal : input string is searched in this goal string
   * @return
   */
  public boolean rotateString_NotEditingStrings(String s, String goal) {

    boolean flag = false;
    int lenght = s.length();
    String str = s;

    for (int i = 0; i < lenght; i++) {
      if (goal.contains(str)) {
        flag = true;
        break;
      }
      str = s.substring(lenght - 1 - i, lenght) + s.substring(0, lenght - 1 - i);
    }

    return flag;
  }
}

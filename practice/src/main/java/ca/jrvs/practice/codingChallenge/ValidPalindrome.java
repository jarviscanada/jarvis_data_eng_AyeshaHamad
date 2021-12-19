package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Valid-Palindrome-39f54412e56743478cdd1a15d82ab606
 */
public class ValidPalindrome {

  public boolean isPalindrome(String s) {

    int start = 0;
    int end = s.length()-1;

    while(start < end) {
      System.out.println(s.charAt(start) + " " + s.charAt(end));
      System.out.println(start + " " + end);
      if(s.charAt(start) != s.charAt(end)) {
        return false;
      }
      start++;
      end--;
    }
    return true;
  }

  public boolean isPalindromeRecursion(String s) {
    if(s.length() == 0 || s.length() == 1) {
      return true;
    }
    if(s.charAt(0) == s.charAt(s.length()-1)) {
      return  isPalindromeRecursion(s.substring(1, s.length() - 1));
    }
    return false;
  }

}

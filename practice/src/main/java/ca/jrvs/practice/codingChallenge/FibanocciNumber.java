package ca.jrvs.practice.codingChallenge;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ticket : https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-0d2b60be6bc74bb9810edc073f6c0292
 */
public class FibanocciNumber {

  Integer[] memo = new Integer[15];

  /*public FibanocciNumber() {
    memo = new ArrayList<>();
    //memo.stream().map(e -> null);
  }*/
  public int fib(int n) {
    if (n == 0) {
      return 0;
    } else if (n == 1) {
      return 1;
    }

    int[] array = new int[n+1];
    array[0] = 0;
    array[1] = 1;
    for(int i = 2; i <= n; i++) {
      array[i] = array[i-1] + array[i-2];
    }
    System.out.println(Arrays.toString(array));
    return array[n];
  }

  public int fib_modified(int n) {
    if (n == 0) {
      return 0;
    } else if (n == 1) {
      return 1;
    }

    int[] array = new int[3];
    array[0] = 0;
    array[1] = 1;
    for(int i = 2; i <= n; i++) {
      array[2] = array[0] + array[1];
      array[0] = array[1];
      array[1] = array[2];
    }
    System.out.println(Arrays.toString(array));
    return array[2];
  }

  public int fib_modified2(int n) {
    if (n == 0) {
      return 0;
    } else if (n == 1) {
      return 1;
    }

    int[] array = new int[2];
    array[0] = 0;
    array[1] = 1;
    for(int i = 2; i <= n; i++) {
      array[1] = array[0] + array[1];
      array[0] = array[1] - array[0];
    }
    System.out.println(Arrays.toString(array));
    return array[1];
  }

  public int fibRecursiveMemo(int n) {
    int result = 0;

    if(memo[n] != null)
      return (int)memo[n];

    if (n == 1 || n == 2)
      result = 1;
    else
      result = fibRecursiveMemo(n-1) + fibRecursiveMemo(n-2);

    memo[n]=result;
    return result;
  }

  public int fibonacciRecursion(int n) {
    if (n == 0) {
      return 0;
    } else if (n == 1) {
      return 1;
    } else {
      return fibonacciRecursion(n-1) + fibonacciRecursion(n-2);
    }
  }
}

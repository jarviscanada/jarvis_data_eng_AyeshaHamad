package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FibanocciNumberTest {

  FibanocciNumber fib ;
  @Before
  public void setUp() throws Exception {
    fib = new FibanocciNumber();
  }

  @Test
  public void fib() {

    System.out.println(fib.fib(10));
  }

  @Test
  public void fib_modified() {
    System.out.println(fib.fib_modified(10));
  }

  @Test
  public void fibRecursive() {
    System.out.println(fib.fibRecursiveMemo(10));
  }
}
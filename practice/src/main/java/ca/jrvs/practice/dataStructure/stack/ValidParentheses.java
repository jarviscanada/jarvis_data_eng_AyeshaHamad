package ca.jrvs.practice.dataStructure.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * ticket: https://www.notion.so/jarvisdev/Valid-Parentheses-443c8106575b42708b6c2b682d621534
 */
public class ValidParentheses {

  public boolean isValidParentheses(String s) {
    char[] charArray = s.toCharArray();
    Map<Character, Character> map = new HashMap();
    map.put(')','(');
    map.put('}','{');
    map.put(']','[');

    Stack<Character> stack = new Stack<>();
    //[](){}
    for(char i : charArray) {
      //put constraint before adding to stack
      if(i == '[' || i == '{' || i == '(') {
        stack.push(i);
      }
      else if(map.containsKey(i)) {
        if(stack.isEmpty()) {
          return false;
        }
        if(stack.pop() != map.get(i)) {
          return false;
        }
      }
      /*else {
        return false;
      }*/
    }
    return stack.isEmpty();
  }

}

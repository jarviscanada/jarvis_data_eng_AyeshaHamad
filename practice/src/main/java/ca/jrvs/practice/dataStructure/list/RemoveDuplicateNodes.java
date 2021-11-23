package ca.jrvs.practice.dataStructure.list;

import java.util.HashSet;

/**
 * ticket : https://www.notion.so/jarvisdev/Duplicate-LinkedList-Node-fc654f15921a4870b19fe33fc26a0395
 * @param <E>
 */
public class RemoveDuplicateNodes<E> implements JList{

  private Node head;
  private Node last;
  private int size = 0;

  public static void main(String[] args) {
    RemoveDuplicateNodes obj = new RemoveDuplicateNodes();

    obj.add(new Node(5));
    obj.add(new Node(6));
    obj.add(new Node(9));
    obj.add(new Node(6));
    obj.add(new Node(2));
    obj.add(new Node(2));
    obj.add(new Node(9));
    System.out.println("original linked list : size : " + obj.size);
    print(obj.head);
    obj.removeDuplcate(obj.head);
    System.out.println("removed duplicates from linked list : size : " + obj.size);
    print(obj.head);
  }

  public static void print(Node node) {
    while(node != null) {
      System.out.println(node.value);
      node = node.next;
    }
  }
  @Override
  public void removeDuplcate(Object start) {
    HashSet<Integer> set = new HashSet();
    Node current = (Node)start;
    Node previous = null;

    while(current != null) {
      int value = current.value;
      if(set.contains(value)) {
        previous.next = current.next;
        size--;

      } else {
        set.add(value);
        previous = current;

      }
      current = current.next;
    }
  }

  @Override
  public boolean add(Object o) {

    if(head == null) {
      head = (Node) o;
      head.next = null;

      last = head;
      last.next = head.next;
    } else {

      last.next = (Node) o;
      last = last.next;
    }
    size++;
    return false;
  }

  @Override
  public Object[] toArray() {
    return new Object[0];
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public int indexOf(Object o) {
    return 0;
  }

  @Override
  public boolean contains(Object o) {
    return false;
  }

  @Override
  public Object get(int index) {
    return null;
  }

  @Override
  public Object remove(int index) {
    return null;
  }

  @Override
  public void clear() {

  }

}

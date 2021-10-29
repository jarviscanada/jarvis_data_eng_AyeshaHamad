package ca.jrvs.practice.dataStructure.list;

import java.util.List;
import java.util.Arrays;
public class ArrayAPIs {

  public static void main(String[] args) {
    //create int array and init later
    int[] intArray = new int[10];
    intArray[0] = 10;
    intArray[1] = 200;
    intArray[2] = 5;

    //create and init array same time
    int[] inlineArray = {100,90,500};

    //create and init 2D array
    String[][] namesArray2D = {
        {"Mr. ", "Mrs. " , "Ms. "},
        {"Smith", "Jones"}
    };

    //copy array
    char[] copyFrom = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l'
    };
    char[] copyTo = new char[7];
    //srcArray,srcPos,destArray,destposStart,destPosEnd
    System.arraycopy(copyFrom, 2, copyTo, 1, 5);
    System.out.println(new String (copyTo));

    //convert an array to list
    List<String> title = Arrays.asList("Mr. ", "Mrs. ");
    title = Arrays.asList(new String[]{"Mr. ", "Mrs. "});
    System.out.println(title);

    //copy using copyOfRange
    String[] fruitArray = new String[]{"apple","orange","banana"};
    String[] anotherFruitArray = Arrays.copyOfRange(fruitArray,1,3);
    System.out.println(anotherFruitArray[0].toString());
    System.out.println(anotherFruitArray[1].toString());

    //sort fruit array
    Arrays.sort(fruitArray);
    System.out.println(fruitArray[0].toString());
    System.out.println(fruitArray[1].toString());
    System.out.println(fruitArray[2].toString());

    //binary Search works on sorted arrays
    int exact = Arrays.binarySearch(fruitArray,"banana");
    System.out.println(exact);

  }
}
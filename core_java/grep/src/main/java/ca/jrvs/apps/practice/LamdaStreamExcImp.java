package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LamdaStreamExcImp implements LambdaStreamExc{

  @Override
  public Stream<String> createStrStream(String... strings) {
    //Stream<String> stringStream = Arrays.stream(strings);
    Stream<String> stringStream = Stream.of(strings);
    return stringStream;
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {
    Stream<String> stringStream = createStrStream(strings).map(String::toUpperCase);
    return stringStream;
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    //return stringStream.filter(str -> !Pattern.matches(pattern, str));
    Stream<String> stream = stringStream.filter(str -> !Pattern.matches(pattern,str));
    return stream;
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    IntStream stream = Arrays.stream(arr);
    return stream;
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    List<E> list = stream.collect(Collectors.toList());
    return list;
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    List<Integer> intList;
    //boxed converts int to Integer
    intList = (List<Integer>) intStream.boxed().collect(Collectors.toList());
    return intList;
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    IntStream stream = IntStream.range(start,end+1);
    return stream;
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    DoubleStream stream = intStream.asDoubleStream().map(Math::sqrt);
    return stream;
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    IntStream stream = intStream.filter(i -> !(i%2 == 0) );
    return stream;
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    //test it
   Consumer<String> consumer = msg -> {
     System.out.println(prefix + msg + suffix);
   };

    return consumer;
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    //Consumer p = getLambdaPrinter("Msg:", "!");
    Stream<String> messageStream = createStrStream(messages);
    messageStream.forEach(printer);
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    getOdd(intStream).mapToObj(odd -> ((Integer) odd).toString() ).forEach(printer);
  }

  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    return null;
  }
}

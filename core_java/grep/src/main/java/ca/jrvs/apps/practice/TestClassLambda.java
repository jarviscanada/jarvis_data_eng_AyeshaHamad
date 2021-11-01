package ca.jrvs.apps.practice;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class testClassLambda {

  public static void main(String[] args) {
    LamdaStreamExcImp imp = new LamdaStreamExcImp();
    Stream stream = imp.createStrStream("a","b","c", "a");
    //stream.forEach(System.out::println);

    //stream = imp.toUpperCase("ayesha","alina");
    //stream.forEach(System.out::println);

    Stream<String> strStream = imp.filter(stream,"a");
    strStream.forEach(System.out::println);

    imp.createIntStream(1,10).forEach(System.out::println);
    imp.getOdd(imp.createIntStream(1,10)).forEach(System.out::print);

    Consumer<String> printer = imp.getLambdaPrinter("start>", "<end");
    printer.accept("Message body");

    String[] messages = {"a","b", "c"};
    imp.printMessages(messages,imp.getLambdaPrinter("Msg:", "!"));

    imp.printOdd(imp.createIntStream(1,10),imp.getLambdaPrinter("Msg:", "!"));

  }

}

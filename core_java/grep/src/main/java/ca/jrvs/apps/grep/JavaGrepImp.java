package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JavaGrepImp implements JavaGrep {

  private String regex;
  private String rootPath;
  private String outFile;

  final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  public static void main(String[] args) {
    if(args.length!=3){
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    //configuration??

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.logger.debug("message");
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    System.out.println("done");
    try {
      javaGrepImp.process();
    } catch (IOException e) {
      javaGrepImp.logger.error("Error: Unable to process", e);
    }

  }

  @Override
  public void process() throws IOException {
    listFiles("/home/centos/dev/jarvis_data_eng_AyeshaHamad/core_java/grep/data");
  }

  @Override
  public List<File> listFiles(String rootDir) {

    File folder = new File(rootDir);
    for(File file : folder.listFiles()){
      if(!file.isDirectory()){
        System.out.println("file name " + file.getName());
      }
      else
      {
        System.out.println("it is directory");
        listFiles(file.getPath());
      }
    }

    return null;
  }

  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException {
    return null;
  }

  @Override
  public boolean containsPattern(String line) {
    return false;
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {

  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}

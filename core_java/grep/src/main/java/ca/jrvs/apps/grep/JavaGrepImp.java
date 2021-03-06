package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

  private String regex;
  private String rootPath;
  private String outFile;

  final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  /**
   * CLI arguments are passed by setting following in 'Edit Configuration' ".*Romeo.*Juliet.*"
   * "./data" "./out/outFile.txt"
   *
   * @param args
   */
  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    //Using default Logger Config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);
    javaGrepImp.logger.debug("checking logger message");

    try {
      javaGrepImp.process();
    } catch (IOException e) {
      javaGrepImp.logger.error("Error: Unable to process", e);
    }
  }

  @Override
  public void process() throws IOException {
    List<File> listOfFiles = listFiles(getRootPath());

    logger.debug("Total number of files in root director : " + listOfFiles.size());
    List<String> matchedLines = new ArrayList<>();

    for (File file : listOfFiles) {
      matchedLines.addAll(readLines(file));
    }
    logger.debug("Total number of matched lines that will be written : " + matchedLines.size());
    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> fileList = new ArrayList<>();
    traverseDirectorRecursively(rootDir, fileList);
    return fileList;
  }

  public void traverseDirectorRecursively(String rootDir, List<File> fileList) {
    File folder = new File(rootDir);
    if(!folder.exists())
      return;
    for (File file : folder.listFiles()) {
      if (!file.isDirectory()) {
        fileList.add(file);
      } else {
        traverseDirectorRecursively(file.getPath(), fileList);
      }
    }
  }

  @Override
  public List<String> readLines(File inputFile)
      throws IllegalArgumentException, FileNotFoundException {

    List<String> lines = new ArrayList<>();
    BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
    String singleLine = null;

    try {
      singleLine = bufferedReader.readLine();
      while (singleLine != null) {
        if (containsPattern(singleLine)) {
          lines.add(singleLine + "\n");
        }
        singleLine = bufferedReader.readLine();
      }
      bufferedReader.close();
    } catch (IOException e) {
      logger.error("Error : unable to read line(s) in file : ", e);
    }

    logger.debug("File : " + inputFile.getName() + " : Total # of matched line(s) : " + lines.size());
    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    return Pattern.matches(getRegex(), line);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    if(lines.size()==0)
      return;
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getOutFile()));
    for (String stingLine : lines) {
      bufferedWriter.write(stingLine);
    }
    bufferedWriter.close();
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

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  public String getOutFile() {
    return outFile;
  }

}

package ca.jrvs.apps.grep;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

  /**
   * Top level search workflow
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverse the given root directory and return all file(s) in a list
   * @param rootDir
   * @return files under rootDir
   */
  List<File> listFiles(String rootDir);

  /**
   * Read a file and return all the lines
   *
   * @param inputFile file to be read
   * @return all lines in the file
   * @throws IllegalArgumentException if a given inputFile is invalid
   */
  List<String> readLines(File inputFile) throws IllegalArgumentException, FileNotFoundException;

  /**
   * Check if a line contains the regex pattern
   * @param line input string
   * @return true if pattern is matched in the line
   */
  boolean containsPattern(String line);

  /**
   * Write lines to file
   * @param lines matched with regex pattern
   * @throws IOException if failed to write output file
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  void setOutFile(String outFile);

}

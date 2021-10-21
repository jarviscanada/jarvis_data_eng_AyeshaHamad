# Introduction

This program is designed to search a string or any pattern from text files. 
It traverses the "root directory" that you enter and looks for files and sub-directories further in it. And search for the matching string in all those files, saves all matching lines. The result is then saved in an output file (outFIle.txt) that you enter.

This program is created using followings Java and Application Development concepts:
- List and ArrayList
- Interface
- Regex Pattern
- FileReader/FileWriter
- BufferedReader/BufferedWriter
- Exception Handling 
- Logger
- Maven
- Docker

# Quick Start

Compile using Jar file
````bash
#compile and package your Java code
mvn clean compile package

#Compiling using Jar file
#Usage
java -cp target/grep-1.0.jar ca.jrvs.apps.grep.JavaGrepImp [regex] [rootDirectory] [ouputFile.txt]

#Example
java -cp target/grep-1.0.jar ca.jrvs.apps.grep.JavaGrepImp ".*Romeo.*Juliet.*" "./data" "./out/grep.txt"
````

#Implemenation
## Pseudocode
`process` is the main function is which all implementation is done.
```
matchedLines []
List<File> fileList = ReadingFilesRecursively(rootDir)
For each file in fileList
    matchedLines = readLinesAndReturnMatchedLines(file) 
    appendList(matchedLines)
writeToFile (matchedLines)
```

## Performance Issue
(30-60 words)
Discuss the memory issue and how would you fix it. Not done yet

# Test
 - Manual testing executing following test cases
   - Validating if it displays error for invalid number of input parameters
   - Validating if it displays message for invalid directory path
   - Validating if it displays message for no file found
   - Validating it does not create empty output file when no string is matched
 - Debugging by using logger and printing appropriate message.

# Deployment
How you dockerize your app for easier distribution?
Not done yet.


# Improvement
List three things you can improve in this project.
- Avoiding extra loops.
- Reading data in such a way that it consumes minimum time.
- Resolve memory management issues.
- Make code more efficient.
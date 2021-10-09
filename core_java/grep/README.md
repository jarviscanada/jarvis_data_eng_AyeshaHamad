# Introduction
(50-100 words)
Discuss the design of each app. 

This program is designed to search a string or any pattern from text files. 
It traverses the "root directory" that you enter and looks for files and sub-directories further in there. And search for the matching string in all those files, saves all matching lines. The result is then saved in an output file (outFIle.txt) that you enter.

This program is created using followings Java concepts:
- List and ArrayList
- Interface
- Regex Pattern
- FileReader/FileWriter
- BufferedReader/BufferedWriter
- Exception Handling 
- Logger
- Maven

# Quick Start

1 Command Line
````
#Complie JavaGrepImp.java
javac JavaGrepImp.java

#Execute JavaGrepImp.class by passing arguments
#Usage
java JavaGrepImp [regex] [rootDirectory] [outputFile.txt]

#Example 
java JavaGrepImp ".*Romeo.*Juliet.*" "./data" "./out/outFile.txt"
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
Discuss the memory issue and how would you fix it

# Test
How did you test your application manually? (e.g. prepare sample data, run some test cases manually, compare result)

# Deployment
How you dockerize your app for easier distribution?
Not done yet.

# Improvement
List three things you can improve in this project.
- Avoiding extra loops
- Memory management
- Make code more efficient
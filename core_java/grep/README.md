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
```bash
#compile and package your Java code
mvn clean compile package

#Compiling using Jar file
#Usage
java -cp target/grep-1.0.jar ca.jrvs.apps.grep.JavaGrepImp [regex] [rootDirectory] [ouputFile.txt]

#Example
java -cp target/grep-1.0.jar ca.jrvs.apps.grep.JavaGrepImp ".*Romeo.*Juliet.*" "./data" "./out/grep.txt"
```

#Implementation

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
By default JVM heap size is 1 GB which is usually enough to accommodate the data. However, larger heap size may be required under some circumstances. Which is very important concept in this program as we are reading number of files from root directory and the file size may exceed the JVM maximum heap limit.
We need to avoid Java OutOfMemoryException while reading or processing large files.
Java File IO provide various ways of dealing with files. However, large file handling is challenging because we need to find a right balance between the speed and memory utilisation.

We are using BufferedReader in this program. Both BufferedReader and BufferedWriter are used in order to achieve greater efficiency through use of buffers. A data buffer is generally a region in memory that is temporarily used. 

But if there is any memory issue we can increase JVM minimum and maximum heap size.
````shell
java -Xms250m -Xmx250m -cp target/grep-1.0.jar ca.jrvs.apps.grep.JavaGrepImp [regex] [rootDirectory] [out.txt]
````

# Test
 - Manual testing executing following test cases
   - Validating if it displays error for invalid number of input parameters
   - Validating if it displays message for invalid directory path
   - Validating if it displays message for no file found
   - Validating it does not create empty output file when no string is matched
 - Debugging by using logger and printing appropriate message.

# Deployment
This program has been dockerized so that you can access it easily. First I will discuss how I dockerized it and then steps to deploy the dockerzied application so that you can deploy it at your own.

**Dockerizing the Grep App**
```shell
#Register Docker hub account
docker_user=docker_hub_id

#It creates connection with docker hub and prompts to enter username and password
docker login 

#Creating a dockerfile
cat > Dockerfile << EOF
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
EOF

#Pakcaging java grep app
mvn clean package

#building a new docker image locally
docker build -t ${docker_user}/grep .

#verifying docker image locally
docker image ls | grep "grep"

#pushing image to Docker Hub
docker push ${docker_user}/grep
```

**Deploying Docker Image to use Grep App**
```shell
#pull docker image from Docker Hub
#it pulls the latest image
docker pull ayeshahamad/grep

#verify docker image is pulled
docker image ls | grep "ayeshahamad/grep"

#running docker container with grep image
docker run --rm \
-v `pwd`/data:/data -v `pwd`/log:/log \
ayeshahamad/grep .*Romeo.*Juliet.* /data /log/grep.txt
```

# Improvement
List three things you can improve in this project.
- Avoiding extra loops.
- Reading data in such a way that it consumes minimum time.
- Resolve memory management issues.
- Make code more efficient.

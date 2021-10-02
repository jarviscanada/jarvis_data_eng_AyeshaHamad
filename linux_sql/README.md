# Linux Cluster Monitoring Agent

This project is designed to manage and administer Jarvis Linux Cluster, so that the LCA team can get hardware specifications and software utilization, memory/CPU management and generate reports according to their requirements. This project will help the LCA team to make decisions like if the system is fully utilized or not, or if they want to increase hardware/software specifications.

In this project we have used following technologies.
- docker to create a psql container
- bash scripts
- postgres sql
- git

# Quick Start

- Create/Start/Stop a psql instance using psql_docker.sh
```bash
./scripts/psql_docker.sh create [DB_USER_NAME] [DB_PASSWORD]
./scripts/psql_docker.sh start
./scripts/psql_docker.sh stop
```
- Create tables using ddl.sql
```shell
# Script usage
psql -h [PSQL_HOST_NAME] -p [PORT] -U [DB_USER_NAME] -d [DB_NAME] -f [FILE_NAME.sql]

# Example
psql -h "localhost" -p 5432 -U "postgres" -d "host_agent" -f "sql/ddl.sql"
```
- Insert hardware specs data into the DB using host_info.sh
```bash
# Script usage
bash scripts/host_info.sh [PSQL_HOST_NAME] [PORT] [DB_NAME] [DB_USER_NAME] [DB_PASSWORD]

# Example
bash scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "password"
```
- Insert hardware usage data into the DB using host_usage.sh
````bash
# Script usage
bash scripts/host_usage.sh [PSQL_HOST_NAME] [PORT] [DB_NAME] [DB_USER_NAME] [DB_PASSWORD]

# Example
bash scripts/host_usage.sh "localhost" 5432 "host_agent" "postgres" "password"
````
- Crontab setup
````bash
#edit crontab jobs
crontab -e

#add this to crontab to execute every minute
* * * * * bash /copy_complete_path_to_script/host_usage.sh "localhost" 5432 "host_agent" "postgres" "password"

#You can list crontab job(s) that you added
crontab -l
````

# Implemenation
In this project we are implementing MVP, so instead of executing it on Linux Cluster, this program is implemented on a single node of the cluster.
For implementing we have used 
- bash scripts to start psql docker, extract hardware and software specs, and execution of bash scripts through terminal
- POSTGRES SQL to create database and save results to tables
- installed psql client to execute psql commands and view DB results
- git and GitHub for project backup

## Architecture
Following diagram explain basic architecture of the system.

![Basic_architecture](assets/Basic_architecture.png)

## Scripts
*(I already mentioned how to use in quick start, can you guide me what to explain here, commands how to execute or actual code)*

Every script/file is explained with a diagram so that it would be easier to understand how it is executed and what is the purpose.

**Psql_docker.sh**
![Psql_docker.sh](assets/Psql_docker.png)

````shell

````

**DDL.sql**
![Creating_Tables.sh](assets/Creating_Tables.png)

````postgresql

````

**Monitoring Agent**
![Monitoring_Agent](assets/Monitoring_Agent.png)

````shell

````
````shell

````
````shell

````

**Queries.sql**
![Queries](assets/Queries.png)

````postgresql

````

Shell script description and usage (use markdown code block for script usage)
- psql_docker.sh
- host_info.sh
- host_usage.sh
- crontab
- queries.sql (describe what business problem you are trying to resolve)

## Database Modeling
Following tables host_info and host_usage are created to save data respectively. The relationship between host_info and host_usage is 1 to many.

![DatabaseModelling](assets/database_modelling.png)

# Test
To test this program we have gone through 
- Unit testing for every script and sql file. 
- Writing few simple test cases covering all possible scenarios for the scripts

**Psql_docker.sh**
  - Testing script if it displays error message for invalid parameters
  - Testing if you are recreating docker container.
  - Testing if start/stop container command is executed with no container created

**DDl.sql**
  - Testing if table are created successfully by running ddl.sql only if tables does not exist in DB

**Host_info.sh and host_usage,sh**
  - Testing if error is displayed for invalid number of parameters passed
  - Testing if correct data is extracted using bash CLI for host_info and host_usage
  - Testing if data is inserted in database and respective tables successfully 

**Queries.sql**
  - Testing if data is selected from DB and displayed correctly
  - Testing if queries are returning correct results

# Deployment
How did you deploy your app? (e.g. Github, crontab, docker)????

# Improvements
Write at least three things you want to improve????
- handle hardware update
- 
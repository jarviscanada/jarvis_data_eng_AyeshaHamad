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
```bash
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
    #first it inspect if psql container exist or not 
    docker container inspect jrvs-psql
    container_status=$?
    
    #based upon the inspect result following command is executed to create container
    docker volume create pgdata
    docker run --name jrvs-psql -e POSTGRES_USER=$db_username -e POSTGRES_PASSWORD=$db_password -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
    
    #Start/Stop psql_container
    docker container start jrvs-psql
    docker container stop jrvs-psql
````

**DDL.sql**

![Creating_Tables.sh](assets/Creating_Tables.png)

````postgresql
CREATE TABLE IF NOT EXISTS PUBLIC.host_info
(
    id                  SERIAL NOT NULL,
    hostname            VARCHAR UNIQUE NOT NULL,
    cpu_number          INTEGER NOT NULL,
    cpu_architecture    VARCHAR NOT NULL,
    cpu_model           VARCHAR NOT NULL,
    cpu_mhz             FLOAT(3) NOT NULL,
    L2_cache            INTEGER NOT NULL,
    total_mem           INTEGER NOT NULL,
    timestamp           TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (hostname)
);

CREATE TABLE IF NOT EXISTS PUBLIC.host_usage
(
    host_id             SERIAL NOT NULL,
    memory_free         INTEGER NOT NULL,
    cpu_idle            INTEGER NOT NULL,
    cpu_kernel          INTEGER NOT NULL,
    disk_io             INTEGER NOT NULL,
    disk_available      INTEGER NOT NULL,
    timestamp           TIMESTAMP NOT NULL,
    FOREIGN KEY (host_id) REFERENCES host_info (id)
);
````

**Monitoring Agent**

![Monitoring_Agent](assets/Monitoring_Agent.png)

**host_info.sh**
````shell
#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#checking for valid arguments
if [ $# -ne 5 ]; then
      echo 'Invalid Arguments'
      exit 1
fi

#extracting Host information using following commands and saving to a variable
lscpu_out=`lscpu`
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model name:" | awk '{ for(i=3;i<=NF;i++)print $i}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz:" | awk '{ print $3}')
L2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{ print $3}'| sed 's/[^0-9]*//g')
total_mem=$(grep "^MemTotal" /proc/meminfo | awk '{ print $2 }')
timestamp=$(date -u "+%Y-%m-%d %H:%M:%S")

#Creating insert statement
insert_stmt="INSERT INTO host_info(
                                    hostname,
                                    cpu_number,
                                    cpu_architecture,
                                    cpu_model,
                                    cpu_mhz,
                                    L2_cache,
                                    total_mem,
                                    timestamp
                                  )
                        VALUES    (
                                    '$hostname',
                                     $cpu_number,
                                    '$cpu_architecture',
                                    '$cpu_model',
                                     $cpu_mhz,
                                     $L2_cache,
                                     $total_mem,
                                    '$timestamp'
                                  )"
export PGPASSWORD=$psql_password                                  
#executing insert statement using parameters provided                      
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit ?
````
**host_usage.sh**
````shell
#!/bin/bash

#assigning arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#checking for valid arguments
if [ $# -ne 5 ]; then
      echo 'Invalid Arguments'
      exit 1
fi

#saving results in variable for host usage
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)
memory_free=$(echo "$vmstat_mb" | awk '{print $4}'| tail -n1 | xargs)
cpu_idle=$(echo "$vmstat_mb" | awk '{print $15}' | tail -n1 | xargs)
cpu_kernel=$(echo "$vmstat_mb" | awk '{print $14}' | tail -n1 | xargs)
disk_io=$(echo "$vmstat_mb" | awk '{print $10}' | sed 's/[^0-9]*//g'  | xargs)
disk_available=$(df -BM / | awk '{print $4}' | tail -1 | sed 's/[^0-9]*//g' | xargs)
timestamp=$(date -u "+%Y-%m-%d %H:%M:%S")

#creating a subqurey to get host id from host_info table
host_id="(SELECT id FROM host_info
          WHERE hostname='$hostname')";

#creating insert statement
insert_stmt="INSERT INTO host_usage(
                                    host_id,
                                    memory_free,
                                    cpu_idle,
                                    cpu_kernel,
                                    disk_io,
                                    disk_available,
                                    timestamp
                                  )
                        VALUES    (
                                     $host_id,
                                     $memory_free,
                                    '$cpu_idle',
                                    '$cpu_kernel',
                                     $disk_io,
                                     $disk_available,
                                    '$timestamp'
                                  )"
export PGPASSWORD=$psql_password                  
#executing  insert statement
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
````

**Crontab**
````shell
#edit crontab jobs
crontab -e

#add this to crontab to execute every minute
* * * * * bash /copy_complete_path_to_script/host_usage.sh "localhost" 5432 "host_agent" "postgres" "password"

#You can list crontab job(s) that you added
crontab -l
````

**Queries.sql**

![Queries](assets/Queries.png)

**Query#1**
Following query group host by CPU number and display sorted result for total memory within each group
````postgresql
SELECT cpu_number,
       id,
       total_mem,
       row_number()  OVER
            (
                PARTITION BY cpu_number
                ORDER BY total_mem DESC
            )
FROM host_info;
````
**Query#2**
Following query displays average used memory in percentage over 5 minutes interval for each host. So that LCA team can know that how much memory is utilized and if they should increase their resources.
Round5 is the function that rounds up the timestamp to nearest 5 minutes interval.
````postgresql
SELECT host_id,
       round5( host_usage.timestamp ),
       trunc( avg( host_info.total_mem-memory_free ) / avg( host_info.total_mem ) * 100,1)
           as avg_used_memory_percentage
FROM host_usage
LEFT JOIN host_info
    ON host_usage.host_id=host_info.id
group by round5, host_id
order by host_id;
````
**Query#3**
Following query detects server failure if crontab is not executed and number of data points inserted are less than 3 in 5 minutes of interval. 
````postgresql
SELECT host_id,
       round5(host_usage.timestamp),
       COUNT(host_id) AS num_data_points
FROM host_usage
GROUP BY host_id, round5
HAVING count(host_id)<=2
ORDER BY host_id, round5;
````

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
How did you deploy your app? (e.g. Github, crontab, docker)???? Can you guide me 

# Improvements
Write at least three things you want to improve????
- handle hardware update
- 
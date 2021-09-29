#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -ne 5 ]; then
      echo 'Invalid Arguments'
      exit 1
fi

lscpu_out=`lscpu`
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model name:" | awk '{ for(i=3;i<=NF;i++)print $i}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz:" | awk '{ print $3}')
L2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{ print $3}'| sed 's/[^0-9]*//g')
total_mem=$(grep "^MemTotal" /proc/meminfo | awk '{ print $2 }')
timestamp=$(date -u "+%Y-%m-%d %H:%M:%S")

#host_id="(SELECT id FROM host_info
#          WHERE host_name='$hostname')";

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
echo "$insert_stmt"
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

exit $?
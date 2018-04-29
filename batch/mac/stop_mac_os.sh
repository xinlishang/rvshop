#!/bin/bash

# Setup base directory and environment
SCRIPT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
BASE_DIR="$SCRIPT_DIR/"
source ${BASE_DIR}/common_mac_os.sh

# Find out the process and kill it
PID=`ps -u $PROC_USER -o pid,args= | grep "java" | grep "$JAR_FILE" | grep "server" | awk ' { print $1 }'`
echo "pid: $PID"
if [ "$PID" != "" ] ; then
    log "Killing existing PID: $PID"
    sudo -u $PROC_USER kill -9 $PID
else
    echo "there is no process found"
fi

PID=`ps -u $PROC_USER -o pid,args= | grep "java" | grep "$JAR_FILE" | grep "db migrate"| awk ' { print $1 }'`
echo "pid: $PID"
if [ "$PID" != "" ] ; then
    log "Killing existing PID: $PID"
    sudo -u $PROC_USER kill -9 $PID
else
    echo "there is no process found"
fi
#!/bin/bash

# function
log() {
  echo "[`date`] $@"
}

# Setup JAVA
# which java

# Some other variables
USER=`whoami`
HOST=`hostname`
PROC_USER="shangx"
JAR_FILE="rvshop-0.7.0-SNAPSHOT.jar"
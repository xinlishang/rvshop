#!/bin/bash

# Setup base directory and environment
SCRIPT_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
BASE_DIR="$SCRIPT_DIR/.."
source ${BASE_DIR}/batch/mac/common_mac_os.sh
LOG_DIR="$BASE_DIR/log"
cd $BASE_DIR

# If log directory doesn't exist, create it.
if [ ! -d "$LOG_DIR" ]; then
    mkdir -p $LOG_DIR
    chmod -R 777 $LOG_DIR
fi

# Stop current process
./config/mac/stop_mac_os.sh

# JVM config
JVM_CONFIGS="-Xms2G -Xmx2G -XX:NewSize=512m -XX:MaxNewSize=2048m \
                   -XX:+AlwaysPreTouch -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC \
                   -XX:CMSInitiatingOccupancyFraction=70 -XX:SurvivorRatio=2 -XX:+PrintTenuringDistribution \
                   -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationStoppedTime \
                   -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCTimeStamps \
                   -Xloggc:$LOG_DIR/gc-rvshop.log"
log "Config JVM as $JVM_CONFIGS"

RV_CONFIG_FILE="config/staging.yml"
log "Using config file $RV_CONFIG_FILE"

log "Starting rv shop"
nohup java -jar target/$JAR_FILE db migrate $RV_CONFIG_FILE >>log/rvdb.out 2>>log/rvdb.err &
nohup java -cp target/$JAR_FILE RvShopApplication server $RV_CONFIG_FILE >>log/rv.out 2>>log/rv.err &

# Test
curl -s -k http://localhost:8082/hello-world >log/curl.out
sleep 1
if grep -q "Hello, ViaRV!" log/curl.out; then
  log "success!!!!!!!!!!!!!"
else
  log "failed! the expected output string (Hello, ViaRV!is is not found of 'curl http://localhost:8082/hello-world'"
fi

exit 0

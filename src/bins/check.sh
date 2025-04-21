#!/usr/bin/env bash

set -e

export LC_ALL=en_US.UTF-8

APP_NAME="hkvb-ebank-opening-app"
APP_LOG_HOME=$(cd "$(dirname "$0")/../logs";pwd)
#echo "${APP_LOG_HOME}"
PID_FILE="${APP_LOG_HOME}/application*.pid"
#echo "${PID_FILE}"
echo "start check PID"
PID=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')
if [[ -z "${PID}" ]]; then
    echo "no $APP_NAME process, check failed"
    exit 2
fi

PID=""
for i in `seq 0 60`
do
    if [ -f ${PID_FILE} ]; then
        PID=`cat ${PID_FILE}`
        break
    fi
    echo "wait boot process, ${i}s"
    sleep 1
done

if [[ "${PID}" == "" ]]; then
    echo "${PID_FILE} not exists, check failed"
    exit 2
fi

CHECK_MSG=`ps -p ${PID}`
CHECK_RESULT=$?
# echo "current pid: [${PID}] process info: ${CHECK_RESULT}"

if [[ "${CHECK_RESULT}" == "0" ]]; then
    echo "check process success, $APP_NAME is runing with pid=$PID"
    exit 0
else
    echo "check process failed, check info:${CHECK_MSG}"
    exit 3
fi

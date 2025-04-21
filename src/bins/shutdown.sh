#!/usr/bin/env bash

set -e

export LC_ALL=en_US.UTF-8

APP_HOME=$(cd "$(dirname "$0")/..";pwd)
APP_LOG_HOME=${APP_HOME}/logs
PID_FILE="${APP_HOME}/logs/application*.pid"

PID_FILE_COUNT=$(find ${APP_LOG_HOME}/ | grep '.pid$' | wc -l)
if [ ${PID_FILE_COUNT} -gt 1 ];then
  echo "PID file count:${PID_FILE_COUNT}, greater than 1, execute delete PID file."
  # 获取当前正在运行的进程pid，
  OLD_PID=$(ps axw | grep "${APP_HOME}/" | grep bin/java | grep -v grep | awk {'print $1'})
  # 删除多余的pid文件
  $(find ${APP_LOG_HOME}/ | grep '.pid$' | grep -v ${OLD_PID}| xargs rm -f)
elif [ ${PID_FILE_COUNT} -eq 0 ];then
  exit 0;
else
  echo "the number of PID files is correct."
fi


PID=$(cat ${PID_FILE})
RESULT=$(ps axw | grep "${APP_HOME}/" | grep ${PID} | grep bin/java | grep -v grep | wc -l)
if [ ${RESULT} -ge 1 ];then
    echo -ne "${APP_HOME} will be killed\n"
    ps axw | grep "${APP_HOME}/" | grep ${PID} | grep bin/java | grep -v grep | awk '{print("echo killing process:"$1"&kill "$1)}' | sh
else
    echo "${APP_HOME} not exists !!!"
    exit 0
fi

# defunct进程10秒检测
for i in `seq 0 2`
do
    sleep 1
    RESULT=$(ps axw | grep ${PID} | grep defunct | grep java | grep -v grep | wc -l)
    if [ ${RESULT} -ge 1 ];then
      echo "defunct process found, prepare to execute pstack, pid[${PID}]"
      pstack ${PID} >> ${APP_LOG_HOME}/pstack_${PID}.log
      break;
    else
      echo "defunct process not found"
    fi
done

#10秒检测
for i in `seq 0 9`
do
    sleep 1
    RESULT=$(ps axw | grep "${APP_HOME}/" | grep ${PID} | grep bin/java | grep -v grep | wc -l)
    if [ ${RESULT} -ge 1 ];then
      echo "process not exit yet"
    else
      break;
    fi
done


RESULT=$(ps axw | grep ${PID} | grep java | grep -v grep | wc -l)
if [ ${RESULT} -ge 1 ];then
    echo -ne "${APP_HOME} process count:${RESULT}\n"
    echo -ne "${APP_HOME} will be killed force\n"
    ps axw | grep ${PID} | grep java | grep -v grep | awk '{print("echo killing process:"$1" force&kill -9 "$1)}' | sh
    # 再次检查进程是否完全退出
    sleep 1
    RESULT=$(ps axw | grep ${PID} | grep java | grep -v grep | wc -l)
    if [ ${RESULT} -ge 1 ];then
      ps axw | grep ${PID} | grep java | grep -v grep | awk '{print("echo killing process:"$1" force again &kill -9 "$1)}' | sh
    else
      echo "killed -9 ${PID} success"
    fi
else
    echo "shutdownServer success"
fi

#检查PID
PID_FILE_OLD="${APP_HOME}/logs/application_${PID}.pid"
if [ -f ${PID_FILE_OLD} ];then
    echo "remove ${PID_FILE_OLD}"
    rm -f ${PID_FILE_OLD}
fi
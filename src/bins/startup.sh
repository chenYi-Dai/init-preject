#!/usr/bin/env bash

if [ "$(whoami)" != "product_pub" ]; then
  echo -e "current user is $(whoami) not product_pub !"
  exit 0
fi

set -e

export LC_ALL=en_US.UTF-8
export LANG=en_US.UTF-8
umask 022
#ulimit -c 0
source /etc/profile

echo "Begin to startup:"
APP_HOME=$(cd "$(dirname "$0")/..";pwd)
# 应用名称
APP_NAME="init-project"
APP_LOG_HOME=${APP_HOME}/logs
APP_GC_HOME=${APP_LOG_HOME}/gc
APP_LIB_HOME=${APP_HOME}/lib
APP_CONF_HOME=${APP_HOME}/conf
APP_TEMP_DIR=${APP_HOME}/tmp

#检查并创建log的目录
if [[ ! -d ${APP_LOG_HOME} && ! -h ${APP_LOG_HOME} ]];then
    echo "${APP_LOG_HOME} not exists"
    mkdir ${APP_LOG_HOME}
fi
if [[ ! -d ${APP_GC_HOME} && ! -h ${APP_GC_HOME} ]];then
    echo "${APP_GC_HOME} not exists"
    mkdir ${APP_GC_HOME}
fi
if [[ ! -d ${APP_TEMP_DIR} && ! -h ${APP_TEMP_DIR} ]];then
    echo "${APP_TEMP_DIR} not exists"
    mkdir ${APP_TEMP_DIR}
fi

if [[ -f "${APP_CONF_HOME}/setenv.conf" ]];then
    sed -i 's/\r//g' ${APP_CONF_HOME}/setenv.conf
    while IFS='=' read -r key value
    do
        #过滤空行
        if [[ ${key} != "" && ${value} != "" ]];then
            #只读取指定变量，其余忽略
            if [[ ${key} == "APP_LOG_HOME" ]] || [[ ${key} == "JAVA_HOME" ]] || [[ ${key} == "JAVA_OPTS" ]] || [[ ${key} == "JAVA_MAIN_CLASS" ]] || [[ ${key} == "JAVA_LIBRARY_PATH" ]];then
                eval "${key}='${value}'"
            fi
        fi
    done < ${APP_CONF_HOME}/setenv.conf
else
    echo "${APP_CONF_HOME}/setenv.conf not exists"
    exit -1
fi
JAVA_OPTS="-server ${JAVA_OPTS}"

if [[ "DEBUG" = $1 ]];then
#开启debug
JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=2345"
fi
#性能相关
JAVA_OPTS="${JAVA_OPTS} -XX:-UseBiasedLocking -XX:-UseCounterDecay -XX:AutoBoxCacheMax=20000"

#G1 GC
JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
#GC LOG
JAVA_OPTS="${JAVA_OPTS} -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
JAVA_OPTS="${JAVA_OPTS} -Xloggc:${APP_GC_HOME}/gc_%t.log"
#异常日志
JAVA_OPTS="${JAVA_OPTS} -XX:ErrorFile=${APP_LOG_HOME}/hs_err_%p.log"
JAVA_OPTS="${JAVA_OPTS} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${APP_LOG_HOME}"
JAVA_OPTS="${JAVA_OPTS} -XX:OnError=\"$JAVA_HOME/bin/jstack %p > ${APP_LOG_HOME}/java_error_%p.log\""
#其它参数
JAVA_OPTS="${JAVA_OPTS} -Dspring.config.location=${APP_CONF_HOME}/"
JAVA_OPTS="${JAVA_OPTS} -Dpolaris.log.home=${APP_LOG_HOME}/polaris"
JAVA_OPTS="${JAVA_OPTS} -Dconsumer.localCache.persistDir=${APP_TEMP_DIR}/polaris"
JAVA_OPTS="${JAVA_OPTS} -Dfile.encoding=UTF-8 -Dlog.home=${APP_LOG_HOME} -Dio.netty.leakDetectionLevel=advanced"
JAVA_OPTS="${JAVA_OPTS} -Djava.io.tmpdir=${APP_TEMP_DIR} -Djna.tmpdir=${APP_TEMP_DIR}"
if [[ ${JAVA_LIBRARY_PATH} = "" ]];then
    JAVA_OPTS="${JAVA_OPTS} -Djava.library.path=${APP_LIB_HOME}"
else
    JAVA_OPTS="${JAVA_OPTS} -Djava.library.path=${APP_LIB_HOME}:${JAVA_LIBRARY_PATH}"
fi

#classpath将config放在前边，避免找classpath资源时先去找lib目录中的jar,如果lib中的jar有打包配置文件的话，就会先使用jar中的配置文件
CLASS_PATH="-Xbootclasspath/a:${APP_CONF_HOME}:${APP_LIB_HOME}/*"

if [[ ! -d $JAVA_HOME && ! -h $JAVA_HOME ]];then
    echo "JAVA_HOME dir not exists"
    exit -1
fi

echo `date` >> ${APP_LOG_HOME}/$APP_NAME.log
#add jacoco
jacoco_tools=""
if [ "DEVTEST" == $RPM_ENV ];
then
    test_tools="/data/pcov_agent/jacoco/base_tools/auto_startup.sh"
    jacoco_tools=`test -e $test_tools && source $test_tools`
fi
echo "nohup $JAVA_HOME/bin/java ${JAVA_OPTS} ${CLASS_PATH} ${jacoco_tools} -jar $APP_HOME/$APP_NAME.jar  $SERVER_OTPS >>$APP_HOME/logs/$APP_NAME.log 2>&1 &"
eval "nohup $JAVA_HOME/bin/java ${JAVA_OPTS} ${CLASS_PATH} ${jacoco_tools} -jar $APP_HOME/$APP_NAME.jar  $SERVER_OTPS >>$APP_HOME/logs/$APP_NAME.log 2>&1 &"

echo "server process started"
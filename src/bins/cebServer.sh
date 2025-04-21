#!/bin/bash

if [ "$(whoami)" != "product_pub" ]; then
  echo "current user is $(whoami) not product_pub"
  exit 0
fi

SERVICE_LIST=(
  "hkvb_corp_ac_opening_service"
  "hkvb_corp_ebank_base_service"
  "hkvb_corp_ebank_batch_service"
  "hkvb_corp_ebank_customer_service"
  "hkvb_corp_ebank_general_service"
  "hkvb_corp_ebank_inner_gateway"
  "hkvb_corp_ebank_outer_gateway"
  "hkvb_corp_ebank_platform_service"
  "hkvb_corp_ebank_wkfl_service"
)

APP_BASE_DIR="/data/apps"

LOCAL_IP=$(hostname -I | awk '{print $1}')
DEPLOYED_SERVICES=()

get_deployed_services() {

  for service in "${SERVICE_LIST[@]}"; do
    if [ -d "$APP_BASE_DIR/$service" ]; then
        DEPLOYED_SERVICES+=("$service")
    fi
  done

  echo
  echo "Deployed services on ${LOCAL_IP}:"
  echo "----------------------------------------"
  for srv in "${DEPLOYED_SERVICES[@]}"; do
    echo "  $srv"
  done
  echo
}

del_pid_file() {
  srv="$1"
  pid_file_count=$(find /APP_BASE_DIR/$srv/logs -name "application*.pid" -type f | wc -l)
  if [ $pid_file_count -gt 0 ]; then
    find /APP_BASE_DIR/$srv/logs -name "application*.pid" -type f -exec rm {} \;
  fi
}

start_all() {

  for srv in "${DEPLOYED_SERVICES[@]}"; do
    echo
    echo "start $srv ..."

    del_pid_file $srv

    cd $APP_BASE_DIR/$srv/bin
    sh startup.sh
    sleep 1
  done
}

check_all() {

  for srv in "${DEPLOYED_SERVICES[@]}"; do
    cd $APP_BASE_DIR/$srv/bin
    re=$(sh check.sh | grep success | grep -v grep)
    if [ -n "$re" ]; then
      echo "[PASS] $srv is running"
    else
      echo "[FAIL] can't find $srv"
    fi
  done
  echo
}

shutdown_all() {
  for srv in "${DEPLOYED_SERVICES[@]}"; do
    echo
    echo "shutdown $srv ..."

    cd $APP_BASE_DIR/$srv/bin
    sh shutdown.sh
    sleep 1
  done
  echo
}

kill_9_all() {
  echo
  for srv in "${DEPLOYED_SERVICES[@]}"; do
    echo "kill -9 $srv"
    kill -9 $(ps -ef | grep ${srv} | grep -v grep | awk '{print $2}')

    del_pid_file $srv
  done
}

restart_all() {

  for srv in "${DEPLOYED_SERVICES[@]}"; do
    echo
    echo "restart $srv ..."

    cd $APP_BASE_DIR/$srv/bin
    sh restart.sh
  done
}

case $1 in
  start)
    get_deployed_services
    start_all
    ;;
  stop)
    get_deployed_services
    shutdown_all
    ;;
  kill9)
    get_deployed_services
    kill_9_all
    ;;
  restart)
    get_deployed_services
    restart_all
    ;;
  check)
    get_deployed_services
    check_all
   ;;
  *)
    echo
    echo "  Usage: $0 {start|stop|kill9|restart|check} " >&2
    echo
esac

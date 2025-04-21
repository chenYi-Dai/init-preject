#!/usr/bin/env bash

if [ "$(whoami)" != "product_pub" ]; then
  echo -e "current user is $(whoami) not product_pub !"
  exit 0
fi

export LC_ALL=en_US.UTF-8
set -x

DIR=`dirname "$0"`
echo "shutdown..."
${DIR}/shutdown.sh
echo "startup..."
${DIR}/startup.sh
echo "check...something"
${DIR}/check.sh
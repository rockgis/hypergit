#!/bin/bash

SCRIPT_BIN="${BASH_SOURCE-$0}"
SCRIPT_BIN="$(dirname "${SCRIPT_BIN}")"
SCRIPT_HOME="$(cd "${SCRIPT_BIN}/.."; pwd)"
LIB_PATH=$SCRIPT_HOME/libs

addParameter()
{

  if [ -z "$JVAV_PARAMETER" ]; then
    JAVA_PARAMETER="-Djava.net.preferIPv4Stack=true -Dlogging.config=$SCRIPT_HOME/config/logback-spring.xml"
  fi
  JAVA_PARAMETER=$JAVA_PARAMETER" "$1
}

start()
{
    java -jar $JAVA_PARAMETER "$LIB_PATH"/hypergit_idp-0.0.1-SNAPSHOT.jar &
}
stop() {
  echo "stop application"
  kill $(cat "$SCRIPT_HOME"/pid/hypergitIdp.pid)
}

helpFunction()
{
   echo "Usage: $0 [option...] {start|stop|restart}" >&2
   echo -e "\t-p, --profile run with given active profie : local, prod, dev"
   echo -e "\t-c, --config run with external config file"
   exit 1 # Exit script after printing help
}


while :
do
  case "$1" in
    -p | --profiles)
      if [ $# -ne 0 ]; then
        addParameter "-Dspring.profiles.active=$2"
      fi
      shift 2
      ;;
    -c | --config)
      if [ $# -ne 0 ]; then
          addParameter "-Dspring.config.location=$2"
      fi
      shift 2
      ;;
    -h | --help)
      helpFunction
      exit 0
      ;;
    --)
      shift
      break
      ;;
    -*)
      echo "Error: Unknown option: $1" >&2
      exit 1
      ;;
    *)  # No more options
      break
      ;;
  esac
done

case "$1" in
  start)
    start
    ;;
  stop)
    stop
    ;;
  restart)
    stop
    start
    ;;
  *)
     helpFunction
     exit 1
     ;;
esac
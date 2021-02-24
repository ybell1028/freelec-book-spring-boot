#!/usr/bin/env bash

#readlink 명령어 : 심볼릭 파일의 경로를 읽는다. (바로가기의 원본 파일 경로)
#-f 옵션: 심볼릭 링크를 따라 최종의 파일을 절대경로로 반환.
ABSPATH=$(readlink -f $0)
#ABSDIR=$(dirname $ABSPATH) - 현재 stop.sh가 속해 있는 경로를 찾습니다.
#하단의 코드와 같이 profile.sh의 경로를 찾기 위해 사용됩니다.
ABSDIR=$(dirname $ABSPATH)
#source ${ABSDIR}/profile.sh - 자바로 보면 일종의 import 구문입니다.
#해당 코드로 인해 stop.sh에서도 profile.sh의 여러 function을 사용할 수 있게 됩니다.
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi
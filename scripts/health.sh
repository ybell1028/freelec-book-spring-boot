#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh #3. 엔진엑스 프록시 설정 변경은 switch.sh에서 수행합니다.

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start!"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  #1. 엔진엑스와 연결되지 않은 포트로 스프링 부트가 잘 수행되었는지 체크
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  #grep - 파일 내에서 지정한 패턴이나 문자열을 찾은 후에, 그 패턴을 포함하고 있는 모든 행을 표준 출력해 준다.
  #wc - 사용자가 지정한 파일의 행, 단어, 문자수를 세는 명령어 (옵션 -l: 행 개수)
  UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1 ("real" 문자열이 있는지 검증)
      echo "> Health check 성공"
      switch_proxy #2. 잘 떴는지 확인되어야 엔진엑스 프록시 설정을 변경합니다.
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다."
      echo "> Health check: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done
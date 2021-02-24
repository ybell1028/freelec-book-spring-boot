#!/usr/bin/env bash

# bash는 return value가 안되니 *제일 마지막줄에 echo로 해서 결과 출력*후, 클라이언트에서 값을 사용한다

# 쉬고 있는 profile 찾기: real1이 사용중이면 real2가 쉬고 있고, 반대면 real1이 쉬고 있음
function find_idle_profile()
{
    #현재 엔진엑스가 바라보고 있는 스프링 부트가 정상 수행 중인지 확인합니다.
    #응답값을 HttpStatus로 받습니다.
    #정상이면 200, 오류가 발생한다면 400~503 사이로 발생하니 400 이상은 모두 예외로 보고 real2를 현재 profile로 사용합니다.

    #curl 옵션
    #-s	--silent	진행 내용 등을 출력하지 않음. 응답코드 정도 확인 시에 유용함
    #-o	--output FILE	표준 출력대신에 해당 FILE에 출력함. 다운로드 시에 유용하게 사용
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=real2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    #IDLE_PROFILE
    #엔진엑스와 연결되지 않은 profile입니다.
    #스프링 부트 프로젝트를 이 profile로 연결하기 위해 반환합니다.
    if [ ${CURRENT_PROFILE} == real1 ]
    then
      IDLE_PROFILE=real2
    else
      IDLE_PROFILE=real1
    fi

    #bash라는 스크립트는 __값을 반환하는 기능이 없습니다.__
    #그래서 __제일 마지막줄에 echo로 결과를 출력 후__, 클라이언트에서 그 값을 잡아서($((find_idle_profile)) 사용합니다.
    #중간에 echo를 사용해선 안됩니다.
    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}
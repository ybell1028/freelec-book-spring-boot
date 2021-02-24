#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"

    #tee: 표준출력(stdout)을 화면과 파일로 동시에 출력하는 명령어
    #쌍따옴표 (") : 변수의 실제 값을 출력
    #홀따옴표 (') : 변수명을 그대로 출력

    #echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" - 하나의 문장을 만들어 파이프라인(|)으로 넘겨주기위해 echo를 사용합니다.
    #엔진엑스가 변경할 프록시 주소를 생성합니다.
    #쌍따옴표를 사용하지 않으면 $service_url을 그대로 인식하지 못하고 변수를 찾게 됩니다.

    #| sudo tee /etc/nginx/conf.d/service-url.inc
    #앞에서 넘겨준 문장을 service-url.inc에 덮어씁니다.
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    echo "> 엔진엑스 Reload"
    #엔진엑스 설정을 다시 불러옵니다.(restart와는 다릅니다.)
    #reload는 끊김 없이 다시 불러옵니다. 다만, 중요한 설정들은 반영되지 않으므로 restart를 사용해야합니다.
    #여기선 __외부의 설정 파일__인 service-url을 다시 불러오는 거라 reload로 가능합니다.
    sudo service nginx reload
}


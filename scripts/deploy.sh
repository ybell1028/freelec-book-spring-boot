#!/bin/bash

#REPOSITORY=/home/ec2-user/app/step1
#프로젝트 디렉토리 주소는 스크립트 내에서 자주 사용하는 값이기 때문에 이를 변수로 저장합니다
#마찬가지로 PROJECT_NAME = ...도 동일하게 변수로 저장합니다.
#쉘에서는 타입 없이 선언하여 저장합니다. 쉘에서는 $ 변수명으로 변수를 사용할 수 있습니다.
REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=freelec-book-spring-boot
echo "> Build 파일 복사"

#build의 결과물인 jar 파일을 복사해 jar 파일을 모아둔 위치로 복사합니다.
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동 중인 애플리케이션 pid 확인"

#스프링 부트 애플리케이션의 프로세스 ID를 찾아 실행 중이면 종료합니다
#PROJECT_NAME으로 된 다른 프로그램들이 있을 수 있어 jar 프로세스를 찾은 뒤 ID를 찾습니다.
CURRENT_PID=$(pgrep -fl ${PROJECT_NAME} | grep jar | awk '{print $1}')
echo "현재 구동 중인 애플리케이션 pid : $CURRENT_PID"

#현재 구동 중인 프로세스가 있는지 없는지를 판단해서 기능을 수행합니다.
#PID 값을 보고 프로세스가 있으면 해당 프로세스를 종료합니다.
if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 애플리케이션 배포"

#새로 실행할 jar 파일명을 찾습니다.
#여러 jar 파일이 생기기 때문에 tail -n로 가장 나중의 jar 파일(최신 파일)을 변수에 저장합니다.
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME에 실행권한 추가"

chmod +x $JAR_NAME

#$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
#nohup 실행 시 CodeDeploy는 **무한 대기** 합니다.
#이 이슈를 해결하기 위해 nohup.out 파일을 표준 입출력용으로 별도로 사용합니다.
#이렇게 하지 않으면 nohup.out 파일이 생기지 않고, CodeDeploy 로그에 표준 입출력이 출력됩니다.
#nohup이 끝나기 전까지 CodeDeploy도 끝나지 않으니 꼭 이렇게 해야만 합니다.
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties,classpa    th:/application-real.properties \
    -Dspring.profiles.active=real \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
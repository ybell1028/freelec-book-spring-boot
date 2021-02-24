#!/usr/bin/env bash

#기본적인 스크립트는 step2의 deploy.sh와 유사합니다.
#다른 점이라면 IDLE_PROFILE을 통해 properties 파일을 가져오고(application-#IDEL_PROFILE.properties)
#active profile을 지정하는 것(-Dspring.profiles.active=$IDLE_PROFILE)뿐입니다.

ABSPATH=$(readlink -f $0) #자신(파일)의 절대 경로
ABSDIR=$(dirname $ABSPATH) #자신이 속한 폴더의 절대경로
source ${ABSDIR}/profile.sh #여기서도 IDLE_PROFILE을 사용하니 profile.sh을 가져와야 합니다.

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=freelec-book-spring-boot

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1) #tail -n로 가장 나중의 jar 파일(최신 파일)을 변수에 저장합니다.

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=$IDLE_PROFILE \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
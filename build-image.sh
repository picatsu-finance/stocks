#! /bin/bash

./gradlew clean build -x test

JAR_FILE=$(ls build/libs/ | grep "^finance-stock")

docker build . --build-arg jar=build/libs/$JAR_FILE -t ezzefiohez/finance-stock
docker push ezzefiohez/finance-stock





echo " ######## BUILD STOCK DONE ######## "

curl  -X POST http://94.239.109.172:9000/api/webhooks/fc96fe93-45e6-4ea7-98b8-2cb65a5d4e25

#! /bin/bash

./gradlew clean build -x test

JAR_FILE=$(ls build/libs/ | grep "^finance-stock")

docker build . --build-arg jar=build/libs/$JAR_FILE -t ezzefiohez/finance-stock
docker push ezzefiohez/finance-stock

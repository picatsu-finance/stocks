#! /bin/bash

./gradlew clean build -x test

JAR_FILE=$(ls build/libs/ | grep "^finance-stock")

docker build . --build-arg jar=build/libs/$JAR_FILE -t ezzefiohez/finance-stock
docker push ezzefiohez/finance-stock


echo " ######## BUILD STOCK DONE ######## "

curl  -X POST http://146.59.195.214:9000/api/webhooks/35761d61-262a-45bc-a04a-335041f134f8

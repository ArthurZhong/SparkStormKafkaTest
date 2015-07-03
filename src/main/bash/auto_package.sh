#!/bin/bash

cd SparkStormKafkaTest
git fetch --all
git reset --hard origin/master

if [[ ! `mvn dependency:list | grep com.walmart.labs.pcs:SparkStormKafkaTest` =~ .*SNAPSHOT.* ]]; then
  echo "Maven dependency on SparkStormKafkaTest for SpringBootRestfulService is not SNAPSHOT"
  mail -s 'SpringBootRestfulService Build aborted due to non-SNAPSHOT Maven dependency on SparkStormKafkaTest' abc@abc.com </dev/null
  exit 1
fi

mvn clean package -U -P cluster
STATUS=$?
if [ $STATUS -eq 0 ]; then
  cp target/uber-SparkStormKafkaTest-*-SNAPSHOT.jar ../
else
  echo "Maven clean install Failed for SparkStormKafkaTest"
  exit 1
fi

mvn dependency:go-offline
cd ..
cp SparkStormKafkaTest/parentpom/simple-parent.xml pom.xml

#Get the latest rule daily
cd SpringBootRestfulService
git fetch --all
git reset --hard origin/master
cd ..

cd SparkStormKafkaTest
git pull
cd ..
mvn clean package -o -nsu -P cluster
STATUS=$?
cd SparkStormKafkaTest
if [ $STATUS -eq 0 ]; then
  echo "Maven clean install Successful for SparkStormKafkaTest"
  cp target/uber-SparkStormKafkaTest-*-SNAPSHOT.jar ../
  cd ../SpringBootRestfulService
  git add src/
  git commit -m '[jenkins auto build]: Rerun the latest SparkStormKafkaTest changes'
  git push origin master
else
  echo "Maven clean install Failed for SparkStormKafkaTest"
  cd ../SparkStormKafkaTest
  git --no-pager diff | tee git_diff_SparkStormKafkaTest.txt
  git checkout src/
  mail -s 'SparkStormKafkaTest Build broken' -a git_diff_SparkStormKafkaTest.txt abc@abc.com </dev/null
  exit 1
fi


dataDir=src/main/resources

CSV_LINE_CNT=$(wc -l $dataDir/abc/*.csv | sort -n | tail -1 | awk '{print $1}')
if [ $CSV_LINE_CNT -lt 250000 ]
then
  echo "data with fewer csv line count"
  git checkout src/
  mail -s 'Data pull failed with fewer csv line count' abc@abc.com </dev/null
  exit 1
fi

JAR=$(ls -t /home/abc/uber-SparkStormKafkaTest-*-SNAPSHOT.jar | head -1)
cp $JAR SparkStormKafkaTest-LATEST-uber.jar
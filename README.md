# java-springboot-kafka

this is an exploration of kafka using springboot and docker.

## requirements
* Java 18
* Maven

## usage

* to build project:
  ```
  $ mvn clean package
  ```
* to run project:
  ```
  $ mvn spring-boot:run
  ```

references:
* https://docs.spring.io/spring-boot/tutorial/first-application/index.html
* https://www.geeksforgeeks.org/blogs/how-to-learn-apache-kafka/
* https://www.baeldung.com/spring-kafka
* https://developer.confluent.io/get-started/java/
* https://docs.docker.com/engine/install/ubuntu/
* https://www.baeldung.com/dockerizing-spring-boot-application
* [temurin dockerization](https://adoptium.net/installation/containers)

### example docker commands
```
sudo docker run --name kcon -dit ubuntu:22.04 # `-dit` is detached interactive terminal - the container runs in the background awaiting input
sudo docker cp LICENSE kcon:/kfol # copy local file to container
sudo docker ps # same as sudo docker container ls. add `-a` to see stopped containers
```


#### notes for https://kafka.apache.org/quickstart/
sudo docker pull apache/kafka:4.3.1
sudo docker run -dit --name kafka1 -p 9092:9092 apache/kafka:4.3.1

sudo docker exec -i kafka1 sh -c "/opt/kafka/bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092"
sudo docker exec -i kafka1 sh -c "/opt/kafka/bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092" # add events
sudo docker exec -i kafka1 sh -c "/opt/kafka/bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092" # read events

sudo docker exec -i kafka1 sh -c 'cat /opt/kafka/config/connect-standalone.properties'
sudo docker exec -i kafka1 sh -c 'echo "plugin.path=/opt/kafka/libs/connect-file-4.3.1.jar" >> /opt/kafka/config/connect-standalone.properties'
sudo docker exec -i kafka1 sh -c 'cat /opt/kafka/config/connect-standalone.properties'

sudo docker exec -i kafka1 sh -c "mkdir /home/appuser/k"
sudo docker exec -i kafka1 sh -c "touch /home/appuser/k/test.txt"
sudo docker exec -i kafka1 sh -c 'echo -e "foo
bar" > /home/appuser/k/test.txt'
sudo docker exec -i kafka1 sh -c "cat /home/appuser/k/test.txt"

# make filepath absolute in connect-file-source.properties
sudo docker exec -i kafka1 sh -c "cat /opt/kafka/config/connect-file-source.properties"
sudo docker cp kafka1:/opt/kafka/config/connect-file-source.properties .
sed -i 's/file=test.txt/file=\/home\/appuser\/k\/test.txt/g' connect-file-source.properties
sudo docker cp connect-file-source.properties kafka1:/opt/kafka/config/connect-file-source.properties
sudo docker exec -i kafka1 sh -c "cat /opt/kafka/config/connect-file-source.properties"
rm connect-file-source.properties

# likewise, make filepath absolute in connect-file-sink.properties
sudo docker exec -i kafka1 sh -c "cat /opt/kafka/config/connect-file-sink.properties"
sudo docker cp kafka1:/opt/kafka/config/connect-file-sink.properties .
sed -i 's/file=test.sink.txt/file=\/home\/appuser\/k\/test.sink.txt/g' connect-file-sink.properties
sudo docker cp connect-file-sink.properties kafka1:/opt/kafka/config/connect-file-sink.properties
sudo docker exec -i kafka1 sh -c "cat /opt/kafka/config/connect-file-sink.properties"
rm connect-file-sink.properties


sudo docker exec -i kafka1 sh -c "/opt/kafka/bin/connect-standalone.sh /opt/kafka/config/connect-standalone.properties /opt/kafka/config/connect-file-source.properties /opt/kafka/config/connect-file-sink.properties"
sudo docker exec -i kafka1 sh -c "cat /home/appuser/k/test.sink.txt"
sudo docker exec -i kafka1 sh -c "/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic connect-test --from-beginning"

sudo docker exec -i kafka1 sh -c 'echo -e "another line" > /home/appuser/k/test.txt'
sudo docker exec -i kafka1 sh -c "cat /home/appuser/k/test.sink.txt"

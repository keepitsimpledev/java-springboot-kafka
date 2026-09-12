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

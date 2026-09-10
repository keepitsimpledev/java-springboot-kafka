# java-springboot-kafka

this is an exploration of kafka using springboot.

## requirements
* Java 18
* Maven

## usage

* to build project:
  ```
  $ mvn package
  ```

references:
* https://docs.spring.io/spring-boot/tutorial/first-application/index.html
* https://www.geeksforgeeks.org/blogs/how-to-learn-apache-kafka/
* https://www.baeldung.com/spring-kafka
* https://developer.confluent.io/get-started/java/
* https://docs.docker.com/engine/install/ubuntu/

docker commands
```
sudo docker run --name kcon -dit ubuntu:22.04 # `-dit` is detached interactive terminal - the container keeps running awaiting input
sudo docker ps # same as sudo docker container ls. add `-a` to see stopped containers

sudo docker exec -it kcon sh -c "mkdir kfol" # executes `mkdir kfol` on the container named kcon

sudo docker attach kcon # then quit without exiting with CTRL+p, CTRL+q
sudo docker start kcon
sudo docker stop kcon
```
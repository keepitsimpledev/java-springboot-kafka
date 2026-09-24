#!/bin/sh

# consider https://stackoverflow.com/questions/57515333/how-to-do-a-health-check-of-a-spring-boot-application-running-in-a-docker-contai

CURL_DKCON=$(curl --silent --fail localhost:8887) || true # `|| true` will prevent failure, allowing retry
CURL_DKCON_LENGTH=${#CURL_DKCON}
if [ $CURL_DKCON_LENGTH -gt 0 ]; then
  CURL_DKCON_SUCCESS=true
  echo "container dkcon successfully initalized"
  curl localhost:8887
else
  CURL_DKCON_SUCCESS=false
  echo "container dkcon not yet initalized"
fi
if [ $CURL_DKCON_SUCCESS = false ]; then
  echo "waiting up to 60 seconds for container dkcon to initalize"
  for i in {1..20}; do
    echo "wait 3 seconds"
    sleep 3
    echo "waited a total of $((i*3)) seconds - checking"
    CURL_DKCON=$(curl --silent --fail localhost:8887) || true # `|| true` will prevent failure,
    CURL_DKCON_LENGTH=${#CURL_DKCON}
    if [ $CURL_DKCON_LENGTH -gt 0 ]; then
      CURL_DKCON_SUCCESS=true
      echo "container dkcon successfully initalized"
      curl localhost:8887
      break
    elif [ $i -eq 20 ]; then
      echo "container dkcon failed to initalize"
      curl localhost:8887
    fi
  done
fi

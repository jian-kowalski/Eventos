#!/bin/sh

export URL="http://127.0.0.1:8080/events"

while :; do
  ./execute.sh
  sleep 60
done;
#!/bin/sh

export URL="localhost:8080/events"

while :; do
  ./execute.sh
  sleep 60
done;
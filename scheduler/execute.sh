#!/bin/sh

echo "Chamando atualização do status dos eventos "
curl -X PUT $URL/events;

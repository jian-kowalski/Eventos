FROM node:16.20.2-alpine3.18

RUN apk add --no-cache bash

RUN npm install
RUN npm install -g @angular/cli

USER node

WORKDIR /home/node/app

ENTRYPOINT [ "npm serve" ]

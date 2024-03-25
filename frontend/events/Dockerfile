FROM node:18

WORKDIR /app/portal/static

RUN npm install --global http-server

COPY /dist/events /app/portal/static

ENV PORT_SERV="4200" \
    DNS="0.0.0.0"

EXPOSE $PORT_SERV

CMD http-server -p ${PORT_SERV} -a ${DNS}

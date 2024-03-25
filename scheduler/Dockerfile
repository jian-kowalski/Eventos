FROM alpine:latest

RUN apk --update add curl

ADD execute.sh execute.sh
ADD local.execute.sh local.execute.sh

RUN chmod +x /local.execute.sh /execute.sh

CMD ["./local.execute.sh"]
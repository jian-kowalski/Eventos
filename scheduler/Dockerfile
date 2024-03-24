FROM alpine:latest

RUN apk --update add curl jq

ENV URL=127.0.0.1:8080
# Copiar o script para o contêiner
COPY execute.sh /execute.sh
# Tornar o script executável
RUN chmod +x /execute.sh


# Comando a ser executado quando o contêiner for iniciado
CMD ["crond -f" ,"0   *   *   *  /execute.sh"]

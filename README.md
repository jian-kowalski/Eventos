# events

O Problema: 
um sistema CRUD onde os usuários podem cadastrar eventos com uma data de vigência (data inicial e data final). A partir dessa data, os eventos se tornam ativos automaticamente e ao final desta data são inativados. Cada evento pertence a uma instituição que pode ter um ou mais eventos.

## Execução 

Para executar e testar o projeto, pode ser feito manualmente ou pode ser executado o docker-compose.yaml que irá subir todos os serviços necessarios.

## A Solução

O Database: 

Utilizado postgres na ultima versão.

O Backend: 

Para acessar a api via postman: Events.postman_collection.json

Desenvolvido em java, utilizando clean arch como modelo arquitetural e DDD no dominio de negocios, o framework utilizado foi o Quarkus.

Recebimento de Dados: Na arquitetura utilizada poderia ter N formas de criação de um evento, eu utilizei um endpoit rest para receber os dados. 

Validação dos Dados: Após o recebimento eu executo o caso de uso responsavel pela criação de um evento, e aqui são aplicadas as regras de dominio. Não permitindo datas nulas ou invalidas, limites de tamanho do nome do evento e o identificador da instituição.

Ativação dos Eventos: Existem muitas formas para resolver esse problema, eu implementei da seguinte maneira: 
 1 - Sempre que um evento for criado a ativação ou inativação é realizada, isso evita a necessidade de processar esse evento ao longo do dia.

 2 - Crei um caso de uso de atualização de eventos, que pode ser chamado através de um cronjob, scheduler, fila de mensageria, eu optei por fazer via chamada rest. Esse caso de uso processa de 10 em 10 registros, onde ele faz uma busca paginada no banco, com o filtro da data de hoje considerando a data de inicio e a data de termino + 1, isso perminte que essa rotina possa ser executada somente 1 vez ao dia de peferencia no inicio da madrugada. Nesse caso, ela vai ativar todos os eventos do dia e terminar todos os eventos do dia anterior.

No projeto, crei a cron que faz um chamada de atualização dos eventos a cada 5 minutos e fica diponivel na imagem jiankowalski/events-scheduler:latest.

BUILD:

./mvnw clean package -DskipTests -Dquarkus.container-image.build=true

docker build -t jiankowalski/events-backend:latest .

O Frontend:

Desenvolvido utilizando typescript com o framework Angular e esquema de componentes Angular Material.

BUILD
ng build

docker build -t jiankowalski/events-frontend:latest . 

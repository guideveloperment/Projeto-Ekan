** Esse Projeto é uma aplicação Java com framework spring boot que necessita ser rodado com o Java 11.

    ### BUILD ###
** Para buildar o projeto, você deve ir ao meu repositório e dar o comando git clone no meu projeto
git@github.com:guideveloperment/NOMEDOPROJETOEMQUESTAO
após clonar, abrir o projeto com a IDEA de sua preferência, executar o comando mvc clean install, logo verificar se as todas as dependências foram baixadas do projeto. 

    ### DATABASE ###
** Dentro do Projeto foi utilizado a ferramenta de gestão de migrações de banco de dados o Flyway.
O Fliway criou as tabelas através dos scripts do V1,V2 e V3.
Scripts abaixo:
select * from demo.beneficiarios b ;
select * from demo.documentos d ;
select * from demo.beneficiarios_documentos ;

delete from flyway_schema_history where success  = 0;
drop database demo;
create database demo;

    ### RUNNING ###
** após isso ir na classe principal do projeto e executar com RUN ou o botao de start.
O projeto será inicializado e você pode acessar o swagger e executar os endpoints com suas respectivas requisições. 

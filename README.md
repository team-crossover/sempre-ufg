# SempreUFG

Projeto "SempreUFG" da disciplina Arquitetura de Software do curso Engenharia de Software (UFG 2018/1).
Alunos: Natália Lopes, Sofia Moraes e Nelson William.

## Ambiente

Esta seção descreve as configurações que foram feitas para desenvolver e implantar o sistema em uma máquina virtual.

### Sistema operacional

O protótipo foi desenvolvido e executado no sistema operacional **openSUSE 12** instalado numa máquina virtual no **VirtualBox**.

### Banco de dados

Foi instalado o **PostgreSQL 9.2.7**. O banco de dados do SempreUFG foi criado com o usuário 'sempreufg' cuja senha também foi configurada para 'sempreufg'. Foi utilizado o **phpPgAdmin** para verificar e manipular diretamente o conteúdo do banco de dados. Este banco de dados e suas configurações não estão inclusos neste repositório. Este repositório contém um [backup da pasta .pgsql do PostgreSQL](../.pgsql/).

Como o sistema não inclui a funcionalidade de inserir usuários, foram inseridos no banco de daos dois usuários padrão: um gestor com email "gestor@gestor.com" e senha "gestor"; e um aluno com email "aluno@aluno.com" e senha "aluno".

### Servidor de aplicação

Na máquina virtual foi instalado o servidor de aplicação **GlassFish OpenSource Edition 4.1** no diretório "/home/nelson/glassfish4". Foi criado um domain para o SempreUFG no diretório "/home/nelson/glassfish4/glassfish/domains/SempreUFG". Este repositório contém um [backup da pasta do domain](../.glassfish4/glassfish/domains/SempreUFG/) .

A conexão entre o servidor e o banco de dados foi configurada através do painel de administração do GlassFish (cujo login é "admin" e a senha é "admin"). Na configuração, foi criado o JDBC Resource "sempreufgResource" e o JDBC Connection Pool "sempreufgPool".

Para prover a autenticação de usuários e controle de acesso, foi configurado um Security Realm chamado "jdbc-realm" que utiliza o sempreufgResource para acessar os logins (coluna "email" da tabela "usuario"), as senhas (coluna "senha" da tabela "usuario") e a tabela "papel" que contém o papel de cada usuário. Espera-se que as senhas sejam armazenadas apenas como suas hashs SHA-256 em Base64 e encoding UTF-8.

### Desenvolvimento

O deploy foi feito com o auxílio da **Eclipse Java EE IDE for Web Developers 4.7.3a**, que foi facilmente integrada ao servidor de aplicação e permitiu iniciar a execução do servidor. O sistema foi testado no browser **Mozilla Firefox v40**, acessado através do endereço 'localhost:8080/SempreUFG/login.xhtml".

Todo o código do projeto Java está presente neste diretório.



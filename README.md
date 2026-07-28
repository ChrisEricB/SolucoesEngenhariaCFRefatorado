# Soluções Engenharia C&F — Projeto Refatorado

## Status do projeto

Projeto acadêmico desenvolvido e testado.

## Objetivo

O sistema Soluções Engenharia C&F foi desenvolvido para apoiar a gestão de projetos, auditorias e não conformidades em uma empresa de engenharia.

Esta versão corresponde à refatoração do sistema desktop criado anteriormente em Java Swing. O objetivo principal foi separar as regras de negócio da interface gráfica, permitindo o reaproveitamento das classes em uma futura aplicação web.

## Tecnologias utilizadas

* Java SE 17
* JDBC
* MySQL
* Apache NetBeans IDE
* Git
* GitHub

## Funcionalidades

* Cadastro e consulta de usuários
* Autenticação de usuários
* Cadastro e acompanhamento de projetos
* Cadastro e acompanhamento de auditorias
* Registro e acompanhamento de não conformidades
* Integração com banco de dados MySQL
* Validação das regras de negócio

## Organização do projeto

O projeto foi separado nos seguintes pacotes:

* `app`: classe principal e testes do sistema
* `config`: configuração da conexão com o banco de dados
* `model`: classes que representam as entidades do sistema
* `repository`: interfaces de acesso aos dados
* `repository.jdbc`: implementações JDBC dos repositórios
* `service`: regras de negócio e validações

## Princípios aplicados

Foram aplicados princípios SOLID, principalmente:

* Princípio da Responsabilidade Única
* Princípio da Inversão de Dependência
* Princípio Aberto/Fechado
* Princípio da Segregação de Interfaces

## Banco de dados

O arquivo para criação e importação do banco está disponível em:

`database/solucoes_engenharia_cf_dump.sql`

O banco utilizado pelo sistema possui o nome:

`solucoes_engenharia_cf`

## Configuração da conexão

A aplicação utiliza as seguintes variáveis de ambiente:

* `DB_URL`
* `DB_USUARIO`
* `DB_SENHA`

Quando `DB_URL` e `DB_USUARIO` não são informadas, o sistema utiliza os valores locais definidos na classe `ConnectionFactory`.

A senha do banco não é armazenada no código-fonte.

## Testes

Os testes foram implementados no método `main()` e verificam:

* conexão com o MySQL;
* listagem de usuários;
* autenticação;
* listagem de projetos;
* listagem de auditorias;
* listagem de não conformidades;
* funcionamento das validações das regras de negócio.

## Desenvolvedor

Christian Eric Barrantes Briceño

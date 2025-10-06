# HospiData API

Bem-vindo à documentação da HospiData API, um sistema de gerenciamento hospitalar, desenvolvido com uma arquitetura de microsserviços.

## Visão Geral

O HospiData é uma solução completa para a gestão de informações em um ambiente hospitalar. Ele permite o gerenciamento de usuários (médicos, enfermeiros, pacientes), agendamento de consultas, manutenção de históricos médicos e um sistema de notificações para manter os pacientes informados.

O projeto foi construído com serviços independentes que se comunicam de forma assíncrona, garantindo alta disponibilidade e resiliência.

## Tecnologias Utilizadas

Este projeto utiliza um conjunto de tecnologias para garantir performance e escalabilidade:

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Arquitetura:** Microsserviços
* **Banco de Dados:** MariaDB
* **Mensageria:** Apache Kafka
* **Gateway de API:** Spring Cloud Gateway com OpenFeign
* **Segurança:** Spring Security com JWT (Tokens via Cookies)
* **Documentação da API:** Swagger (OpenAPI)
* **Containerização:** Docker e Docker Compose

## Arquitetura

A API é dividida em quatro microsserviços principais, um API Gateway que centraliza o acesso, um banco de dados e um broker de mensageria.

* **Gateway Service (`gateway-service`):** É a porta de entrada para todas as requisições. Ele é responsável por:
    * Autenticação e autorização de usuários.
    * Roteamento de requisições para os microsserviços correspondentes.
    * Servir a API GraphQL, que agrega dados de outros serviços.
    * Atuar como um ponto único de acesso, simplificando a interação do cliente com a API.

* **Appointment Service (`appointment-service`):** Gerencia todo o ciclo de vida dos agendamentos de consultas. Suas responsabilidades incluem:
    * Criação, leitura, atualização e cancelamento de consultas.
    * Publicação de eventos em um tópico do Kafka sempre que um agendamento é criado ou modificado.

* **History Service (`history-service`):** Responsável por armazenar e gerenciar os históricos médicos dos pacientes.
    * Cria novos registros de histórico associados a uma consulta.
    * Permite a busca e a visualização de históricos.

* **Notification Service (`notification-service`):** Opera de forma assíncrona, consumindo eventos do Kafka para notificar os usuários.
    * Escuta o tópico de eventos de agendamento.
    * Envia notificações por e-mail (simuladas no console) para os pacientes quando uma consulta é agendada, alterada ou cancelada.

## Como executar

Para executar o projeto em seu ambiente local, você precisará ter o **Docker** e o **Docker Compose** instalados.

### 1. Configuração do Ambiente

O projeto utiliza arquivos `.env` para gerenciar as variáveis de ambiente. Você precisará de um arquivo `.env` na raiz do projeto e um para cada serviço (`.env-gateway`, `.env-appointment`, etc.), preenchidos com as configurações de banco de dados, Kafka e JWT.

### 2. Executando a Aplicação

Com o Docker em execução, abra um terminal na raiz do projeto e execute o seguinte comando:

```bash
docker-compose up --build

```
Divirta-se!

HospiData
HospiData é um projeto backend de um sistema de gerenciamento hospitalar, desenvolvido para a Pós Tech em Java da FIAP. A solução utiliza uma arquitetura de microsserviços com Spring Boot, MariaDB, Kafka e Docker.

Requisitos
Antes de executar, certifique-se de ter instalado:

Docker

Docker Compose

Executando a aplicação
Após clonar o repositório, não é necessário configurar nenhum arquivo. Basta executar o comando na raiz do projeto:

Bash

docker-compose up --build
A aplicação estará disponível em: http://localhost:8080

A documentação interativa do Swagger pode ser acessada em: http://localhost:8080/swagger-ui/index.html

Estrutura do Projeto
Dockerfile.*: Define as imagens para cada microsserviço da aplicação e para o banco de dados.

docker-compose.yml: Orquestra a inicialização de todos os contêineres (API, Banco de Dados e Kafka).

src/: Código-fonte de cada microsserviço, organizado em seus respectivos módulos.

Endpoints da API (via Gateway)
Método	Endpoint	Descrição
POST	/auth/login	Autentica um usuário e retorna tokens em cookies.
POST	/users	Cria um novo usuário.
GET	/users	Lista todos os usuários (requer perfil ADMIN).
PUT	/users/{id}	Atualiza os dados de um usuário.
DELETE	/users/{id}	Desativa um usuário (soft delete).
POST	/appointments	Cria um novo agendamento de consulta.
GET	/appointments	Lista todos os agendamentos.
PUT	/appointments/{id}	Atualiza um agendamento.
GET	/history	Lista o histórico médico completo.
GET	/history/search	Busca no histórico por appointmentId, patientId, etc.

Export to Sheets
Repositório
🔗 https://github.com/Hospidata-techchallange/HospiData

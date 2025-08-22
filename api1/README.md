# API1 - Magnum Bank FIPE

API responsável por:
- Disparar a **carga inicial** dos dados da FIPE (marcas de veículos).
- Publicar as marcas no **Kafka** para que a API2 consuma.
- Disponibilizar endpoints REST para consulta/alteração de marcas e veículos.
- Autenticação via JWT (endpoint `/login`).
- Camada de cache com **Redis** para otimizar buscas.

---

## 🚀 Tecnologias
- Java 21 + Spring Boot
- Spring Data JPA (MySQL)
- Spring Kafka
- Spring Security (JWT)
- Redis
- Docker & Docker Compose

---

## 📦 Como rodar
O ambiente completo (API1, API2, MySQL, Redis, Kafka, Kafka-UI) é iniciado com um único comando no diretório raiz do projeto:

```bash
docker compose up -d --build
```

- **API1** → [http://localhost:8080](http://localhost:8080)
- **API2** → [http://localhost:8081](http://localhost:8081)
- **Kafka UI** → [http://localhost:8085](http://localhost:8085)
- **MySQL** → porta `3306` (usuário `fipe_user`, senha `fipe_pass`)
- **Redis** → porta `6379`

---

## 🧪 Postman Collection
A collection de requisições está em:  
`api1/docs/MAGNUM BANK.postman_collection.json`

---

## 🔑 Autenticação
1. Use o endpoint **`POST /login`**.
    - **Usuário:** `admin@magnum.com`
    - **Senha:** `admin123`
   > Esse usuário é gerado automaticamente na tabela `user` ao rodar o projeto pela primeira vez.

2. O retorno é um **JWT Bearer Token**.
3. Use este token no header `Authorization` das demais requisições:
   ```
   Authorization: Bearer <token>
   ```
4. O payload já está configurado na collection do Postman.

---

## 📖 Principais Endpoints
- **POST /login** → Gera token JWT.
- **POST /fipe/carga-inicial** → Dispara carga inicial de marcas da FIPE e envia para Kafka.
- **GET /fipe/marcas** → Lista marcas do banco (cacheado em Redis).
- **GET /fipe/marcas/{id}/veiculos** → Busca veículos por marca.
- **PUT /fipe/veiculos/{id}** → Atualiza modelo/observações do veículo.  

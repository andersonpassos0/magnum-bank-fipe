# API2 - Magnum Bank FIPE

API responsável por:
- Consumir mensagens do **Kafka** com as marcas enviadas pela API1.
- Consultar a FIPE para buscar **códigos e modelos** dos veículos.
- Persistir informações no banco **MySQL** (`código`, `marca`, `modelo`).

---

## 🚀 Tecnologias
- Java 21 + Spring Boot
- Spring Data JPA (MySQL)
- Spring Kafka
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
- **MySQL** → porta `3306`
- **Redis** → porta `6379`

---

## 📌 Observações
- O consumo Kafka usa `group-id=api2-marcas`.
- Tópico: `fipe.marcas` (criado automaticamente pelo API1).
- Dados persistidos no banco `fipe_db`.

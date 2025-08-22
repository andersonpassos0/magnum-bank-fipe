# Magnum Bank - Desafio Técnico (Tabela FIPE)

Repositório contendo implementação do desafio **Magnum Bank - Desenvolvedor Back-end Java**.

---

## 📋 Descrição do Desafio
Objetivo: integrar com a [API FIPE](https://deividfortuna.github.io/fipe/), processar dados de forma assíncrona via Kafka, persistir em banco SQL, adicionar cache com Redis e autenticação JWT.

Etapas principais:
1. API1 → carga inicial das marcas + publicação em Kafka.  
2. API2 → consumo das marcas, consulta dos modelos/códigos na FIPE e persistência no banco.  
3. API1 → endpoints REST para consultar e atualizar marcas/veículos (com cache em Redis e segurança JWT).  
4. Infraestrutura completa orquestrada via Docker Compose.  

---

## 📦 Como rodar o projeto
Clone o repositório:
```bash
git clone https://github.com/andersonpassos0/magnum-bank-fipe.git
cd magnum-bank-fipe
```

Suba o ambiente completo (APIs + infra):
```bash
docker compose up -d --build
```

Isso inicia automaticamente:
- API1 (porta 8080)
- API2 (porta 8081)
- MySQL (porta 3306, db `fipe_db`, user `fipe_user`, pass `fipe_pass`)
- Redis (porta 6379)
- Kafka (porta 9092)
- Kafka UI (porta 8085)

👉 Não é necessário subir cada API separadamente — o `docker-compose.yml` já provisiona tudo.

---

## 📂 Estrutura do Monorepo
```
magnum-bank-fipe/
├── api1/   # microsserviço API1
│   ├── src/
│   ├── docs/MAGNUM BANK.postman_collection.json
│   └── README.md
├── api2/   # microsserviço API2
│   ├── src/
│   └── README.md
├── docker-compose.yml
└── README.md   # este arquivo
```

> ℹ️ Em ambiente profissional, o ideal seria ter **um repositório por microsserviço** (API1, API2, auth service, etc).  
> Para fins de desafio técnico, foi utilizado **monorepo** para facilitar a execução e entrega.

---

## 🧪 Testando com Postman
1. Importar a collection localizada em `api1/docs/MAGNUM BANK.postman_collection.json`.  
2. Primeiro, chamar o endpoint **Login**:
   - Usuário: `admin@magnum.com`  
   - Senha: `admin123`  
   - Gera um **JWT** automaticamente.  
3. Nas demais requisições, usar o token no header:
   ```
   Authorization: Bearer <token>
   ```

---

## 🔗 Links Úteis
- **Repositório**: [github.com/andersonpassos0/magnum-bank-fipe](https://github.com/andersonpassos0/magnum-bank-fipe)  
- **FIPE API**: [https://deividfortuna.github.io/fipe/](https://deividfortuna.github.io/fipe/)  
- **Kafka UI**: [http://localhost:8085](http://localhost:8085)  
- **API1 (local)**: [http://localhost:8080](http://localhost:8080)  
- **API2 (local)**: [http://localhost:8081](http://localhost:8081)  

---

## ✅ Entregáveis
- Código fonte (API1 + API2).
- Arquivo `docker-compose.yml` para levantar todo o ambiente.
- Collection Postman com payloads prontos.
- Documentação breve dos endpoints principais.

# 🚀 Xpto-Stream – Motor de Recomendação Real-Time

[![Java 21](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.3-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Apache_Kafka-Distributed-black?logo=apachekafka)](https://kafka.apache.org/)
[![DynamoDB](https://img.shields.io/badge/Amazon_DynamoDB-NoSQL-blue?logo=amazondynamodb)](https://aws.amazon.com/dynamodb/)

[Português](#português) | [English](#english)

---

## <a name="português"></a> 🇧🇷 Português

### 📝 Descrição
O **Xpto-Stream** é um motor de processamento de eventos de alta performance projetado para capturar o comportamento do usuário em tempo real e transformar interações brutas em perfis de interesse dinâmicos. O sistema provê uma base de dados ultra-rápida (DynamoDB) para que motores de busca ou vitrines personalizem a experiência do usuário instantaneamente.

### 🏗️ Arquitetura e Tecnologias
O projeto foi construído sob os princípios da **Clean Architecture**, garantindo que o núcleo de negócio seja independente de frameworks e ferramentas de infraestrutura.

* **Java 21 & Virtual Threads (Project Loom):** Processamento massivo de eventos com threads leves, permitindo escalabilidade vertical extrema.
* **Spring Cloud Stream & Kafka:** Ingestão reativa e assíncrona.
* **DynamoDB Local:** Persistência NoSQL utilizando **Atomic Increments** para garantir que múltiplos eventos simultâneos não causem perda de dados.
* **Testcontainers:** Ambiente de testes de integração idêntico ao de produção, subindo instâncias reais via Docker durante o build.

### 📊 Lógica de Scoring
| Evento | Descrição | Pontuação |
| :--- | :--- | :--- |
| `VIEW` | Visualização de produto | +1 ponto |
| `CLICK` | Clique em detalhe | +3 pontos |
| `ADD_TO_CART` | Adição ao carrinho | +7 pontos |
| `PURCHASE` | Finalização de compra | +15 pontos |

---

## <a name="english"></a> 🇺🇸 English

### 📝 Description
**Xpto-Stream** is a high-performance event processing engine designed to capture user behavior in real-time and transform raw interactions into dynamic interest profiles. The system provides an ultra-fast database (DynamoDB) enabling search engines or storefronts to personalize the user experience instantly.

### 🏗️ Architecture & Technologies
The project is built on **Clean Architecture** principles, ensuring the business core remains independent of external frameworks and infrastructure tools.

* **Java 21 & Virtual Threads (Project Loom):** Massive event processing using lightweight threads for extreme vertical scalability.
* **Spring Cloud Stream & Kafka:** Reactive and asynchronous ingestion. The API returns `202 Accepted` immediately, ensuring minimal latency.
* **DynamoDB Local:** NoSQL persistence leveraging **Atomic Increments** to ensure data consistency under high concurrency.
* **Testcontainers:** Integration testing environment identical to production, spinning up real instances via Docker during build.


### 📊 Scoring Logic
| Event | Description | Score |
| :--- | :--- | :--- |
| `VIEW` | Product view | +1 point |
| `CLICK` | Click on detail | +3 points |
| `ADD_TO_CART` | Add to cart | +7 points |
| `PURCHASE` | Purchase completion | +15 points |

---

## 🛠️ Como Executar / How to Run

### Requisitos / Prerequisites
* Docker & Docker Compose
* Java 21 (OpenJDK)
* Maven 3.9+

### Passos / Steps

1.  **Subir a Infraestrutura (Kafka & DynamoDB):**
    ```bash
    docker-compose up -d
    ```

2.  **Criar a Tabela no DynamoDB Local:**
    (Caso não tenha o AWS CLI instalado localmente, use o container):
    ```powershell
    docker run --rm -e AWS_ACCESS_KEY_ID=fake -e AWS_SECRET_ACCESS_KEY=fake amazon/aws-cli `
    dynamodb create-table --table-name user_interest `
    --attribute-definitions AttributeName=userId,AttributeType=S AttributeName=category,AttributeType=S `
    --key-schema AttributeName=userId,KeyType=HASH AttributeName=category,KeyType=RANGE `
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 `
    --endpoint-url [http://host.docker.internal:8000](http://host.docker.internal:8000) --region us-east-1
    ```

3.  **Executar a Aplicação:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## 🧪 Testes / Testing
O projeto utiliza **TDD** e **Testcontainers**. Para validar a lógica de scoring e integração:
    ```bash
    mvn test
    ```
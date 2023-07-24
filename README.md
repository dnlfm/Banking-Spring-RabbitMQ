# Banking App

This project is a simplified banking application created using the following technologies:
- Java 11
- SpringBoot
- MyBatis
- Gradle
- Postgres
- RabbitMQ **(TODO)**
- JUnit **(TODO)**
- Docker

It implements an API to:
- Create an account (multiple currency)
- Get an account given an account ID
- Create a transaction
- Get list of transactions given an account ID

[-> Jump to Running the application](#running-the-application)

## Structure and Decisions

### About the Database

The database script can be located at [src/main/resources/schema.sql](./src/main/resources/schema.sql). It defines the database structure in a very simple way, for example having only __name__ and __email__ of customers. Also, an account ID is its serial number, in this case, an auto-incremented integer field - this doesn't corresponds to reality, but is just a way to simplify things. 

Business logics are implemented in the backend service, but some of them were also added to the SQL script to ensure that all operations done indirectly or directly to the database are valid. For example, it is not possible to insert a transaction of type "OUT" with a value greater than the available amount for an account balance, or to insert a transaction with empty description.

In this initial version, the database script is loaded every time the main application runs (see [DatabaseInitializer.java](./src/main/java/com/dnlfm/config/DatabaseInitializer.java)) - it doesn't generate conflicts since tables and types are created only once. This is an improvement point that could be achived better probably by using MyBatis migrations or something similar such as Flyway or Liquibase.

### About MyBatis + SpringBoot

MyBatis offers many ways of dealing with SpringBoot, as shown in [mybatis-spring-boot-samples](https://github.com/mybatis/spring-boot-starter/tree/dc92633c39a9cb546f313edfa4c65f89d5d8b323/mybatis-spring-boot-samples). The approach followed in this project is the same as in [mybatis-spring-boot-sample-web](https://github.com/mybatis/spring-boot-starter/tree/dc92633c39a9cb546f313edfa4c65f89d5d8b323/mybatis-spring-boot-samples/mybatis-spring-boot-sample-web). If comparing with [mybatis-spring-boot-sample-xml](https://github.com/mybatis/spring-boot-starter/tree/dc92633c39a9cb546f313edfa4c65f89d5d8b323/mybatis-spring-boot-samples/mybatis-spring-boot-sample-xml), the adopted structure is better since SQL and code stays together, and defining the mappers are more intuitive and easier to track related issues.

### About Exception Handling

A nice and organized way to give proper results is to define [custom exceptions](./src/main/java/com/dnlfm/exception). The one responsible for customizing responses in SpringBoot context is [GlobalExceptionHandler.java](./src/main/java/com/dnlfm/exception/GlobalExceptionHandler.java), that in this case won't return the Stacktrace of an exception, but the message value itself. Every custom exception has a custom message defined on it, and makes the development cleanear this way. Enumeration types such as [CurrencyType](src/main/java/com/dnlfm/domain/CurrencyType.java) and [TransactionDirection](src/main/java/com/dnlfm/domain/TransactionDirection.java) can throw their own related exceptions during JSON deserialization (e.g. from a request body), making data validation very straightforward.

## Running the application

Since it uses [Docker](https://docs.docker.com/) and [docker-compose](https://docs.docker.com/engine/reference/commandline/compose_up/), to run it is simple as:

> docker-compose up

The main application will run at port 8080. You can import [Banking.postman_collection.json](./docs/Banking.postman_collection.json) to your [Postman](https://www.postman.com/) (or similar software) to call the available endpoints. The commands below are also valid:

- **[POST]** /customer
    Creating a customer:
    ```
    curl --location 'http://localhost:8080/customer' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "name": "Daniel Freitas Martins",
        "email": "mdanielfm97@gmail.com"
    }'
    ```

    Response:
    ```
    {
        "id": 1,
        "name": "Daniel Freitas Martins",
        "email": "mdanielfm97@gmail.com"
    }
    ```

- **[POST]** /account
    Creating an account:
    ```
    curl --location 'http://localhost:8080/account' \
    --header 'Content-Type: application/json' \
    --data '{
        "customerId": "1",
        "country": "Sweden",
        "currencyTypes": ["EUR", "SEK"]
    }'
    ```

    Response:
    ```
    {
        "id": 1,
        "customer_id": 1,
        "accountBalanceDTOs": [
            {
                "currencyType": "SEK",
                "availableAmount": 0
            },
            {
                "currencyType": "EUR",
                "availableAmount": 0
            }
        ]
    }
    ```

- **[GET]** /account
    Retrieving an account:
    ```
    curl --location --request GET 'http://localhost:8080/account/1' \
    --header 'Content-Type: application/json'
    ```

    Response:
    ```
    {
        "id": 1,
        "customer_id": 1,
        "accountBalanceDTOs": [
            {
                "currencyType": "EUR",
                "availableAmount": 0.00
            },
            {
                "currencyType": "SEK",
                "availableAmount": 0.00
            }
        ]
    }
    ```

- **[POST]** /transaction
    Creating a transaction:
    ```
    curl --location 'http://localhost:8080/transaction' \
    --header 'Content-Type: application/json' \
    --data '{
        "accountId": 1,
        "amount": 500,
        "currencyType": "EUR",
        "transactionDirection": "IN",
        "description": "Adding 500 euros"
    }'
    ```

    Response:
    ```
    {
        "accountId": 1,
        "transactionId": 1,
        "amount": 500,
        "currencyType": "EUR",
        "transactionDirection": "IN",
        "description": "Adding 500 euros",
        "balanceAfterTransaction": 500.00
    }
    ```

- **[GET]** /transaction
    Retrieving a transaction:
    ```
    curl --location 'http://localhost:8080/transaction/1'
    ```

    Response:
    ```
    [{"accountId":1,"transactionId":1,"amount":500.00,"currencyType":"EUR","transactionDirection":"IN","description":"Adding 500 euros"}]
    ```

Copyright © 2023 Daniel Freitas Martins (dnlfm)
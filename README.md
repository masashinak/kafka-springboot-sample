# kafka-springboot-sample

This is a sample project to demonstrate how to use Kafka with Spring Boot.
It is based on the udemy course [Apache Kafka Crash Course for Java and Python Developers
](https://www.udemy.com/share/106ujs3@z4nx5xz1VvdPahRkTqPaDP2zmZM4RTYxtvwxNgRCKzbprvNIx8_xLc5Nx4T0jqbE/).

They use java and maven, I used kotlin and gradle in the project.

## How to run

1. Start zookeeper and 3 kafka servers and confluent schema registry service
```shell
docker-compose up
```
2. Start the applications(from CLI or IDEA) - people-service(producer) and people-consumer

## How to test

You hit the API of people producer to send a message to kafka, and then you can see the message in the consumer logs.
For example, you can use the following command to send 5 messages to kafka. It checks the schema
```shell
curl localhost:8080/api/people -d '{"count":5}'  -H 'Content-Type: application/json' -v

## others

Code generation for avro schema can be done in avro-domain-events module. 
```shell
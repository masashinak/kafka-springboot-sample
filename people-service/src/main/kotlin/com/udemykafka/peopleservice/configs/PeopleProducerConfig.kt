package com.udemykafka.peopleservice.configs

import com.udemykafka.peopleservice.entities.Person
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class PeopleProducerConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private var bootStrapServers: String?) {



    private fun producerConfigs(): Map<String,Any?> = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootStrapServers,
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
    )


    @Bean
    fun producerFactory():ProducerFactory<String, Person> =
        DefaultKafkaProducerFactory<String,Person>(producerConfigs())

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String,Person> =
        KafkaTemplate(producerFactory())
}
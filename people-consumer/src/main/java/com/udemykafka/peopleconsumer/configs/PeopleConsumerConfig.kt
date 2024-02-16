package com.udemykafka.peopleconsumer.configs

import com.udemykafka.peopleconsumer.entities.Person
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.bind.Bindable.mapOf
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer

@EnableKafka
@Configuration
open class PeopleConsumerConfig(
    @Value("\${spring.kafka.bootstrap-servers}") private val bootstrapServers: String,
    @Value("\${topics.people-basic.name}") private val topicName: String,
    @Value("\${topics.people-basic.consumer-group}") private val consumerGroup: String,
    ) {

    private fun consumerConfigs(): Map<String,Any?> = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        ConsumerConfig.GROUP_ID_CONFIG to consumerGroup)

    @Bean
    open fun consumerFactory(): ConsumerFactory<String, Person> =
        DefaultKafkaConsumerFactory<String,Person>(consumerConfigs(),
            StringDeserializer(),JsonDeserializer(Person::class.java,false))

    @Bean
    open fun personListenerFactory(): ConcurrentKafkaListenerContainerFactory<String,Person> = run {
        var factory = ConcurrentKafkaListenerContainerFactory<String,Person>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

}
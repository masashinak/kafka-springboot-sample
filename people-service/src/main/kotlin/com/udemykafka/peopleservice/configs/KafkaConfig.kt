package com.udemykafka.peopleservice.configs

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.config.TopicConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaConfig(
    @Value("\${spring.kafka.bootstrap-servers}") private val bootstrapServers: String,
    @Value("\${topics.people-basic.name}") private val topicName: String,
    @Value("\${topics.people-basic.partitions}") private val topicPartitions: Int,
    @Value("\${topics.people-basic.replicas}") private val topicReplicas: Int,
) {

    @Bean
    fun peopleBasicTopic() = run {
        TopicBuilder.name(topicName)
            .partitions(topicPartitions)
            .replicas(topicReplicas)
            .build()
    }
    @Bean
    fun peopleBasicShortTopic() = run {
        TopicBuilder.name("$topicName-short")
            .partitions(topicPartitions)
            .replicas(topicReplicas)
            .config(TopicConfig.RETENTION_MS_CONFIG,"360000")
            .build()
    }

}
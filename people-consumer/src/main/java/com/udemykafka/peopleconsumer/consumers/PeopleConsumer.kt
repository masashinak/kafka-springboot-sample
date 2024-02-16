package com.udemykafka.peopleconsumer.consumers

import com.udemykafka.peopleconsumer.entities.Person
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class PeopleConsumer {
    companion object {
        private  val logger = LoggerFactory.getLogger(javaClass)
    }

    @KafkaListener(topics = ["people.basic.java"], containerFactory = "personListenerFactory")
    fun handlePerson(person: Person){
        logger.info("Person recieved:$person")
    }
}
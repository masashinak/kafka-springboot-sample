package com.udemykafka.peopleservice.controllers

import com.udemykafka.peopleservice.commands.CreatePeopleCommand
import com.udemykafka.peopleservice.entities.Person
import net.datafaker.Faker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*


// Test with curl
// curl localhost:8080/api/people -d '{"count":5}'  -H 'Content-Type: application/json' -v
@RestController
@RequestMapping("/api")
class PeopleController(
    @Value("\${topics.people-basic.name}")
    private var peopleTopic :String,
    private var kafkaTemplate: KafkaTemplate<String,Person>
    ) {
    companion object {
      private  val logger = LoggerFactory.getLogger(javaClass)
    }


    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody cmd:CreatePeopleCommand):List<Person>{
        logger.info("command is $cmd")

        var faker = Faker()
        val people = mutableListOf<Person>()

        repeat(cmd.count){
            val person = Person(UUID.randomUUID().toString(),faker.name().fullName(),faker.job().title())
            people.add(person)
            val key = person.title.lowercase().replace("\\s+","-")
            var future = kafkaTemplate.send(peopleTopic,key,person)
        }

        kafkaTemplate.flush()
        return people

    }

}
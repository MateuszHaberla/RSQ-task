package com.mateuszhaberla.recruitmenttaskrsq.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {
    @Bean
    fun getObjectMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())
                                                        .registerModule(JavaTimeModule())
                                                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}
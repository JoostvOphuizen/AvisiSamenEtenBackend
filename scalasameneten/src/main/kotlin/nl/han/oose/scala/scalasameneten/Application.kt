package com.example.springboot

import org.modelmapper.ModelMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan



@SpringBootApplication
@ComponentScan(basePackages = ["com.example.springboot", "nl.han.oose.scala.scalasameneten.controller"])
class Application {

    companion object {
        @kotlin.jvm.JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    @Bean
    fun modelMapper(): ModelMapper? {
        return ModelMapper()
    }

}

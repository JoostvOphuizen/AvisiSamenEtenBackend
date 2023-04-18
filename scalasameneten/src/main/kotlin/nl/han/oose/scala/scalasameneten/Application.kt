package com.example.springboot

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import java.util.*

@SpringBootApplication
@ComponentScan(basePackages = ["com.example.springboot", "nl.han.oose.scala.scalasameneten.controller"])
class Application {

    @Bean
    fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
        return CommandLineRunner { args ->
            println("Let's inspect the beans provided by Spring Boot:")
            val beanNames: Array<String> = ctx.getBeanDefinitionNames()
            Arrays.sort(beanNames)
            for (beanName in beanNames) {
                println(beanName)
            }
        }
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}
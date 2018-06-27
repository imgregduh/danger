package com.doubleu.danger

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@ComponentScan
class DangerApplication : WebMvcConfigurer {
    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer.favorPathExtension(false)
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(DangerApplication::class.java, *args)
}
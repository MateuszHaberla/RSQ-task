package com.mateuszhaberla.recruitmenttaskrsq.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.LocalDate


@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun docket(): Docket {
        return Docket(DocumentationType.SWAGGER_12)
                .directModelSubstitute(LocalDate::class.java, String::class.java)
                .ignoredParameterTypes(LocalDate::class.java)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("\\/v.*"))
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder().title("Monkey Generator @")
                .description("It's tool to help CRBZ members work more efficiently :)")
                .contact(Contact("Mateusz Haberla, Krzysztof Lichorad", "", "MateuszHaberla@Santander.com"))
                .version("1.4.0").build()
    }
}
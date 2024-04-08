package br.com.planosaude.ekan.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    /**
     * Expoe um objeto do tipo OpenAPI. Será carregado pelo spring doc e seguirá com as configurações determinadas no return do método.
     * @return
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DOCUMENTAÇÃO API EKAN")
                        .description("API Rest da aplicação avaliacao.ekan")
                        .contact(new Contact()
                                .name("Contato do desenvolvedor")
                                .email("viana.guilherme29@gmail.com"))
                        .license(new License()
                                .name("Github")
                                .url("https://github.com/guideveloperment/")
                        )
                );
    }
}

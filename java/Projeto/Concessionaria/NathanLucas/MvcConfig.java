package Projeto.Concessionaria.NathanLucas;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Pastas internas (CSS, JS, imagens padrão)
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

        // Pasta externa para fotos de perfil
        String caminhoFotos = Paths.get("C:/NathanLucas/fotos_perfil/").toUri().toString();
        registry.addResourceHandler("/fotos_perfil/**")
                .addResourceLocations(caminhoFotos);
    }
}

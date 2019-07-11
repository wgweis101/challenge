package williamssonama.challenge.configuration;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WilliamsSonomaConfigurator {

    @Bean
    Gson gsonServiceFactory(){
        return new Gson();
    }
}

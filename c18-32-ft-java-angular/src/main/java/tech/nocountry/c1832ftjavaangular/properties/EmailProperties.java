package tech.nocountry.c1832ftjavaangular.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("mailersend.api")
public class EmailProperties {

    /**
     * Secret key used for mailersend
     */
    private String key;
}

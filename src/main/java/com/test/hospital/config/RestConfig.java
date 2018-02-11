package com.test.hospital.config;

import com.test.hospital.orm.entity.Medic;
import com.test.hospital.orm.entity.Procedure;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RestConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Medic.class);
        config.exposeIdsFor(Procedure.class);
    }
}
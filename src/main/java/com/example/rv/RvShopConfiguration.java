package com.via.rv;

import com.codahale.dropwizard.Configuration;
import com.codahale.dropwizard.db.DataSourceFactory;
import com.via.rv.core.Template;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;


public class RvShopConfiguration extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public Template buildTemplate() {
        return new Template(template, defaultName);
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        Map<String, String> properites = new HashMap<>();
        properites.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properites.put("charSet", "UTF-8");
        database.setProperties(properites);
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
}

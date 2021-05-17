package com.paulo.config;

import org.aeonbits.owner.Config;

@Config.Sources("file:src/test/resources/properties/test.properties")
public interface Configuracoes extends Config {
    @Key("baseURI")
    String baseURI();

    @Key("basePath")
    String basePath();
}

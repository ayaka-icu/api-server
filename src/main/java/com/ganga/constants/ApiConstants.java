package com.ganga.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConstants {

    @Value("${api.img.path.absolute-path}")
    public String absolutePath;

    @Value("${api.img.path.relative-path}")
    public String relativePath;

    @Value("#{T(java.lang.Boolean).parseBoolean('${api.img.path.is-relative-path}')}")
    public boolean isRelative;

}

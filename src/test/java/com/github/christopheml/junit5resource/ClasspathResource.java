package com.github.christopheml.junit5resource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ClasspathResource {

    String value();

    String charset() default "UTF-8";

}

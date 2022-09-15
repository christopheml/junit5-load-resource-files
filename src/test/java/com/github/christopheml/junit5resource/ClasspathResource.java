package com.github.christopheml.junit5resource;

public @interface ClasspathResource {

    String value();
    String charset() default "UTF-8";

}

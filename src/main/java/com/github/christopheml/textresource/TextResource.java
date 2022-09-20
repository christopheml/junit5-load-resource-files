package com.github.christopheml.textresource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TextResource {

    String value();

    String charset() default "UTF-8";

}

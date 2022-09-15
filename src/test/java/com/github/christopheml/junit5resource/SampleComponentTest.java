package com.github.christopheml.junit5resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ClasspathResourceParameterResolver.class)
class SampleComponentTest {

    @Test
    void dummy(@ClasspathResource("/sample.txt") String text) {
        Assertions.assertEquals("Hello, world!\n", text);
    }

}

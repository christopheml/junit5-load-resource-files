package com.github.christopheml.textresource.sample;

import com.github.christopheml.textresource.TextResource;
import com.github.christopheml.textresource.TextResourceParameterResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TextResourceParameterResolver.class)
class SampleTest {

    @Test
    void dummy(@TextResource("/sample.txt") String text) {
        Assertions.assertEquals("Hello, world!\n", text);
    }

}

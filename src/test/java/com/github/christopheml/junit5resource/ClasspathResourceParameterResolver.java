package com.github.christopheml.junit5resource;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

public class ClasspathResourceParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(ClasspathResource.class) && parameterContext.getParameter().getType().equals(String.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.findAnnotation(ClasspathResource.class).flatMap(classpathResource -> {
            var path = classpathResource.value();
            var charset = Charset.forName(classpathResource.charset());
            return readClasspathResourceContent(path, charset);
        }).orElse(null);
    }

    private Optional<String> readClasspathResourceContent(String path, Charset charset) {
        return Optional.ofNullable(getClass().getResourceAsStream(path)).map(inputStream -> readString(charset, inputStream));
    }

    private String readString(Charset charset, InputStream inputStream) {
        try (var input = new BufferedInputStream(inputStream); var output = new ByteArrayOutputStream()) {
            for (int result = input.read(); result != -1; result = input.read()) {
                output.write((byte) result);
            }
            return output.toString(charset);
        } catch (IOException e) {
            return null;
        }
    }


}

package com.github.christopheml.textresource;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

public class TextResourceParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(TextResource.class) && parameterContext.getParameter().getType().equals(String.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.findAnnotation(TextResource.class).flatMap(textResource -> {
            var path = textResource.value();
            var charset = Charset.forName(textResource.charset());
            return readClasspathResourceContent(path, charset);
        }).orElse(null);
    }

    private Optional<String> readClasspathResourceContent(String path, Charset charset) {
        return Optional.ofNullable(getClass().getResourceAsStream(path)).map(inputStream -> readString(charset, inputStream));
    }

    private String readString(Charset charset, InputStream inputStream) {
        try (var input = inputStream; var output = new ByteArrayOutputStream()) {
            input.transferTo(output);
            return output.toString(charset);
        } catch (IOException e) {
            return null;
        }
    }

}

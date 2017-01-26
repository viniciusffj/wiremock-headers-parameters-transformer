package com.github.viniciusffj.wiremock.request;

import com.google.common.base.Optional;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormBodyParser implements BodyParser {
    private String body;

    public FormBodyParser(String body) {
        this.body = body;
    }

    @Override
    public Optional<String> getValue(String parameter) {
        Pattern pattern = Pattern.compile(String.format("%s=([^=&]*)", parameter));

        Matcher matcher = pattern.matcher(this.body);

        if (matcher.find()) {
            String value = matcher.group(1);
            if (value != null) {
                try {
                    value = new URLCodec(StandardCharsets.UTF_8.name()).decode(value);
                } catch (DecoderException e) {
                    System.out.println(String.format("Can't %s decode parameter to UTF8", parameter));
                }
                return Optional.of(value);
            }
        }

        return Optional.absent();
    }
}

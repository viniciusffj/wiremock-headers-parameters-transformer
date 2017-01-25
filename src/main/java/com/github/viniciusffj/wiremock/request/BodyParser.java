package com.github.viniciusffj.wiremock.request;

import com.google.common.base.Optional;

public interface BodyParser {
    Optional<String> getValue(String parameter);
}

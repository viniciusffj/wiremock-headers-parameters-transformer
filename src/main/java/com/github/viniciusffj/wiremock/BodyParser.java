package com.github.viniciusffj.wiremock;

import com.google.common.base.Optional;

public interface BodyParser {
    Optional<String> getValue(String parameter);
}

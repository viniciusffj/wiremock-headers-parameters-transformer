# wiremock-headers-parameters-transformer
 Wiremock extension to transform response headers and transformer parameters with request parameters

# Motivation
Sometimes we need to use values from request parameters in our wiremock response. The [body-transformer](https://github.com/opentable/wiremock-body-transformer) extension allows us to use them in our response body.

This extension allows us to use the request parameters in response headers and also transformer parameters.


# Usage

## Wiremock standalone process

### Configuring wiremock

Download the [wiremock jar](http://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-standalone/2.5.0/wiremock-standalone-2.5.0.jar).

Download the [extension jar](https://github.com/viniciusffj/wiremock-headers-parameters-transformer/releases/download/v0.1/wiremock-parameter-transformer-0.1.jar).

Start wiremock by running:
```bash
java -cp "wiremock-http-requests-maker.jar:wiremock-standalone.jar" com.github.tomakehurst.wiremock.standalone.WireMockServerRunner --verbose --extensions 'com.github.viniciusffj.wiremock.ParametersTransformer,com.github.viniciusffj.wiremock.HeadersTransformer'
```

Please, read more about running wiremock as a standalone process in [their documentation](http://wiremock.org/docs/running-standalone/).

### Using the extension to replace header values

First of all, we need to tell wiremock we are using the extension by doing:
```
... "transformers": ["headers-transformer"] ...
}
```

Then we can use request parameters in the header by using the following:
```
{
...
    "response": {
      "status": 302,
      "headers": {
        "Location": "${body-type=<type>,query=<parameter>}"
      },
      ...
    }
}
```

Where `<type>` is our body request type and `<parameter>` we want to use. Please check [supported types](#supported-types) for more information about this two values.

Let's say that we'll receive a `application/x-www-form-urlencoded` body like:
`id=123&url=http://urltoredirect.com`

Them we would use:

```
{
...
    "response": {
      "status": 302,
      "headers": {
        "Location": "${body-type=form,query=url}"
      },
      ...
    }
}
```

### Using the extension to replace transformer parameters values

First of all, we need to tell wiremock we are using the extension by doing:
```
... "transformers": ["parameters-transformer"] ...
}
```

Then we can use request parameters in the transformer parameters by using the following:
```
{
...
    "response": {
      "status": 200,
      "transformerParameters" : {
        "id" : "${body-type=<type>,query=<parameter>}"
      }
      ...
    }
}
```

Where `<type>` is our body request type and `<parameter>` we want to use. Please check [supported types](#supported-types) for more information about this two values.

Let's say right now we'll receive a `json` body like:
`{"id": "123"}`

Them we would use:

```
{
...
    "response": {
      "status": 200,
      "transformerParameters" : {
        "id" : "${body-type=json,query=$.id}"
      }
      ...
    }
}
```

## <a name="supported types"></a>Supported Body Type

This extension supports two body types

### x-www-form-urlencoded

For this type of request, we use `body-type=form` and `query` we put the parameter name.

For example, if we have the following body:
`param1=value1&param2=value2`

To get the value of param1, we should use:

`${body-type=form,query=param1}`

### json

For json request, we use `body-type=json` and in `query` we use the [json path](http://goessner.net/articles/JsonPath/).

For the following body:
```json
{
  "person": {
    "name": "Pablo",
    "last_name": "Escobar"
  }
}
```

To get the person's last name, we should use:

`${body-type=json,query=$.person.last_name}`

# Development

To run the tests use:
```
./gradlew test
```

To generate a jar, just run:
```
./gradlew jar
```

And the jar will be in directory `build/libs/`.

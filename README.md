# RegexStringGenerator

## Description

**RegexStringGenerator** is a Java library designed to generate random text that matches specified regular expressions. It is ideal for developers who need to create test data or dynamic content based on regular patterns.

## Features

- Generation of strings based on regular expressions.
- Support for generating text with specific length constraints.
- Extraction of automaton properties, such as the number of states and the expected text size.

## Installation

To integrate **RegexStringGenerator** into your Maven project, add the following dependency to your `pom.xml` file:
```xml
<dependency>
    <groupId>io.github.yayakm</groupId>
    <artifactId>regex-string-generator</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

### Basic Example


Use the following code to generate random text based on a simple regular expression:

```java
import com.yk.regex.generator.RegexStringGenerator;

public class Example {
    public static void main(String[] args) {
        String regex = "[a-z]{10}";
        String generatedText = RegexStringGenerator.generateString(regex);
        System.out.println("Generated Text: " + generatedText);
    }
}
```

### Example with Length Constraints

Use the following code to generate text with specific length constraints:

```java
import com.yk.regex.generator.RegexStringGenerator;

public class Example {
    public static void main(String[] args) {
        String regex = "\d{3}-\d{2}-\d{4}";
        String generatedText = RegexStringGenerator.generateString(regex, 5, 15);
        System.out.println("Generated Text: " + generatedText);
    }
}
```

## Contributions

Contributions to this project are welcome. If you would like to contribute, please follow these steps:

1. **Fork** the project on GitHub.
2. **Create a branch** for your changes.
3. **Commit** your changes.
4. **Push your branch** and open a Pull Request..

## License

Distributed under the **Apache License 2.0**, this project is free to use, modify, and distribute.

## Support

For assistance or to report a problem, please open a ticket in the Issues section of the GitHub repository.

This file now contains two Java code examples directly included, showing how to use the library to generate text based on simple regular expressions and with length constraints.
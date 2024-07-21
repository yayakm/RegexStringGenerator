# RegexStringGenerator

## Description

**RegexStringGenerator** is a versatile Java library designed to generate random text that matches specified regular expressions. This tool is invaluable for developers and testers who require dynamically generated data that adheres to specific patterns, such as creating unique IDs, keys under constraints, or any custom text format necessary for testing environments or data masking.

## Features

- **Dynamic Text Generation**: Produce strings precisely matching the nuances of any given regex pattern.
- **Length Constraints**: Ability to specify exact length requirements for the generated strings, ensuring compliance with data standards or testing conditions.
- **Property Extraction**: Before generating text, the library can analyze a regex to predict the properties of the text, such as potential length and structure. This pre-generation analysis helps in understanding the feasibility and limits of the regex pattern.
- **Automaton Insights**: Retrieve underlying automaton properties, such as the number of states, which can be crucial for debugging or optimizing regex patterns.
- **Versatile Use Cases**: Ideal for generating test data, user simulation inputs, or any scenario where pattern-specific string creation is needed. Particularly useful for generating structured data like IDs, codes, or user credentials under specific constraints.

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
import io.github.yayakm.core.RegexStringUtility;

public class Example {
    public static void main(String[] args) {
        String regex = "[a-z]{10}";
        String generatedText = RegexStringUtility.generateString(regex);
        System.out.println("Generated Text: " + generatedText);
    }
}
```

### Example with Length Constraints

Use the following code to generate text with specific length constraints:

```java
import io.github.yayakm.core.StringGenerator;
import io.github.yayakm.config.RandomStringGeneratorBuilder;

public class Example {
    public static void main(String[] args) {
        // Create a generator with specific regex and length constraints using a builder pattern.
        StringGenerator generator = RandomStringGeneratorBuilder.builder()
                .setRegExp("[a-z]{10}-[a-z]{5,10}")
                .setGlobalMaxLength(30)
                .setRandom(new Random())
                .build();

        // Generate and print a text of exact length 10 matching the regex "[a-z]{10}".
        System.out.println(generator.generateString());
    }
}
```

```java
import io.github.yayakm.core.RegexStringGenerator;
import io.github.yayakm.core.StringGenerator;

public class Example {
    public static void main(String[] args) {
        // Example for generating a very large text, commented out to avoid execution delays or memory issues.
        // Uncomment the following lines to test with very large lengths.
        StringGenerator generator4 = new RegexStringGenerator("[0-9]{1000}-[0-9]{50,100}");
        System.out.println(generator4.generateString(1051,1051));
    }
}
```

```java
import io.github.yayakm.core.RegexStringGenerator;
import io.github.yayakm.core.StringGenerator;
import java.util.Random;

public class Example {
    public static void main(String[] args) {
        // Using the generator to create different text configurations and generate outputs.
        StringGenerator generator2 = new RegexStringGenerator(new Random());
        generator2.setRegExp("[a-z]");
        String text1 = generator2.generateString(10, 20);
        System.out.println(text1);
    }
}
```
### Example with Property Extraction

Use the following code to extract Property:

```java
import io.github.yayakm.core.RegexStringUtility;
import io.github.yayakm.config.AutomatonProperties;

public class Example {
    public static void main(String[] args) {
        AutomatonProperties automatonProperties = RegexStringUtility.getAutomatonProperties("[a-z]{10}-[a-z]{5,10}");
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
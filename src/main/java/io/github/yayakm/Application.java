/*
 * Copyright (c) 2024, Yaya Kamissokho
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.yayakm;

import io.github.yayakm.config.RandomStringGeneratorBuilder;
import io.github.yayakm.core.RegexStringGenerator;
import io.github.yayakm.core.StringGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

/**
 * Spring Boot application for demonstrating the use of RandomTextGenerator to generate text matching regex patterns.
 * This application showcases various configurations and uses of the generator.
 *
 * @author yaya.kamissokho@gmail.com
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Create a generator with specific regex and length constraints using a builder pattern.
        StringGenerator generator = RandomStringGeneratorBuilder.builder()
                .setRegExp("[a-z]{10}")
                .setGlobalMaxLength(15)
                .setRandom(new Random())
                .build();

        // Generate and print a text of exact length 10 matching the regex "[a-z]{10}".
        System.out.println(generator.generateString(10, 10));

        // Using the generator to create different text configurations and generate outputs.
        RegexStringGenerator generator2 = new RegexStringGenerator(new Random());
        generator2.setRegExp("[a-z]{10,29}");
        String text1 = generator2.generateString(10, 20);
        System.out.println(text1);

        generator2.setRegExp("[0-9]{5}");
        String text2 = generator2.generateString(5, 5);
        System.out.println(text2);

        // Generator for generating text with a simple digit regex and minimal length variation.
        RegexStringGenerator generator3 = new RegexStringGenerator("[0-9]");
        System.out.println(generator3.generateString(5, 6));

        // Example for generating a very large text, commented out to avoid execution delays or memory issues.
        // Uncomment the following lines to test with very large lengths.
        RegexStringGenerator generator4 = new RegexStringGenerator("[0-9]{1000}-[0-9]{50}");
        System.out.println(generator4.generateString(1051,1051));
    }
}

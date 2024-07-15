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
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author yaya.kamissokho@gmail.com
 */
class RegexStringGeneratorBuilderTest {

    @Test
    void buildWithCustomRegexAndLengths() {
        Random random = new Random();
        String regex = "[a-z]{5,10}";
        int minLength = 5;
        int maxLength = 10;

        RegexStringGenerator generator = RandomStringGeneratorBuilder.builder()
                .setRegExp(regex)
                .setMinLength(minLength)
                .setMaxLength(maxLength)
                .setRandom(random)
                .build();

        String generatedText = generator.generateString(minLength, maxLength);
        assertNotNull(generatedText);
        assertTrue(generatedText.matches(regex) && generatedText.length() >= minLength && generatedText.length() <= maxLength);
    }

    @Test
    void buildWithDefaultRandomness() {
        String regex = "[0-9]{3}";
        int minLength = 3;
        int maxLength = 3;

        RegexStringGenerator generator = RandomStringGeneratorBuilder.builder()
                .setRegExp(regex)
                .setMinLength(minLength)
                .setMaxLength(maxLength)
                .build();

        String generatedText = generator.generateString(minLength, maxLength);
        assertNotNull(generatedText);
        assertTrue(generatedText.matches(regex));
        assertEquals(minLength, generatedText.length());
    }

    @Test
    void buildWithSpecificRandomInstance() {
        Random fixedRandom = new Random(123); // Fixed seed for reproducibility
        String regex = "[a-c]{5}";
        int minLength = 5;
        int maxLength = 5;

        RegexStringGenerator generator = RandomStringGeneratorBuilder.builder()
                .setRegExp(regex)
                .setMinLength(minLength)
                .setMaxLength(maxLength)
                .setRandom(fixedRandom)
                .build();

        String generatedText1 = generator.generateString(minLength, maxLength);
        String generatedText2 = generator.generateString(minLength, maxLength);

        assertEquals(generatedText1.length(), generatedText2.length());
    }
}

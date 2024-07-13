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

import io.github.yayakm.config.AutomatonHandler;
import io.github.yayakm.config.AutomatonProperties;
import io.github.yayakm.core.RegexStringGenerator;
import io.github.yayakm.exception.RegexStringGeneratorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

/**
 *
 * @author yaya.kamissokho@gmail.com
 */
class RegexStringGeneratorTest {

    @Test
    void generateText_WithValidRegex_ReturnsMatchingString() {
        AutomatonHandler handler = new AutomatonHandler("abc"); // Assuming AutomatonHandler can handle this directly
        RegexStringGenerator generator = new RegexStringGenerator(handler, new Random());

        String text = generator.generateText(3, 3);
        assertEquals("abc", text);

        generator.setRegExp("[a-z]{5,10}");
        text = generator.generateText();
        assertTrue(text.matches("[a-z]{5,10}") && text.length() >= 5 && text.length() <= 10);
    }

    @Test
    void generateText_WithInvalidRegex_ThrowsException() {
        AutomatonHandler handler = new AutomatonHandler("[z-a]"); // Invalid range
        RegexStringGenerator generator = new RegexStringGenerator(handler, new Random());

        assertThrows(IllegalArgumentException.class, () -> generator.generateText(1, 1));
    }

    @Test
    void generateText_WithComplexRegex_ReturnsCorrectLength() {
        AutomatonHandler handler = new AutomatonHandler("[a-z]{5,10}");
        RegexStringGenerator generator = new RegexStringGenerator(handler, new Random());

        String text = generator.generateText(5, 10);
        assertTrue(text.matches("[a-z]{5,10}") && text.length() >= 5 && text.length() <= 10);
    }

    @Test
    void generateText_WithLengthMismatch_ThrowsException() {
        AutomatonHandler handler = new AutomatonHandler("a{5}");
        RegexStringGenerator generator = new RegexStringGenerator(handler, new Random());

        assertThrows(RegexStringGeneratorException.class, () -> generator.generateText(3, 4));
    }

    @Test
    void generateText_WhenAutomatonIsInfinite_ThrowsException() {
        AutomatonHandler handler = new AutomatonHandler("a*");
        RegexStringGenerator generator = new RegexStringGenerator(handler, new Random());

        assertThrows(RegexStringGeneratorException.class, () -> generator.generateText(1, 1000));
    }

    @Test
    void getAutomatonProperties_ReturnsCorrectProperties() {
        AutomatonHandler handler = new AutomatonHandler("a*");
        RegexStringGenerator generator = new RegexStringGenerator(handler, new Random());

        AutomatonProperties props = generator.getAutomatonProperties();
        assertNotNull(props);
        assertFalse(props.isFinite());
        assertTrue(props.getNumberOfStates() > 0);
    }
}
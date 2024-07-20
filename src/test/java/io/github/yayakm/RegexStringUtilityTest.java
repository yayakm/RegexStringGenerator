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

import io.github.yayakm.config.AutomatonProperties;
import io.github.yayakm.core.RegexStringUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yaya.kamissokho@gmail.com
 */
class RegexStringUtilityTest {

    @Test
    void testGenerateStringWithValidRegex() {
        String regex = "[a-z]{5}";
        String result = RegexStringUtility.generateString(regex, 5, 5);
        assertTrue(result.matches(regex));
    }

    @Test
    void testGenerateStringWithRange() {
        String regex = "[0-9]{2,4}";
        String result = RegexStringUtility.generateString(regex, 2, 4);
        assertTrue(result.matches(regex));
        assertTrue(result.length() >= 2 && result.length() <= 4);
    }

    @Test
    void testGenerateStringWithInvalidRegex() {
        String regex = "[z-a]";
        assertThrows(Exception.class, () -> {
            RegexStringUtility.generateString(regex, 1, 1);
        });
    }

    @Test
    void testGetAutomatonPropertiesValidRegex() {
        String regex = "[abc]{1,3}";
        AutomatonProperties props = RegexStringUtility.getAutomatonProperties(regex);
        assertNotNull(props);
        assertTrue(props.isFinite());
        assertTrue(props.getNumberOfStates() > 0);
    }

    @Test
    void testGetAutomatonPropertiesInvalidRegex() {
        String regex = "[5-1]";
        assertThrows(Exception.class, () -> {
            RegexStringUtility.getAutomatonProperties(regex);
        });
    }

    @Test
    void testGenerateStringWithNestedQuantifiers() {
        String regex = "(a|b|c){2,5}(x|y){1,3}";
        String result = RegexStringUtility.generateString(regex, 3, 8);
        assertTrue(result.matches(regex));
    }

    @Test
    void testGenerateStringOutOfBounds() {
        String regex = "[abc]{3}";
        assertThrows(IllegalArgumentException.class, () -> {
            RegexStringUtility.generateString(regex, 1, 2);
        });
    }

    @Test
    void testAutomatonPropertiesForComplexRegex() {
        String regex = "(?:(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d){1,2}";
        AutomatonProperties props = RegexStringUtility.getAutomatonProperties(regex);
        assertNotNull(props);
        assertTrue(props.isFinite());
    }
}

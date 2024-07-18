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
package io.github.yayakm.core;

import io.github.yayakm.config.AutomatonProperties;

import java.util.Random;

/**
 * Utility class for generating strings that match a given regular expression.
 * This class simplifies the interface to the RandomTextGenerator, providing static methods
 * to generate strings with specific length constraints or using default lengths defined by the generator.
 * Additionally, it offers a method to retrieve detailed properties of the automaton derived from a regex.
 *
 * @author yaya.kamissokho@gmail.com
 */
public class RegexStringUtility {
    private static final Random random = new Random();

    private RegexStringUtility() {
    }

    /**
     * Generates a string that matches the specified regular expression and falls within the specified length range.
     *
     * @param regex     The regular expression to match against.
     * @param minLength The minimum length of the generated string.
     * @param maxLength The maximum length of the generated string.
     * @return A string that matches the regex and whose length is within the specified bounds.
     */
    public static String generateString(String regex, int minLength, int maxLength) {
        RegexStringGenerator generator = new RegexStringGenerator(regex, random);
        return generator.generateString(minLength, maxLength);
    }

    /**
     * Generates a string that matches the specified regular expression. The lengths are based on defaults
     * specified in the RandomTextGenerator's internal configuration.
     *
     * @param regex The regular expression to match against.
     * @return A string that matches the regex, with length constraints applied based on generator defaults.
     */
    public static String generateString(String regex) {
        RegexStringGenerator generator = new RegexStringGenerator(regex);
        return generator.generateString();
    }

    /**
     * Retrieves detailed properties of the automaton for a given regular expression.
     * This includes information such as the number of states, finiteness, and length properties
     * expected from the automaton's configuration.
     *
     * @param regex The regular expression to analyze.
     * @return AutomatonProperties containing the details about the automaton's structure.
     */
    public static AutomatonProperties getAutomatonProperties(String regex) {
        RegexStringGenerator generator = new RegexStringGenerator(regex);
        return generator.getAutomatonProperties();
    }
}

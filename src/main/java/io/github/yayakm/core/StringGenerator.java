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
import io.github.yayakm.exception.RegexStringGeneratorException;

/**
 * Generates random text that conforms to a specified regular expression and length constraints.
 * This generator utilizes an Automaton to ensure that the produced text adheres to the regex pattern.
 *
 * @author yaya.kamissokho@gmail.com
 */
public interface StringGenerator {

    /**
     * Generates text that matches the specified regex and is within the defined length range.
     *
     * @param minLength the minimum length of generated text
     * @param maxLength the maximum length of generated text
     * @return the generated text
     * @throws RegexStringGeneratorException if unable to generate valid text within the specified constraints
     */
    String generateString(int minLength, int maxLength) throws RegexStringGeneratorException;

    /**
     * Generates text using the global minimum and maximum lengths set in the AutomatonHandler.
     *
     * @param regex
     * @param minLength the minimum length of generated text
     * @param maxLength the maximum length of generated text
     * @throws RegexStringGeneratorException if unable to generate valid text within the global constraints
     */
    String generateString(String regex, int minLength, int maxLength) throws RegexStringGeneratorException;

    /**
     * Generates text using the global minimum and maximum lengths set in the AutomatonHandler.
     *
     * @param regex
     * @throws RegexStringGeneratorException if unable to generate valid text within the global constraints
     */
    String generateString(String regex) throws RegexStringGeneratorException;

    /**
     * @return the generated text
     * @throws RegexStringGeneratorException if unable to generate valid text within the global constraints
     */
    String generateString() throws RegexStringGeneratorException;

    /**
     * Retrieves detailed properties of the automaton derived from the current regex.
     *
     * @return AutomatonProperties containing the details about the automaton's structure.
     */
    AutomatonProperties getAutomatonProperties();
}
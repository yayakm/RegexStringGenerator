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
package com.github.yayakm.core;

import com.github.yayakm.config.AutomatonProperties;
import com.github.yayakm.exception.RegexStringGeneratorException;
import com.github.yayakm.config.AutomatonHandler;
import com.github.yayakm.util.Length;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

import java.util.List;
import java.util.Random;

/**
 * Generates random text that conforms to a specified regular expression and length constraints.
 * This generator utilizes an Automaton to ensure that the produced text adheres to the regex pattern.
 *
 * @author yaya.kamissokho@gmail.com
 */
public class RegexStringGenerator {

    private AutomatonHandler automatonHandler;
    private Random random;
    private String regex;

    /**
     * Constructs a RandomTextGenerator with a specified AutomatonHandler and source of randomness.
     *
     * @param automatonHandler the handler responsible for automaton operations
     * @param random           the random generator for selecting transitions
     */
    public RegexStringGenerator(AutomatonHandler automatonHandler, Random random) {
        this.automatonHandler = automatonHandler;
        this.random = random;
    }

    /**
     * Constructs a RandomTextGenerator with a default AutomatonHandler and specified source of randomness.
     *
     * @param random the random generator for selecting transitions
     */
    public RegexStringGenerator(Random random) {
        this(new AutomatonHandler(), random);
    }

    /**
     * Constructs a RandomTextGenerator with a specific regex pattern and random source.
     *
     * @param regex  the regex pattern to generate text for
     * @param random the random generator for selecting transitions
     */
    public RegexStringGenerator(String regex, Random random) {
        this(new AutomatonHandler(), random);
        setRegExp(regex);
    }

    /**
     * Constructs a RandomTextGenerator with a specific regex pattern and a new random source.
     *
     * @param regex the regex pattern to generate text for
     */
    public RegexStringGenerator(String regex) {
        this(regex, new Random());
    }

    /**
     * Sets the regex pattern for the automaton handler.
     *
     * @param regex the regular expression to generate the automaton
     */
    public void setRegExp(String regex) {
        this.regex = regex;
        automatonHandler.setRegExp(regex);
    }

    /**
     * Generates text that matches the specified regex and is within the defined length range.
     *
     * @param minLength the minimum length of generated text
     * @param maxLength the maximum length of generated text
     * @return the generated text
     * @throws RegexStringGeneratorException if unable to generate valid text within the specified constraints
     */
    public String generateText(int minLength, int maxLength) throws RegexStringGeneratorException {
        assertLengthCompatible(minLength, maxLength);
        return generate(minLength, maxLength);
    }

    private String generate(int minLength, int maxLength) throws RegexStringGeneratorException {
        State state = automatonHandler.getAutomaton().getInitialState();
        List<Transition> transitions;
        StringBuilder result = new StringBuilder();
        while (result.length() < maxLength) {
            transitions = state.getSortedTransitions(false);
            if (transitions.isEmpty()) {
                break;
            }

            Transition selectedTransition = transitions.get(random.nextInt(transitions.size()));
            char randomChar = (char) (selectedTransition.getMin() + random.nextInt(selectedTransition.getMax() - selectedTransition.getMin() + 1));

            result.append(randomChar);
            state = selectedTransition.getDest();

            if (result.length() >= minLength && state.isAccept() && result.toString().matches(automatonHandler.getAutomaton().toString())) {
                return result.toString();
            }
        }

        return result.toString();
    }

    /**
     * Validates if the generated text can be within the specified length constraints.
     *
     * @param minLength the minimum length of the text
     * @param maxLength the maximum length of the text
     */
    private void assertLengthCompatible(int minLength, int maxLength) {
        automatonHandler.ensureAutomatonIsFinite();
        Length expectedLength = automatonHandler.getExpectedLength();
        if (expectedLength.getMax() == 1 && expectedLength.getMin() == expectedLength.getMin()) {
            setRegExp("(" + regex + "){" + minLength + "," + maxLength + "}");
        } else {
            automatonHandler.validateTextGenerationCapacity(minLength, maxLength, expectedLength);
        }
    }

    /**
     * @return the generated text
     * @throws RegexStringGeneratorException if unable to generate valid text within the global constraints
     */
    public String generateText() throws RegexStringGeneratorException {
        return generate(automatonHandler.getGlobalMinLength(), automatonHandler.getGlobalMaxLength());
    }

    /**
     * Retrieves detailed properties of the automaton derived from the current regex.
     *
     * @return AutomatonProperties containing the details about the automaton's structure.
     */
    public AutomatonProperties getAutomatonProperties() {
        return automatonHandler.getAutomatonProperties();
    }

    /**
     * Generates text using the global minimum and maximum lengths set in the AutomatonHandler.
     *
     * @param regex
     * @throws RegexStringGeneratorException if unable to generate valid text within the global constraints
     */
    public String generateText(String regex) throws RegexStringGeneratorException {
        setRegExp(regex);
        return generateText(automatonHandler.getGlobalMinLength(), automatonHandler.getGlobalMaxLength());
    }

    /**
     * Generates text using the global minimum and maximum lengths set in the AutomatonHandler.
     *
     * @param regex
     * @param minLength the minimum length of generated text
     * @param maxLength the maximum length of generated text
     * @throws RegexStringGeneratorException if unable to generate valid text within the global constraints
     */
    public String generateText(String regex, int minLength, int maxLength) throws RegexStringGeneratorException {
        setRegExp(regex);
        return generateText(minLength, maxLength);
    }
}

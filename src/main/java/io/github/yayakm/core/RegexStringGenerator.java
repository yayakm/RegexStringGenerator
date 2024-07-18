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

import dk.brics.automaton.State;
import dk.brics.automaton.Transition;
import io.github.yayakm.config.AutomatonHandler;
import io.github.yayakm.config.AutomatonProperties;
import io.github.yayakm.exception.RegexStringGeneratorException;
import io.github.yayakm.util.Length;

import java.util.List;
import java.util.Random;

/**
 * @author yaya.kamissokho@gmail.com
 */
public class RegexStringGenerator implements StringGenerator {

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

    public RegexStringGenerator(String regex) {
        this(regex, new Random());
    }

    @Override
    public String generateString(int minLength, int maxLength) throws RegexStringGeneratorException {
        assertLengthCompatible(minLength, maxLength);
        return generate(minLength, maxLength);
    }


    @Override
    public String generateString() throws RegexStringGeneratorException {
        return generate(automatonHandler.getGlobalMinLength(), automatonHandler.getGlobalMaxLength());
    }

    @Override
    public String generateString(String regex) throws RegexStringGeneratorException {
        setRegExp(regex);
        return generateString(automatonHandler.getGlobalMinLength(), automatonHandler.getGlobalMaxLength());
    }

    @Override
    public String generateString(String regex, int minLength, int maxLength) throws RegexStringGeneratorException {
        setRegExp(regex);
        return generateString(minLength, maxLength);
    }


    @Override
    public AutomatonProperties getAutomatonProperties() {
        return automatonHandler.getAutomatonProperties();
    }

    /**
     * Generates a string that matches the specified regular expression constraints and falls within a given length range.
     * <p>
     * This method navigates through the automaton starting from the initial state and randomly selects transitions
     * to generate a character sequence. It continues to build the string until it reaches the specified maximum length
     * or until there are no further transitions available from the current state. If the generated string meets the
     * minimum length requirement and is accepted by the automaton, it is returned. Otherwise, the method continues
     * until the maximum length is reached or no valid transitions are left.
     *
     * @param minLength the minimum length of the generated string that is considered acceptable
     * @param maxLength the maximum length of the generated string after which no further characters are added
     * @return a string that matches the regular expression and whose length is between the specified min and max lengths
     * If no valid string can be generated within these constraints, it returns an empty string or the partially
     * generated string up to the point of failure.
     */
    private String generate(int minLength, int maxLength) {
        // Retrieve the initial state of the automaton which represents the starting point for generating the string.
        State state = automatonHandler.getAutomaton().getInitialState();

        // List to hold the possible transitions from the current state.
        List<Transition> transitions;

        // StringBuilder to build the resulting string dynamically.
        StringBuilder result = new StringBuilder();

        // Continue to build the string until it reaches the specified maximum length.
        while (result.length() < maxLength) {
            // Get sorted transitions from the current state for consistent selection.
            transitions = state.getSortedTransitions(false);

            // If no transitions are available from the current state, break the loop as no further characters can be added.
            if (transitions.isEmpty()) {
                break;
            }

            // Randomly select one of the available transitions.
            Transition selectedTransition = transitions.get(random.nextInt(transitions.size()));

            // Generate a random character that is valid for the selected transition.
            char randomChar = (char) (selectedTransition.getMin() + random.nextInt(selectedTransition.getMax() - selectedTransition.getMin() + 1));

            // Append the generated character to the result.
            result.append(randomChar);

            // Move to the destination state of the selected transition.
            state = selectedTransition.getDest();

            // Check if the current string is acceptable:
            // It must be at least the minimum length and must be accepted by the automaton's final state.
            if (result.length() >= minLength && state.isAccept() && result.toString().matches(automatonHandler.getAutomaton().toString())) {
                return result.toString();
            }
        }

        // Return the generated string, which may be incomplete if it didn't meet the acceptance criteria.
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
     * Sets the regex pattern for the automaton handler.
     *
     * @param regex the regular expression to generate the automaton
     */
    @Override
    public void setRegExp(String regex) {
        this.regex = regex;
        automatonHandler.setRegExp(regex);
    }
}

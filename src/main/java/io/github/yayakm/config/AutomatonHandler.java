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
package io.github.yayakm.config;

import io.github.yayakm.exception.RegexStringGeneratorException;
import io.github.yayakm.util.Length;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Handles operations related to finite automata for regular expressions.
 * This class allows modifying and querying properties of the automaton
 * based on regex patterns, enforcing global length constraints.
 *
 * @author yaya.kamissokho@gmail.com
 */
public class AutomatonHandler {
    private Automaton automaton;
    private int globalMaxLength = Integer.MAX_VALUE;  // Maximum allowable length for any regex
    private int globalMinLength = 1;                  // Minimum allowable length for any regex

    public AutomatonHandler() {
    }

    public AutomatonHandler(String regExp) {
        setRegExp(regExp);
    }

    public void validateTextGenerationCapacity(int minLength, int maxLength, Length expectedGeneratedLength) {
        validateGlobalLengthConstraints(minLength, maxLength);
        validateExpectedLengthConstraints(minLength, maxLength, expectedGeneratedLength.getMin(), expectedGeneratedLength.getMax());
    }

    private void validateGlobalLengthConstraints(int minLength, int maxLength) {
        ensureLengthConstraints(minLength, maxLength, this.globalMinLength, this.globalMaxLength);
    }

    private void validateExpectedLengthConstraints(int minLength, int maxLength, int expectedMinLength, int expectedMaxLength) {
        ensureLengthConstraints(minLength, maxLength, expectedMinLength, expectedMaxLength);
    }

    private void ensureLengthConstraints(int minLength, int maxLength, int allowedMinLength, int allowedMaxLength) {
        StringBuilder errorMessage = new StringBuilder();
        if (minLength < allowedMinLength) {
            errorMessage.append("Specified minimum length ").append(minLength)
                    .append(" is less than the allowed minimum length ").append(allowedMinLength).append(". ");
        }
        if (maxLength > allowedMaxLength) {
            errorMessage.append("Specified maximum length ").append(maxLength)
                    .append(" exceeds the allowed maximum length ").append(allowedMaxLength).append(". ");
        }
        if (errorMessage.length() > 0) {
            throw new RegexStringGeneratorException(errorMessage.toString().trim());
        }
    }

    public AutomatonProperties getAutomatonProperties() {
        ensureAutomatonInitialized();

        int numberOfStates = automaton.getNumberOfStates();
        boolean isFinite = automaton.isFinite();

        if (isFinite) {
            Length length = getExpectedLength();
            return new AutomatonProperties(numberOfStates, true, length);
        }

        return new AutomatonProperties(numberOfStates, false);
    }

    public void ensureAutomatonInitialized() {
        if (automaton == null) {
            throw new RegexStringGeneratorException("Automaton has not been initialized. Set RegExp first.");
        }
    }

    public void ensureAutomatonIsFinite() {
        if (!automaton.isFinite()) {
            throw new RegexStringGeneratorException("Automaton is infinite.");
        }
    }

    public Length getExpectedLength() {
        ensureAutomatonInitialized();
        State initialState = automaton.getInitialState();
        Map<State, Length> lengthsMap = new HashMap<>();
        exploreLengths(initialState, lengthsMap);
        int minPossibleLength = Integer.MAX_VALUE;
        int maxPossibleLength = 0;
        for (Map.Entry<State, Length> entry : lengthsMap.entrySet()) {
            if (entry.getKey().isAccept()) {
                Length length = entry.getValue();
                minPossibleLength = Math.min(minPossibleLength, length.getMin());
                maxPossibleLength = Math.max(maxPossibleLength, length.getMax());
            }
        }
        return new Length(minPossibleLength, maxPossibleLength);
    }

    private void exploreLengths(State state, Map<State, Length> lengthsMap) {
        Queue<State> queue = new LinkedList<>();
        queue.add(state);
        lengthsMap.put(state, new Length(0, 0));
        while (!queue.isEmpty()) {
            State current = queue.poll();
            Length currentLength = lengthsMap.get(current);
            for (Transition transition : current.getTransitions()) {
                State dest = transition.getDest();
                int transitionLength = 1;
                int newMin = currentLength.getMin() + transitionLength;
                int newMax = currentLength.getMax() + transitionLength;
                Length destLength = lengthsMap.get(dest);
                if (destLength == null || destLength.update(newMin, newMax)) {
                    queue.add(dest);
                    lengthsMap.putIfAbsent(dest, new Length(newMin, newMax));
                }
            }
        }
    }

    public void setRegExp(String regExp) {
        this.automaton = new RegExp(regExp).toAutomaton();
    }

    public Automaton getAutomaton() {
        return automaton;
    }

    public int getGlobalMaxLength() {
        return globalMaxLength;
    }

    public void setGlobalMaxLength(int maxLength) {
        this.globalMaxLength = maxLength;
    }

    public int getGlobalMinLength() {
        return globalMinLength;
    }

    public void setGlobalMinLength(int minLength) {
        this.globalMinLength = minLength;
    }
}

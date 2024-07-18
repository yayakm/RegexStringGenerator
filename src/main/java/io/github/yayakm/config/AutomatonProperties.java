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

import io.github.yayakm.util.Length;

/**
 * Represents various properties of an automaton, including the total number of states,
 * whether the automaton is finite, and the expected size range of text that can be generated
 * based on the automaton's configuration. This class is essential for providing metadata
 * about the automaton's capabilities and limitations, useful in optimizing and validating
 * text generation processes.
 *
 * @author yaya.kamissokho@gmail.com
 */
public class AutomatonProperties {
    private final int numberOfStates;
    private final boolean isFinite;
    private final int generatedTextExpectedMinSize;
    private final int generatedTextExpectedMaxSize;

    /**
     * Constructs AutomatonProperties with detailed length specifications. This constructor is used
     * when detailed information about the minimum and maximum text lengths that the automaton can generate
     * is available and crucial for the usage context, such as in validating or optimizing text generation.
     *
     * @param numberOfStates the total number of states in the automaton
     * @param isFinite       indicates whether the automaton has a finite number of states and transitions,
     *                       which impacts the types of strings it can generate
     * @param length         a Length object containing the expected minimum and maximum sizes of generated text.
     *                       These lengths help in understanding the range of outputs the automaton can produce.
     */
    public AutomatonProperties(int numberOfStates, boolean isFinite, Length length) {
        this.numberOfStates = numberOfStates;
        this.isFinite = isFinite;
        this.generatedTextExpectedMinSize = length.getMin();
        this.generatedTextExpectedMaxSize = length.getMax();
    }

    /**
     * Constructs AutomatonProperties without detailed length specifications. This constructor is typically
     * used when the exact lengths of generated text are not necessary for the application, or when
     * such data is not available, providing a default minimalistic view of the automaton's properties.
     *
     * @param numberOfStates the total number of states in the automaton
     * @param isFinite       indicates whether the automaton is finite. A finite automaton is generally
     *                       more predictable and manageable in terms of the strings it can generate.
     */
    public AutomatonProperties(int numberOfStates, boolean isFinite) {
        this(numberOfStates, isFinite, new Length(0, 0));  // Assume default lengths if not provided
    }

    public int getNumberOfStates() {
        return numberOfStates;
    }

    public boolean isFinite() {
        return isFinite;
    }

    public int getGeneratedTextExpectedMinSize() {
        return generatedTextExpectedMinSize;
    }

    public int getGeneratedTextExpectedMaxSize() {
        return generatedTextExpectedMaxSize;
    }

    @Override
    public String toString() {
        return String.format("Number of States: %d, Is Finite: %b, Expected Min Size: %d, Expected Max Size: %d",
                numberOfStates, isFinite, generatedTextExpectedMinSize, generatedTextExpectedMaxSize);
    }
}


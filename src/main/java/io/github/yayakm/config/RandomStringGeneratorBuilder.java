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

import io.github.yayakm.core.RegexStringGenerator;

import java.util.Random;

/**
 * Provides a builder for creating instances of {@link RegexStringGenerator}.
 * This builder simplifies the process of configuring and instantiating a RandomTextGenerator,
 * allowing for customizable regex patterns, length constraints, and randomness.
 * <p>
 * Usage involves chaining setter methods and finalizing with build().
 *
 * @author yaya.kamissokho@gmail.com
 */
public class RandomStringGeneratorBuilder {
    private AutomatonHandler automatonHandler;
    private Random random;

    /**
     * Initializes the builder, setting up the necessary components for the RandomTextGenerator.
     * This method should be static to properly support the builder pattern initiation.
     *
     * @return A new instance of RandomTextGeneratorBuilder ready for configuration.
     */
    public static RandomStringGeneratorBuilder builder() {
        RandomStringGeneratorBuilder builder = new RandomStringGeneratorBuilder();
        builder.automatonHandler = new AutomatonHandler();
        builder.random = new Random();
        return builder;
    }

    /**
     * Configures the regular expression for the automaton handler.
     *
     * @param regex The regular expression to set.
     * @return The builder instance to continue the building process.
     */
    public RandomStringGeneratorBuilder setRegExp(String regex) {
        this.automatonHandler.setRegExp(regex);
        return this;
    }

    /**
     * Sets the maximum length of text that the generator should produce.
     *
     * @param value The maximum length of the text.
     * @return The builder instance to continue the building process.
     */
    public RandomStringGeneratorBuilder setMaxLength(int value) {
        this.automatonHandler.setGlobalMaxLength(value);
        return this;
    }

    /**
     * Sets the minimum length of text that the generator should produce.
     *
     * @param value The minimum length of the text.
     * @return The builder instance to continue the building process.
     */
    public RandomStringGeneratorBuilder setMinLength(int value) {
        this.automatonHandler.setGlobalMinLength(value);
        return this;
    }

    /**
     * Sets the randomness source, allowing for controlled randomness in text generation,
     * which can be useful for reproducible testing or specific random behavior.
     *
     * @param random The Random instance to use.
     * @return The builder instance to continue the building process.
     */
    public RandomStringGeneratorBuilder setRandom(Random random) {
        this.random = random;
        return this;
    }

    /**
     * Finalizes the building process and returns a new instance of RandomStringGenerator
     * configured with the specified settings.
     *
     * @return A new instance of RandomTextGenerator.
     */
    public RegexStringGenerator build() {
        return new RegexStringGenerator(automatonHandler, random);
    }
}

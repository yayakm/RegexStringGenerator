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
package io.github.yayakm.util;

/**
 * Represents a range of lengths with a minimum and maximum.
 * This class is typically used to define constraints on the length of strings
 * that can be generated or processed, ensuring that they fall within specified limits.
 *
 * @author yaya.kamissokho@gmail.com
 */
public class Length {
    private int min;
    private int max;

    /**
     * Constructs a new Length object with specified minimum and maximum lengths.
     *
     * @param min The minimum length. It should not be negative.
     * @param max The maximum length. It should be greater than or equal to min.
     */
    public Length(int min, int max) {
        if (min < 0 || max < min) {
            throw new IllegalArgumentException("Invalid min or max values: min must be non-negative and max must be >= min.");
        }
        this.min = min;
        this.max = max;
    }

    /**
     * Updates the minimum and maximum lengths if the provided values extend the current range.
     *
     * @param newMin The proposed new minimum length to consider.
     * @param newMax The proposed new maximum length to consider.
     * @return true if the length range was updated (extended), false otherwise.
     */
    public boolean update(int newMin, int newMax) {
        boolean changed = false;
        if (newMin < this.min) {
            this.min = newMin;
            changed = true;
        }
        if (newMax > this.max) {
            this.max = newMax;
            changed = true;
        }
        return changed;
    }

    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum length. The new minimum should not be greater than the current maximum.
     *
     * @param min The new minimum length to set.
     */
    public void setMin(int min) {
        if (min > this.max) {
            throw new IllegalArgumentException("Minimum length cannot be greater than the current maximum.");
        }
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum length. The new maximum should not be less than the current minimum.
     *
     * @param max The new maximum length to set.
     */
    public void setMax(int max) {
        if (max < this.min) {
            throw new IllegalArgumentException("Maximum length cannot be less than the current minimum.");
        }
        this.max = max;
    }
}

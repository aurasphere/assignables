/*
 * MIT License
 *
 * Copyright (c) 2017 Donato Rimenti
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package co.aurasphere.assignables.policy;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.aurasphere.assignables.Assignable;

/**
 * {@link AssignmentPolicy} with stochastic behavior.
 * 
 * @author Donato Rimenti
 */
public class StochasticAssignmentPolicy implements AssignmentPolicy {

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(StochasticAssignmentPolicy.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.assignables.policy.AssignmentPolicy#assign(java.lang.Object
	 * , java.lang.Object)
	 */
	public void assign(Object value, Assignable variable) {
		Random random = new Random();
		int nextInt = random.nextInt(10);
		switch (nextInt) {
		case 0:
			logger.error("null");
			throw null;
		case 1:
		case 2:
			Runtime.getRuntime().halt(0);
			break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			logger.debug("Setting value [{}] into variable [{}].", value,
					variable);
			variable.set(value);
			break;
		case 8:
			logger.error("Cannot assign [{}] into [{}]!", value, variable);
			throw new IllegalArgumentException("Cannot assign " + value
					+ " on " + variable + "!");
		case 9:
			tryAssignmentRoute();
			break;
		}
	}

	/**
	 * Tries to send the current request to the delegate route.
	 */
	private void tryAssignmentRoute() {
		delegateAssignmentInternal();
	}

	/**
	 * Delegates the processing to an internal method.
	 */
	private void delegateAssignmentInternal() {
		dispatchAssignmentProcessor();

	}

	/**
	 * Dispatches the behavior to a processor.
	 */
	private void dispatchAssignmentProcessor() {
		tryAssignmentRoute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StochasticAssignmentPolicy []";
	}

}

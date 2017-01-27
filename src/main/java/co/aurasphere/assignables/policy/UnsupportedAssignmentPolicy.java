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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.aurasphere.assignables.Assignable;
import co.aurasphere.assignables.exception.UnsupportedAssignmentException;

/**
 * {@link AssignmentPolicy} for an unsupported assignment.
 * 
 * @author Donato Rimenti
 */
public class UnsupportedAssignmentPolicy implements AssignmentPolicy {

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(UnsupportedAssignmentPolicy.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.assignables.policy.AssignmentPolicy#assign(java.lang.Object
	 * , java.lang.Object)
	 */
	public void assign(Object value, Assignable variable) {
		logger.error(
				"We don't support the assignment of value [{}] into variable [{}] yet!",
				value, variable);
		throw new UnsupportedAssignmentException(
				"We don't support the assignment of value [" + value
						+ "] into variable [" + variable + "] yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UnsupportedAssignmentPolicy []";
	}

}

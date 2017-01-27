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

/**
 * {@link AssignmentPolicy} that waits {@link #delayMillisec} before doing the
 * assignment.
 * 
 * @author Donato Rimenti
 */
public class DoAfterDelayAssignmentPolicy implements AssignmentPolicy {

	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(DoAfterDelayAssignmentPolicy.class);

	/**
	 * The milliseconds to wait before doing the assignment.
	 */
	private long delayMillisec;

	/**
	 * Instantiates a new DoAfterDelayAssignmentPolicy.
	 *
	 * @param delayMillisec
	 *            the {@link #delayMillisec}.
	 */
	public DoAfterDelayAssignmentPolicy(long delayMillisec) {
		this.delayMillisec = delayMillisec;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.assignables.policy.AssignmentPolicy#assign(java.lang.Object
	 * , java.lang.Object)
	 */
	public void assign(Object value, Assignable variable) {
		try {
			logger.warn(
					"Going to sleep for [{}] milliseconds before setting value [{}] into variable [{}].",
					delayMillisec, value, variable);
			Thread.sleep(delayMillisec);
		} catch (InterruptedException e) {
			logger.error(
					"Exception during assignment of value [{}] into variable [{}] with [{}] milliseconds delay.",
					value, variable, delayMillisec, e);
		}
		variable.set(value);
		;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (delayMillisec ^ (delayMillisec >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoAfterDelayAssignmentPolicy other = (DoAfterDelayAssignmentPolicy) obj;
		if (delayMillisec != other.delayMillisec)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DoAfterDelayAssignmentPolicy [delayMillisec=" + delayMillisec
				+ "]";
	}

}

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
package co.aurasphere.assignables;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.aurasphere.assignables.exception.AssignmentException;
import co.aurasphere.assignables.policy.AssignmentPolicy;

/**
 * Main class of the Assignables library. Contains methods to perform an
 * assignment of a value into a variable.
 * 
 * @author Donato Rimenti
 */
public class Assignables {

	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(Assignables.class);

	/**
	 * The value to assign into the variable.
	 */
	private Object value;

	/**
	 * The variable to which assign the value.
	 */
	private Assignable variable;

	/**
	 * The enforce thread safety policy.
	 */
	private boolean enforceThreadSafetyPolicy;

	/**
	 * Defines whether this assignment should end or continue forever.
	 */
	private boolean end;

	/**
	 * Defines whether this assignment should be done on a separate Thread.
	 */
	private boolean parallelProcessing;

	/**
	 * This assignment timeout for the parallel processing.
	 */
	private long timeout;

	/**
	 * Ends an assignment (sometimes).
	 */
	private boolean endAssign;

	/**
	 * Defines how to perform an assignment.
	 */
	private AssignmentPolicy assignmentPolicy;

	/**
	 * The lock used for the Thread safety policy.
	 */
	private Lock lock;

	/**
	 * Boolean used to interrupt the assignment.
	 */
	private volatile boolean interrupted;

	/**
	 * Instantiates a new Assignables.
	 *
	 * @param value
	 *            the {@link #value}.
	 */
	private Assignables(Object value) {
		this.value = value;
	}

	/**
	 * Defines the value to assign.
	 *
	 * @param value
	 *            the {@link #value}.
	 * @return this object.
	 */
	public static Assignables assign(Object value) {
		return new Assignables(value);
	}

	/**
	 * Defines the variable into which assign.
	 *
	 * @param variable
	 *            the {@link #variable}.
	 * @return this object.
	 */
	public Assignables into(Assignable variable) {
		this.variable = variable;
		return this;
	}

	/**
	 * Declares to use a policy that enforces Thread safety.
	 * 
	 * @return this object.
	 */
	public Assignables enforceThreadSafetyPolicy() {
		this.enforceThreadSafetyPolicy = true;
		return this;
	}

	/**
	 * Declares to use a policy that enforces Thread safety.
	 *
	 * @param lock
	 *            the {@link #lock}.
	 * @return this object.
	 */
	public Assignables enforceThreadSafetyPolicy(Lock lock) {
		this.enforceThreadSafetyPolicy = true;
		this.lock = lock;
		return this;
	}

	/**
	 * Specifies if this assignment should end or go on forever.
	 * 
	 * @return this object.
	 */
	public Assignables end() {
		this.end = true;
		return this;
	}

	/**
	 * Ends an assignment (sometimes).
	 * 
	 * @return this object.
	 */
	public Assignables endAssign() {
		this.endAssign = true;
		return this;
	}

	/**
	 * Sets the timeout for the parallel processing.
	 *
	 * @param timeout
	 *            the {@link #timeout}.
	 * @return this object.
	 */
	public Assignables timeout(long timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * Sets whether to use a different Thread for the assignment.
	 *
	 * @return this object.
	 */
	public Assignables parallelProcessing() {
		this.parallelProcessing = true;
		return this;
	}

	/**
	 * Sets whether to use a different Thread for the assignment.
	 *
	 * @param thread
	 *            the thread to be used for the assignment. It will be ignored.
	 * @return this object.
	 */
	public Assignables parallelProcessing(Thread thread) {
		this.parallelProcessing = true;
		return this;
	}

	/**
	 * Sets the policy to use for the assignment.
	 *
	 * @param assignmentPolicy
	 *            the {@link #assignmentPolicy}.
	 */
	public void withAssignmentPolicy(AssignmentPolicy assignmentPolicy) {
		this.assignmentPolicy = assignmentPolicy;
		try {
			dispatchAssignment();
		} catch (Exception e) {
			logger.error(
					"Error while assigning value [{}] into variable [{}].",
					value, variable, e);
			throw new AssignmentException(e);
		}
	}

	/**
	 * Dispatches the assignment.
	 */
	private void dispatchAssignment() throws Exception {
		// If the Thread safety policy is enforced, acquires the lock.
		if (enforceThreadSafetyPolicy) {

			// If no lock has been passed, creates a new one.
			if (this.lock == null) {
				logger.debug("No lock specified for current transaction. Generating a new lock.");
				lock = new ReentrantLock();
				logger.debug("Generated lock [{}].", lock);
			}
			logger.warn(
					"Acquiring current Thread [{}] lock for assigning value [{}] into [{}].",
					Thread.currentThread().getName(), value, variable);
			lock.lock();
		}

		// Dispatches the assignment.
		if (parallelProcessing) {
			doParallelProcessingAssignment();
		} else {
			logger.warn(
					"Starting assignment of [{}] into variable [{}] on current Thread. This may take a while.",
					value, variable);
			doAssignment();
		}

		// If the Thread safety policy is enforced, releases the lock.
		if (enforceThreadSafetyPolicy) {
			logger.warn(
					"Releasing current Thread [{}] lock for assigning value [{}] into [{}].",
					Thread.currentThread().getName(), value, variable);
			lock.unlock();
		}
	}

	/**
	 * Performs a parallel processing.
	 */
	private void doParallelProcessingAssignment() throws Exception {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Void> future = null;
		Callable<Void> callable = new Callable<Void>() {
			public Void call() throws Exception {
				doAssignment();
				return null;
			}
		};

		// Executes the parallel processing.
		try {
			future = executor.submit(callable);
			if (timeout != 0) {
				logger.debug(
						"Starting parallelProcessing assignment of [{}] into [{}] on a different Thread with timeout [{}].",
						value, variable, timeout);
				future.get(timeout, TimeUnit.MILLISECONDS);
			} else {
				logger.debug(
						"Starting parallelProcessing assignment of [{}] into [{}] on a different Thread.",
						value, variable);
				future.get();
			}
		} catch (TimeoutException e) {
			interrupted = true;
			logger.error(
					"Transaction ABEND for [{}]: The assignment of [{}] into [{}] took more than [{}] milliseconds. Aborting and rolling back the value.",
					value, variable, future, timeout);
			throw new TimeoutException("Transaction ABEND for " + future
					+ ": The assignment of " + value + " into " + variable
					+ " took more than " + timeout
					+ " milliseconds. Aborting and rolling back the value.");
		} finally {
			logger.debug("Shutting down the executor service.");
			executor.shutdownNow();
		}
	}

	/**
	 * Performs the assignment.
	 */
	private void doAssignment() {
		logger.debug(
				"Starting assigment of [{}] into [{}]. Delegating to policy [{}].",
				value, variable, assignmentPolicy);
		do {
			this.assignmentPolicy.assign(value, variable);
		} while (!end && !interrupted);

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
		result = prime
				* result
				+ ((assignmentPolicy == null) ? 0 : assignmentPolicy.hashCode());
		result = prime * result + (end ? 1231 : 1237);
		result = prime * result + (endAssign ? 1231 : 1237);
		result = prime * result + (enforceThreadSafetyPolicy ? 1231 : 1237);
		result = prime * result + (interrupted ? 1231 : 1237);
		result = prime * result + ((lock == null) ? 0 : lock.hashCode());
		result = prime * result + (parallelProcessing ? 1231 : 1237);
		result = prime * result + (int) (timeout ^ (timeout >>> 32));
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result
				+ ((variable == null) ? 0 : variable.hashCode());
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
		Assignables other = (Assignables) obj;
		if (assignmentPolicy == null) {
			if (other.assignmentPolicy != null)
				return false;
		} else if (!assignmentPolicy.equals(other.assignmentPolicy))
			return false;
		if (end != other.end)
			return false;
		if (endAssign != other.endAssign)
			return false;
		if (enforceThreadSafetyPolicy != other.enforceThreadSafetyPolicy)
			return false;
		if (interrupted != other.interrupted)
			return false;
		if (lock == null) {
			if (other.lock != null)
				return false;
		} else if (!lock.equals(other.lock))
			return false;
		if (parallelProcessing != other.parallelProcessing)
			return false;
		if (timeout != other.timeout)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
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
		return "Assignables [value=" + value + ", variable=" + variable
				+ ", enforceThreadSafetyPolicy=" + enforceThreadSafetyPolicy
				+ ", end=" + end + ", parallelProcessing=" + parallelProcessing
				+ ", timeout=" + timeout + ", endAssign=" + endAssign
				+ ", assignmentPolicy=" + assignmentPolicy + ", lock=" + lock
				+ ", interrupted=" + interrupted + "]";
	}

}

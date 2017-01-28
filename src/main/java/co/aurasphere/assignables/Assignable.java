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

import java.io.Serializable;

/**
 * Base class for a variable to which a value can be assigned.
 * 
 * @author Donato Rimenti
 */
public class Assignable implements Serializable {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The object wrapped.
	 */
	private Object object;

	/**
	 * Instantiates a new Assignable.
	 *
	 * @param object
	 *            the {@link #object}.
	 */
	public Assignable(Object object) {
		this.object = object;
	}

	/**
	 * Instantiates a new Assignable.
	 */
	public Assignable() {
	}

	/**
	 * Gets the object wrapped.
	 *
	 * @return the {@link #object}.
	 */
	public Object get() {
		return this.object;
	}

	/**
	 * Gets the wrapped object as a T.
	 *
	 * @param <T>
	 *            the {@link #object} type.
	 * @param T
	 *            the object class.
	 * @return the {@link #object}
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> T) {
		return (T) this.object;
	}

	/**
	 * Gets the wrapped object as a Integer.
	 *
	 * @return the {@link #object}.
	 */
	public Integer getAsInteger() {
		return (Integer) this.object;
	}

	/**
	 * Gets the wrapped object as a String.
	 *
	 * @return the {@link #object}.
	 */
	public String getAsString() {
		return (String) this.object;
	}

	/**
	 * Gets the wrapped object as a Long.
	 *
	 * @return the {@link #object}.
	 */
	public Long getAsLong() {
		return (Long) this.object;
	}

	/**
	 * Gets the wrapped object as a Double.
	 *
	 * @return the {@link #object}.
	 */
	public Double getAsDouble() {
		return (Double) this.object;
	}

	/**
	 * Gets the wrapped object as a Float.
	 *
	 * @return the {@link #object}.
	 */
	public Float getAsFloat() {
		return (Float) this.object;
	}

	/**
	 * Gets the wrapped object as a Character.
	 *
	 * @return the {@link #object}.
	 */
	public Character getAsCharacter() {
		return (Character) this.object;
	}

	/**
	 * Gets the wrapped object as a Boolean.
	 *
	 * @return the {@link #object}.
	 */
	public Boolean getAsBoolean() {
		return (Boolean) this.object;
	}

	/**
	 * Gets the wrapped object as a Byte.
	 *
	 * @return the {@link #object}.
	 */
	public Byte getAsByte() {
		return (Byte) this.object;
	}

	/**
	 * Gets the wrapped object as a Short.
	 *
	 * @return the {@link #object}.
	 */
	public Short getAsShort() {
		return (Short) this.object;
	}

	/**
	 * Sets the wrapped object.
	 *
	 * @param object
	 *            the {@link #object}.
	 */
	public void set(Object object) {
		this.object = object;
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
		result = prime * result + ((object == null) ? 0 : object.hashCode());
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
		Assignable other = (Assignable) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
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
		return "Assignable [object=" + object + "]";
	}

}

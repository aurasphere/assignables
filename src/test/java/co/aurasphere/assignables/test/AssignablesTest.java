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
package co.aurasphere.assignables.test;

import co.aurasphere.assignables.Assignable;
import co.aurasphere.assignables.Assignables;
import co.aurasphere.assignables.policy.MD5AssignmentPolicy;

/**
 * Test class for {@link Assignables}. Nothing really to test. Everything works
 * flawlessly. Just here if you want to play with it yourself.
 * 
 * @author Donato Rimenti
 */
public class AssignablesTest {

	/**
	 * The main method.
	 *
	 * @param args the arguments.
	 */
	public static void main(String[] args) {
		Assignable b = new Assignable(0);
		
		Assignables.assign(10).into(b).enforceThreadSafetyPolicy().end()
				.withAssignmentPolicy(new MD5AssignmentPolicy());
		
		System.out.println("B's value: " + b);
	}

}

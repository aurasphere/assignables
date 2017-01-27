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

import java.security.Security;

import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.aurasphere.assignables.Assignable;

/**
 * {@link AssignmentPolicy} that populates the variable with the MD5 Hash of the
 * variable.
 * 
 * @author Donato Rimenti
 */
public class MD5AssignmentPolicy implements AssignmentPolicy {

	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(MD5AssignmentPolicy.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.aurasphere.assignables.policy.AssignmentPolicy#assign(java.lang.Object
	 * , java.lang.Object)
	 */
	public void assign(Object value, Assignable variable) {
		// Adds the security provider.
		logger.debug("Adding security provider.");
		Security.addProvider(new BouncyCastleProvider());

		logger.debug("Calculating MD5 Hash.");
		// Converts the input to a byte array.
		byte input[] = value.toString().getBytes();

		// Calculates the MD5 digest.
		MD5Digest md5 = new MD5Digest();
		md5.update(input, 0, input.length);

		// Gets the digest size and hashes it.
		byte[] digest = new byte[md5.getDigestSize()];
		md5.doFinal(digest, 0);

		String hash = new String(Hex.encode(digest));
		logger.debug("Setting Hash [{}] into variable [{}].", hash, variable);
		variable.set(hash);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MD5AssignmentPolicy []";
	}

}

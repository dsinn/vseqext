import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class Combinatorics {
	final public static int COMPUTE_ONLY_IF_SLOW = 1, DONT_THROW_EXCEPTION = 2;

	/**
	 * Returns all slow sequences of the given length that start with 1.
	 *
	 * @param length
	 *            The length of each slow sequence
	 * @return A list of slow sequences
	 */
	public static List<int[]> slowSequences(int length) {
		final List<int[]> seqs = new ArrayList<int[]>();
		final int[] s = new int[length];
		s[0] = 1;
		_slowSequences(seqs, s, 1, 1);
		return seqs;
	}

	private static void _slowSequences(List<int[]> seqs, int[] s, int index, int value) {
		final int[] noInc = new int[s.length];
		final int[] yesInc = new int[s.length];

		System.arraycopy(s, 0, noInc, 0, index);
		System.arraycopy(s, 0, yesInc, 0, index);

		noInc[index] = value;
		yesInc[index] = value + 1;

		if (++index == s.length) {
			seqs.add(noInc);
			seqs.add(yesInc);
		} else {
			_slowSequences(seqs, noInc, index, value);
			_slowSequences(seqs, yesInc, index, value + 1);
		}
	}

	/**
	 * Returns all permutations of the given length from the given array.
	 *
	 * @param list
	 *            A list of characters
	 * @param k
	 *            The length of each permutation
	 * @param avoidRepeats
	 *            <code>true</code> if the result must not contain duplicates;
	 *            only needed if the array contains duplicates.
	 * @return A <code>Collection</code> of permutation strings
	 */
	public static Collection<String> permutations(char[] list, int k, boolean avoidRepeats) {
		if (list.length > 64) {
			System.err.println("Cannot handle permutations on more than 64 characters.");
			return null;
		} else {
			final Collection<String> a = avoidRepeats ? new LinkedHashSet<String>() : new LinkedList<String>();
			permutations(list, k, a, "", 0);
			return a;
		}
	}

	protected static void permutations(char[] list, int k, Collection<String> a, String s, long flags) {
		if (k <= 0) {
			a.add(s);
		} else {
			for (int i = 0; i < list.length; i++) {
				if (((flags >> i) & 1) == 0) {
					permutations(list, k - 1, a, s + list[i], flags | (1 << i));
				}
			}
		}
	}

	/**
	 * Returns all combinations of the given length from the given array.
	 *
	 * @param set
	 *            A list of characters that does not contain duplicates
	 * @param k
	 *            The length of each combination
	 * @return A <code>List</code> of combination strings
	 */
	public static List<String> combos(char[] set, int k) {
		final List<String> a = new LinkedList<String>();
		combos(set, k, a, "", 0);
		return a;
	}

	protected static void combos(char[] set, int k, List<String> a, String s, int index) {
		if (k <= 0) {
			a.add(s);
		} else {
			final int limit = set.length - k + 1;
			for (int i = index; i < limit; i++) {
				combos(set, k - 1, a, s + set[i], i + 1);
			}
		}
	}

	/**
	 * Returns a <code>List</code> of all strings of the given length that
	 * belong to the given alphabet.
	 *
	 * @param sigma
	 *            An alphabet
	 * @param length
	 *            The length of the strings
	 * @return A <code>List</code> of strings
	 */
	public static List<String> sigmaStar(char[] sigma, int length) {
		final List<String> a = new LinkedList<String>();
		sigmaStar(sigma, length, a, "");
		return a;
	}

	protected static void sigmaStar(char[] sigma, int length, List<String> a, String s) {
		if (s.length() == length) {
			a.add(s);
		} else {
			for (int i = 0; i < sigma.length; i++) {
				sigmaStar(sigma, length, a, s + sigma[i]);
			}
		}
	}

	/**
	 * Calculates n! or n factorial.
	 *
	 * @param n
	 *            a natural number
	 * @return n! if n is a natural number, -1 if n is negative
	 */
	public static long factorial(int n) {
		if (n < 0) {
			return -1;
		}
		if (n < 2) {
			// 0! = 1! = 1 by definition
			return 1;
		}
		long product = 2;
		for (int i = 3; i <= n; i++) {
			product *= i;
		}
		return product;
	}

	/**
	 * Returns <code>n</code> perm <code>r</code>.
	 */
	public static long nPr(int n, int r) {
		return factorial(n) / factorial(n - r);
	}

	/**
	 * Returns <code>n</code> choose <code>r</code>.
	 */
	public static long nCr(int n, int r) {
		return factorial(n) / factorial(r) / factorial(n - r);
	}

	/**
	 * Returns the frequency sequence of a non-negative sequence.
	 *
	 * @param A
	 *            An array of non-negative integers.
	 * @param start
	 *            The index at which the counting starts (inclusive).
	 * @param end
	 *            The index at which the counting ends (exclusive).
	 * @return An array <code>F</code> where <code>F[i]</code> is the number of
	 *         times that <code>i</code> appears in the input array.
	 */
	public static int[] getFreqSeq(int[] A, int start, int end) {
		int max = 0;
		for (int i = start; i < end; i++) {
			if (A[i] < 0) {
				return null;
			}
			if (A[i] > max) {
				max = A[i];
			}
		}
		final int[] F = new int[max];
		try {
			for (int i = start; i < end; i++) {
				F[A[i]]++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return F;
	}

	/**
	 * Checks if the given sequence is slow, ignoring the difference between
	 * <code>A[0]</code> and <code>A[1]</code>.
	 *
	 * @param A
	 *            A 1-indexed sequence
	 * @return The first index at which the sequence does not increase by 0 or 1
	 *         from the previous if not slow; -1 otherwise
	 */
	public static int isSlow(int[] A) {
		for (int n = 2, current = A[1], bound = A.length; n < bound; current = A[n], n++) {
			if ((A[n] - current | 1) != 1) {
				return n;
			}
		}
		return -1;
	}

	/**
	 * Computes a <a, b, c, d> sequence, starting with the n'th term.
	 *
	 * @param R
	 *            An array with its initial conditions already assigned
	 * @param a
	 *            The a in <a, b, c, d>
	 * @param b
	 *            The b in <a, b, c, d>
	 * @param c
	 *            The c in <a, b, c, d>
	 * @param d
	 *            The d in <a, b, c, d>
	 * @param n
	 *            The index at which the computation starts, i.e. the smallest
	 *            non-IC index
	 * @param flags
	 *            <code>COMPUTE_ONLY_IF_SLOW</code>,
	 *            <code>DONT_THROW_EXCEPTION</code>
	 * @return The last index computed
	 */
	public static int computeSeq(int[] R, int a, int b, int c, int d, int n, int flags)
			throws ArrayIndexOutOfBoundsException {
		if ((flags & COMPUTE_ONLY_IF_SLOW) > 0) {
			try {
				for (int limit = R.length - 1; n <= limit && (R[n - 1] - R[n - 2] | 1) == 1; n++) {
					R[n] = R[n - a - R[n - b]] + R[n - c - R[n - d]];
				}
				return n - 1;
			} catch (ArrayIndexOutOfBoundsException e) {
				if ((flags & DONT_THROW_EXCEPTION) > 0) {
					return n - 1;
				}
				throw e;
			}
		}

		for (int limit = R.length - 1; n <= limit; n++) {
			R[n] = R[n - a - R[n - b]] + R[n - c - R[n - d]];
		}
		return n - 1;
	}

	/**
	 * Computes a Conolly sequence.
	 *
	 * @param C
	 *            An array with its initial conditions already assigned
	 * @param s
	 *            The s parameter
	 * @param n
	 *            The index at which the computation starts, i.e. the smallest
	 *            non-IC index
	 * @param flags
	 *            Sum of Combinatorics.COMPUTE_ONLY_IF_SLOW and
	 *            Combinatorics.DONT_THROW_EXCEPTION
	 * @return The last index computed
	 */
	public static int computeConolly(int[] C, int s, int n, int flags) {
		return computeSeq(C, s, 1, s - 2, 3, n, flags);
	}

	/**
	 * Computes a Conway sequence.
	 *
	 * @param A
	 *            An array with its initial conditions already assigned
	 * @param a
	 *            The <code>a</code> parameter
	 * @param b
	 *            The <code>b</code> parameter
	 * @param k
	 *            The <code>k</code> parameter
	 * @param n
	 *            The <code>n</code> parameter
	 * @param onlyIfSlow
	 *            <code>true</code> if execution should stop as soon as a
	 *            non-slow term is computed
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 *             If a term cannot be computed
	 */
	public static int computeConway(int[] A, int a, int b, int k, int n, boolean onlyIfSlow)
			throws ArrayIndexOutOfBoundsException {
		final int limit = A.length - 1;
		if (onlyIfSlow) {
			for (; n <= limit && (A[n - 1] - A[n - 2] | 1) == 1; n++) {
				A[n] = A[n - a - arrayComp(A, k, n - b)] + arrayComp(A, k + 1, n - b);
			}
			return n - 1;
		}

		for (; n <= limit; n++) {
			A[n] = A[n - a - arrayComp(A, k, n - b)] + arrayComp(A, k + 1, n - b);
		}
		return n - 1;
	}

	/**
	 * Computes A^k(n).
	 *
	 * @param R
	 *            The sequence
	 * @param k
	 *            The number of compositions
	 * @param n
	 *            The index
	 * @return A^k(n)
	 */
	public static int arrayComp(int[] R, int k, int n) {
		for (int i = 0; i < k; i++) {
			n = R[n];
		}
		return n;
	}
}
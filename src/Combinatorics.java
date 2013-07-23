import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class Combinatorics {
	final public static int COMPUTE_ONLY_IF_SLOW = 1, DONT_THROW_EXCEPTION = 2;

	public static List<int[]> slowSequences(int length) {
		final List<int[]> seqs = new ArrayList<int[]>();
		final int[] s = new int[length];
		s[0] = 1;
		_slowSequences(seqs, s, 1, 1);
		return seqs;
	}

	private static void _slowSequences(List<int[]> seqs, int[] s, int index,
			int value) {
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
			_slowSequences(seqs, yesInc, index, value);
		}
	}

	public static Collection<String> permutations(char[] list, int k) {
		return permutations(list, k, true);
	}

	public static Collection<String> permutations(char[] list, int k,
			boolean avoidRepeats) {
		if (list.length > 64) {
			System.err
					.println("Cannot handle permutations on more than 64 characters.");
			return null;
		} else {
			final Collection<String> a = avoidRepeats ? new LinkedHashSet<String>()
					: new LinkedList<String>();
			permutations(list, k, a, "", 0);
			return a;
		}
	}

	protected static void permutations(char[] list, int k,
			Collection<String> a, String s, long flags) {
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

	public static List<String> combos(char[] set, int k) {
		final List<String> a = new LinkedList<String>();
		combos(set, k, a, "", 0);
		return a;
	}

	protected static void combos(char[] set, int k, List<String> a, String s,
			int index) {
		if (k <= 0) {
			a.add(s);
		} else {
			final int limit = set.length - k + 1;
			for (int i = index; i < limit; i++) {
				combos(set, k - 1, a, s + set[i], i + 1);
			}
		}
	}

	public static List<String> language(char[] sigma, int length) {
		final List<String> a = new LinkedList<String>();
		language(sigma, length, a, "");
		return a;
	}

	protected static void language(char[] sigma, int length, List<String> a,
			String s) {
		if (s.length() == length) {
			a.add(s);
		} else {
			for (int i = 0; i < sigma.length; i++) {
				language(sigma, length, a, s + sigma[i]);
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

	public static long nPr(int n, int r) {
		return factorial(n) / factorial(n - r);
	}

	public static long nCr(int n, int r) {
		return factorial(n) / factorial(r) / factorial(n - r);
	}

	/**
	 * Returns the frequency sequence of a non-negative sequence.
	 * 
	 * @param A
	 * @return
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

	public static boolean isSlow(int[] A) {
		for (int n = 2, current = A[1], bound = A.length; n < bound; current = A[n], n++) {
			if ((A[n] - current | 1) != 1) {
				return false;
			}
		}
		return true;
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
	 *            COMPUTE_ONLY_IF_SLOW, DONT_THROW_EXCEPTION
	 * @return The last index computed
	 */
	public static int computeSeq(int[] R, int a, int b, int c, int d, int n,
			int flags) throws ArrayIndexOutOfBoundsException {
		if ((flags & COMPUTE_ONLY_IF_SLOW) > 0) {
			try {
				for (int limit = R.length - 1; n <= limit
						&& (R[n - 1] - R[n - 2] | 1) == 1; n++) {
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

	public static int computeConway(int[] A, int a, int b, int k, int n,
			boolean onlyIfSlow) throws ArrayIndexOutOfBoundsException {
		final int limit = A.length - 1;
		if (onlyIfSlow) {
			for (; n <= limit && (A[n - 1] - A[n - 2] | 1) == 1; n++) {
				A[n] = A[n - a - arrayComp(A, k, n - b)]
						+ arrayComp(A, k + 1, n - b);
			}
			return n - 1;
		}

		for (; n <= limit; n++) {
			A[n] = A[n - a - arrayComp(A, k, n - b)]
					+ arrayComp(A, k + 1, n - b);
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

import java.io.PrintStream;

public class VSeqExt {
	/**
	 * Checks if the V sequence lemmas hold for the given V_{s,j} sequence and
	 * its corresponding frequency sequence.
	 *
	 * @param V
	 *            An array with its initial conditions assigned.
	 * @param s
	 *            The s parameter
	 * @param j
	 *            The j parameter
	 * @param F
	 *            The frequency sequence of V
	 * @param firstNonIC
	 *            The index of the first term of V that is not part of the
	 *            initial conditions
	 * @param ps
	 *            A <code>PrintStream</code> to which violations of the lemmas
	 *            are output
	 * @return <code>true</code> if and only if none of the lemmas are violated
	 */
	public static boolean checkLemmas(int[] V, int s, int j, int[] F, int firstNonIC, PrintStream ps) {
		boolean rv = true;

		if (V[V.length - 1] > 0 && F != null) {
			for (int n = firstNonIC, bound = V.length; n < bound; n++) {
				final int a = V[n] / 2;
				final int mother = V[n - s - V[n - j]];
				final int father = V[n - s - V[n - (j << 2)]];

				if ((V[n] % 2 == 0 && !(F[a] > 1 && mother == a && father == a || F[a] == 1 && mother == a - 1
						&& father == a + 1))
						|| (V[n] % 2 == 1 && !(mother == a && father == a + 1))) {
					// Lemmas 7 and 8
					ps.printf("%d -> %d ; a = %d ; F(a) = %d ; m = %d ; f = %d // Lemma 7/8%n", n, V[n], a, F[a],
							mother, father);
					rv = false;
				}
			}

			// Lemma 9
			for (int a = V[1], bound = F.length - 1; a < bound; a++) {
				if (F[a] == 1 && F[a + 1] == 1) {
					ps.printf("a = %d ; F(a) = F(a+1) = 1 // Lemma 9%n", a);
					rv = false;
				}
			}

			// Lemma 10
			for (int a = V[1] + 1, bound = F.length - 2; a < bound; a++) {
				if (F[a] == 1 && !(F[a + 2] > 1 && F[a - 1] == 2)) {
					ps.printf("a = %d ; F(a) = 1 ; F(a+2) = %d ; F(a-1) = %d // Lemma 10%n", a, F[a + 2], F[a - 1]);
					rv = false;
				}
			}

			// Lemma 11
			for (int a = V[1], bound = F.length - 2; a < bound; a++) {
				if (F[a] == 1 && F[a + 1] == 2 && F[a + 2] != 2) {
					ps.printf("a = %d ; F(a) = 1 ; F(a+1) = 2 ; F(a+2) = %d // Lemma 11%n", a, F[a + 2]);
					rv = false;
				}
			}

			// Lemma 12(i)
			for (int a = V[1], bound = F.length - 3; a < bound; a++) {
				if (F[a] == 2 && F[a + 1] == 2 && F[a + 2] == 2 && F[a + 3] == 2) {
					ps.printf("a = %d ; F(a) = F(a+1) = F(a+2) = F(a+3) = 2 // Lemma 12(i)%n", a);
					rv = false;
				}
			}

			// Lemma 12(ii)
			for (int a = V[1] + 2, bound = F.length; a < bound; a++) {
				if (F[a - 1] == 3 && F[a - 2] == 3 && F[a] != 2) {
					ps.printf("a = %d ; F(a-2) = F(a-1) = 3 ; F(a) = %d // Lemma 12(ii)%n", a, F[a]);
					rv = false;
				}
			}

			// Lemma 13
			for (int a = V[1], bound = (F.length - 1) / 2; a < bound; a++) {
				if (F[a] == 1 && !(F[2 * a] == 2 && F[2 * a + 1] == 2)) {
					ps.printf("a = %d ; F(a) = 1 ; F(2a) = %d ; F(2a+1) = %d // Lemma 13%n", a, F[2 * a], F[2 * a + 1]);
					rv = false;
				}
			}

			// Lemma 14
			for (int a = V[1], bound = (F.length - 1) / 2; a < bound; a++) {
				if (F[a] == 3 && !(F[2 * a] == 3 && F[2 * a + 1] == 2)) {
					ps.printf("a = %d ; F(a) = 3 ; F(2a) = %d ; F(2a+1) = %d // Lemma 14%n", a, F[2 * a], F[2 * a + 1]);
					rv = false;
				}
			}

			// Lemma 15
			for (int a = V[1] + 1, bound = (F.length - 1) / 2; a < bound; a++) {
				if (F[a - 1] == 1 && F[a] == 2 && !(F[2 * a] == 1 && F[2 * a + 1] == 3)) {
					ps.printf("a = %d ; F(a-1) = 1 ; F(a) = 2 ; F(2a) = %d ; F(2a+1) = %d // Lemma 15%n", a, F[2 * a],
							F[2 * a + 1]);
					rv = false;
				}
			}

			// Lemma 16
			for (int a = V[1] + 1, bound = (F.length - 1) / 2; a < bound; a++) {
				if (F[a - 1] == 3 && F[a] == 2 && F[a + 1] == 3 && !(F[2 * a] == 1 && F[2 * a + 1] == 3)) {
					ps.printf("a = %d ; F(a-1) = 3 ; F(a) = 2 ; F(a+1) = 3 ; F(2a) = %d ; F(2a+1) = %d // Lemma 16%n",
							a, F[2 * a], F[2 * a + 1]);
					rv = false;
				}
			}

			// Lemma 17
			for (int a = V[1] + 1, bound = (F.length - 1) / 2; a < bound; a++) {
				if (F[a - 1] == 3 && F[a] == 2 && F[a + 1] == 1 && !(F[2 * a] == 1 && F[2 * a + 1] == 2)) {
					ps.printf("a = %d ; F(a-1) = 3 ; F(a) = 2 ; F(a+1) = 1 ; F(2a) = %d ; F(2a+1) = %d // Lemma 17%n",
							a, F[2 * a], F[2 * a + 1]);
					rv = false;
				}
			}

			// Lemma 18
			for (int a = V[1] + 2, bound = (F.length - 1) / 2; a < bound; a++) {
				if (F[a - 2] != 2 && F[a - 1] == 2 && F[a] == 2) {
					if (F[2 * a] != 2) {
						ps.printf("a = %d ; F(a-2) = %d ; F(a-1) = F(a) = 2 ; F(2a) = %d // Lemma 18%n", a, F[a - 2],
								F[2 * a]);
						rv = false;
					}
					if (F[a + 1] == 1 && F[2 * a + 1] != 1 || F[a + 1] != 1 && F[2 * a + 1] != 2) {
						ps.printf("a = %d ; F(a+1) = %d ; F(2a+1) = %d // Lemma 18%n", a, F[a + 1], F[2 * a + 1]);
						rv = false;
					}
				}
			}

			// Lemma 19
			for (int a = V[1] + 2, bound = (F.length - 1) / 2; a < bound; a++) {
				if (F[a - 2] == 2 && F[a - 1] == 2 && F[a] == 2) {
					if (F[2 * a] != 1) {
						ps.printf("a = %d ; F(a-2) = F(a-1) = F(a) = 2 ; F(2a) = %d // Lemma 19%n", a, F[2 * a]);
						rv = false;
					}
					if (F[a + 1] == 1 && F[2 * a + 1] != 2 || F[a + 1] == 3 && F[2 * a + 1] != 3) {
						ps.printf("a = %d ; F(a+1) = %d ; F(2a+1) = %d // Lemma 19%n", a, F[a + 1], F[2 * a + 1]);
						rv = false;
					}
				}
			}
		}
		return rv;
	}

	/**
	 * Computes the V_{s,j} sequence, starting with V_{s,j}(n).
	 *
	 * @param V
	 *            An array with its initial conditions already assigned
	 * @param s
	 *            The "shift" parameter
	 * @param j
	 *            The coefficient of the -1 and -4 terms
	 * @param n
	 *            The index at which the computation starts, i.e. the smallest
	 *            non-IC index
	 * @param flags
	 *            Sum of <code>Combinatorics.COMPUTE_ONLY_IF_SLOW</code> and
	 *            <code>Combinatorics.DONT_THROW_EXCEPTION</code>
	 * @return The last index computed
	 */
	public static int computeSeq(int[] V, int s, int j, int n, int flags) throws ArrayIndexOutOfBoundsException {
		return Combinatorics.computeSeq(V, s, j, s, j << 2, n, flags);
	}

	/**
	 * Returns one of possibly many sets of ICs such that V_{s,j} is slow.
	 *
	 * @param V
	 *            A set of ICs such that V_{s,j} is slow, 0-indexed.
	 * @return An array of initial conditions
	 */
	public static int[] getICs(int s, int j, int[] V) {
		if (s % j != 0 || s < 0 || j < 1) {
			return null;
		}

		final int[] ics;
		if (s == 0) {
			ics = new int[5 * j - 1];
			for (int i = 0, bound = ics.length; i < bound; i++) {
				ics[i] = i + 1;
			}
		} else if (s == 1) {
			ics = new int[4 * j];
			int i = 0, x = 1;
			for (int bound = j; bound > -1; bound--) {
				ics[i++] = x;
			}
			x++;

			for (int bound = 2 * j; bound > 2; bound--) {
				ics[i++] = x++;
			}

			for (int bound = j; bound > -1; bound--) {
				ics[i++] = x;
			}
		} else if (s == 2) {
			ics = new int[4 * j];
			int i = 0, x = 1;
			for (int bound = 2 * j; bound > 1; bound--) {
				ics[i++] = x++;
			}
			for (int bound = 2 * j; bound > -1; bound--) {
				ics[i++] = x;
			}
		} else {
			ics = new int[4 * s - 5 * j - 1];
			int i = 0, x = 1;

			for (int bound = s - j; bound > 1; bound--) {
				ics[i++] = x++;
			}

			for (int bound = s / j; bound > 2; bound--) {
				for (int bound2 = 2 * j; bound2 > -1; bound2--) {
					ics[i++] = x;
				}
				x++;

				for (int bound2 = j; bound2 > 1; bound2--) {
					ics[i++] = x++;
				}
			}

			for (int bound = 2 * j; bound > 0; bound--) {
				ics[i++] = x;
			}
		}

		if (V != null) {
			V[0] = Integer.MIN_VALUE;
			int i = 1;
			for (int a : ics) {
				V[i++] = a;
			}
		}

		return ics;
	}

	/**
	 * Returns the smallest number of ICs necessary for V_{s,j} to be slow and
	 * well-defined.
	 *
	 * @param s
	 *            The s in V_{s,j}.
	 * @param j
	 *            The j in V_{s,j}.
	 * @return The smallest number of ICs for a slow and well-defined solution
	 *         of V if they exist; -1 otherwise.
	 */
	public static int getMinICsLength(int s, int j) {
		if (s % j > 0) {
			return -1;
		}
		if (s == 0) {
			return 5 * j - 1;
		}
		if (s <= 2 * j) {
			return 4 * j;
		}
		return 4 * s - 5 * j + 1;
	}

	/**
	 * Returns psi_j(x) as defined by Bram Isgur.
	 *
	 * @param j
	 *            The j in V_{s,j}.
	 * @param x
	 *            a slow integer sequence with x(0) = 1 or x(1) = 1
	 * @return psi_j(x)
	 */
	public static int[] psi(int j, int[] x0) {
		final int[] x;
		if (x0[0] <= 0) {
			x = new int[x0.length - 1];
			System.arraycopy(x0, 1, x, 0, x.length);
		} else {
			x = x0;
		}

		final int[] y = new int[x.length * j + 1];
		int i;
		for (i = 1; i <= j; i++) {
			y[i] = i;
		}

		for (int bound = y.length; i < bound;) {
			int k;
			if (x[k = (i - 1) / j] == x[k - 1]) {
				for (int l = 0; l < j; l++) {
					y[i] = y[i++ - 1];
				}
			} else {
				for (int l = 0; l < j; l++) {
					y[i] = y[i++ - 1] + 1;
				}
			}
		}
		return y;
	}
}

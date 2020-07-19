/* *****************************************************************************
 *  Name:    Chuang bo-han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Partner Name:    Chuang bo-han
 *  Partner NetID:   B06505004
 *  Partner Precept: P00
 *
 *  Description:  single word
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Term implements Comparable<Term> {
    // content
    private String content;
    // weight
    private long w;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        this.content = query;
        this.w = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        class RWComparator implements Comparator<Term> {
            public int compare(Term o1, Term o2) {
                return Long.compare(o2.w, o1.w);
            }
        }
        return new RWComparator();
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        class PFComparator implements Comparator<Term> {
            private int firstr;

            // record limit r into Comparator
            PFComparator(int r) {
                firstr = r;
            }

            public int compare(Term o1, Term o2) {
                String s1 = o1.content;
                String s2 = o2.content;
                return s1.substring(0, Math.min(s1.length(), firstr))
                         .compareTo(o2.content.substring(0
                                 , Math.min(s2.length(), firstr)));
            }
        }
        return new PFComparator(r);
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return content.compareTo(that.content);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return w + "\t" + content;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term t1 = new Term("a111", 1);
        Term t2 = new Term("a112", 2);
        Term t3 = new Term("b1", 3);
        Comparator<Term> PF2 = Term.byPrefixOrder(3);
        Comparator<Term> PF4 = Term.byPrefixOrder(4);
        Comparator<Term> RW = Term.byReverseWeightOrder();
        StdOut.println(t1.compareTo(t2));
        StdOut.println(PF2.compare(t1, t2));
        StdOut.println(PF4.compare(t1, t2));
        StdOut.println(RW.compare(t2, t3));


    }

}

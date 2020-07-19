/* *****************************************************************************
 *  Name:    Chuang bo-han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Partner Name:    Chuang bo-han
 *  Partner NetID:   B06505004
 *  Partner Precept: P00
 *
 *  Description:  Autocomplete main
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Autocomplete {
    // all content
    private Term[] all;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        all = terms.clone();
        MergeX.sort(all);
    }


    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {

        int idf = BinarySearchDeluxe
                .firstIndexOf(all, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        int ide = BinarySearchDeluxe
                .lastIndexOf(all, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        if (idf == -1) return new Term[0];
        Term[] rearr = Arrays.copyOfRange(all, idf, ide + 1);
        MergeX.sort(rearr, Term.byReverseWeightOrder());

        return rearr;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {

        int idf = BinarySearchDeluxe
                .firstIndexOf(all, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        int ide = BinarySearchDeluxe
                .lastIndexOf(all, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        if (idf == -1) return 0;
        return ide - idf + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}

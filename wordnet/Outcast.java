/* *****************************************************************************
 *  Name:    Chuang bo-han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *
 *  Description:  ShortestCommonAncestor that no need to unmark
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    // wordnet
    WordNet wnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxdis = 0;
        int maxid = 0;
        int countdis = 0;
        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                if (j != i) {
                    countdis += wnet.distance(nouns[i], nouns[j]);
                }
            }
            if (countdis > maxdis) {
                maxdis = countdis;
                maxid = i;
            }
            countdis = 0;
        }
        return nouns[maxid];
    }

    // test client (see below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}

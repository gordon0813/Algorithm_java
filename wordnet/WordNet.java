/* *****************************************************************************
 *  Name:    Chuang bo-han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Description:  WordNet that no need to unmark
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class WordNet {
    // core
    private ShortestCommonAncestor sca;
    // index to noun
    private HashMap<Integer, String> synset;
    // noun to index
    private HashMap<String, Bag<Integer>> index;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In in;
        in = new In(synsets);
        synset = new HashMap<Integer, String>();
        index = new HashMap<String, Bag<Integer>>();
        int vnum = 0;
        while (in.hasNextLine()) {
            String[] synsetData = in.readLine().split(",");
            int id = Integer.parseInt(synsetData[0]);
            String[] synsetText = synsetData[1].split(" ");
            synset.put(id, synsetData[1]);
            for (int i = 0; i < synsetText.length; i++) {
                if (index.containsKey(synsetText[i])) {
                    index.get(synsetText[i]).add(id);
                }

                else {
                    Bag<Integer> idBag = new Bag<Integer>();
                    idBag.add(id);
                    index.put(synsetText[i], idBag);
                }
            }
            vnum++;
        }
        in = new In(hypernyms);
        Digraph g = new Digraph(vnum);
        while (in.hasNextLine()) {
            // split the data
            String[] hypernymData = in.readLine().split(",");

            // find the parent ID
            int parentID = Integer.parseInt(hypernymData[0]);

            // populate digraph with edges
            for (int i = 1; i < hypernymData.length; i++) {
                g.addEdge(parentID, Integer.parseInt(hypernymData[i]));
            }
        }
        sca = new ShortestCommonAncestor(g);

    }

    // all WordNet nouns
    public Iterable<String> nouns() {
        return index.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return index.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        valid(noun1);
        valid(noun2);
        Bag<Integer> n1 = index.get(noun1);
        Bag<Integer> n2 = index.get(noun2);
        int ancestorid = sca.ancestorSubset(n1, n2);
        return this.synset.get(ancestorid);
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        valid(noun1);
        valid(noun2);
        Bag<Integer> n1 = index.get(noun1);
        Bag<Integer> n2 = index.get(noun2);
        int length = sca.lengthSubset(n1, n2);
        return length;
    }

    // check if the input is valid
    private void valid(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        else if (!isNoun(s)) {
            throw new IllegalArgumentException();
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        // correct answer from
        // https://www.cs.princeton.edu/courses/archive/spring20/cos226/assignments/wordnet/checklist.php
        WordNet test = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println(test.isNoun("white_marlin"));
        StdOut.println(test.distance("white_marlin", "mileage") + " == 23");
        StdOut.println(test.distance("Black_Plague", "black_marlin") + " == 33");
        StdOut.println(test.distance("American_water_spaniel", "histology") + " == 27");
        StdOut.println(test.distance("Brown_Swiss", "barrel_roll") + " == 29");
    }
}

package ru.sinitsynme.airportsearch.prefixTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PrefixTree {

    private final List<PrefixTree> nextSymbols = new ArrayList<>();

    private char symbol;

    private final List<Integer> matchedIds = new ArrayList<>();

    //    // Result is a found in file phrase.
//    private boolean isResult;
//
//    private int resultIndexInRow;

    public PrefixTree(){
    }

    public PrefixTree(Map<Integer, String> wordsIdsMap){
        addMultipleData(wordsIdsMap);
    }

    public char getSymbol() {
        return symbol;
    }

    public Integer getMatchedRowsSize() {
        return matchedIds.size();
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void addMultipleData(Map<Integer, String> wordsIdsMap){
        for (Map.Entry<Integer, String> entry : wordsIdsMap.entrySet()){
            add(entry.getValue(), entry.getKey());
        }
    }

    public void add(String word, int id){
        word = word.toLowerCase();
        int index = 0;

        PrefixTree node = this;

        while (index < word.length()){

            boolean isFoundMatch = false;

            for (PrefixTree candidate : node.nextSymbols){

                if (word.charAt(index) == candidate.getSymbol()){

                    isFoundMatch = true;
                    node = candidate;

                    break;
                }
            }

            if (!isFoundMatch) {
                PrefixTree newNode = new PrefixTree();
                newNode.symbol = word.charAt(index);
                node.nextSymbols.add(newNode);
                node = newNode;
            }

            node.matchedIds.add(id);

            index++;
        }
    }

    public List<Integer> searchIdsStartingByString(String query){

        query = query.toLowerCase();
        int index = 0;

        PrefixTree node = this;

        while (index < query.length()){

            boolean isFoundMatch = false;

            for (PrefixTree candidate : node.nextSymbols){
                if (query.charAt(index) == candidate.getSymbol()) {

                    isFoundMatch = true;
                    node = candidate;

                    break;
                }
            }

            if (!isFoundMatch){
                return Collections.emptyList();
            }

            index++;
        }

        return node.matchedIds;
    }

}

package ru.sinitsynme.airportsearch.prefixTree;

import java.util.*;

public class PrefixTree {

    private final List<PrefixTree> nextSymbols = new LinkedList<>();

    private char symbol;

    private int phraseId;

    public PrefixTree() {
    }

    public PrefixTree(Map<Integer, String> wordsIdsMap) {
        addMultipleData(wordsIdsMap);
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void addMultipleData(Map<Integer, String> wordsIdsMap) {
        for (Map.Entry<Integer, String> entry : wordsIdsMap.entrySet()) {
            add(entry.getValue(), entry.getKey());
        }
    }

    public void add(String word, int id) {
        word = word.toLowerCase();
        int index = 0;

        PrefixTree node = this;
        boolean isFoundMatch = false;

        while (index < word.length()) {

            isFoundMatch = false;

            for (PrefixTree candidate : node.nextSymbols) {

                if (word.charAt(index) == candidate.getSymbol()) {

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

            index++;
        }

        PrefixTree newNode = new PrefixTree();
        newNode.symbol = '$';
        node.nextSymbols.add(newNode);
        node = newNode;

        node.phraseId = id;
    }

    public List<Integer> searchIdsStartingByString(String query) {

        query = query.toLowerCase();
        int index = 0;

        PrefixTree node = this;

        while (index < query.length()) {

            boolean isFoundMatch = false;

            for (PrefixTree candidate : node.nextSymbols) {
                if (query.charAt(index) == candidate.getSymbol()) {

                    isFoundMatch = true;
                    node = candidate;

                    break;
                }
            }

            if (!isFoundMatch) {
                return Collections.emptyList();
            }

            index++;
        }

        return searchIdsStartingFromNode(node);
    }

    private static List<Integer> searchIdsStartingFromNode(PrefixTree node) {
        List<Integer> matchedIds = new ArrayList<>();

        Queue<PrefixTree> unsearchedNodes = new LinkedList<>();
        unsearchedNodes.add(node);

        while (!unsearchedNodes.isEmpty()) {
            PrefixTree currentNode = unsearchedNodes.poll();

            for (PrefixTree child : currentNode.nextSymbols) {
                if (child.getSymbol() == '$') {
                    matchedIds.add(child.phraseId);
                }
            }

            unsearchedNodes.addAll(currentNode.nextSymbols);
        }

        return matchedIds;

    }

}

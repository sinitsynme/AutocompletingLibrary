package ru.sinitsynme.airportsearch.prefixTree;

import java.util.*;

public class PrefixTree {

    private final List<PrefixTree> nextSymbols = new LinkedList<>();

    private char symbol;

    private int phraseId;

    private static final char END_OF_PHRASE_SYMBOL = '$';

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
        boolean isFoundMatch;

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
                PrefixTree nextSymbolNode = new PrefixTree();
                nextSymbolNode.symbol = word.charAt(index);
                node.nextSymbols.add(nextSymbolNode);
                node = nextSymbolNode;
            }

            index++;
        }

        addEndSymbol(id, node);
    }

    public List<Integer> searchIdsByPrefix(String prefix) {

        prefix = prefix.toLowerCase();
        int index = 0;

        PrefixTree node = this;

        while (index < prefix.length()) {

            boolean isFoundMatch = false;

            for (PrefixTree candidate : node.nextSymbols) {
                if (prefix.charAt(index) == candidate.getSymbol()) {

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
                if (child.getSymbol() == END_OF_PHRASE_SYMBOL) {
                    matchedIds.add(child.phraseId);
                }
            }

            unsearchedNodes.addAll(currentNode.nextSymbols);
        }

        return matchedIds;

    }

    private static void addEndSymbol(int id, PrefixTree node) {
        PrefixTree endOfWordNode = new PrefixTree();
        endOfWordNode.symbol = END_OF_PHRASE_SYMBOL;
        node.nextSymbols.add(endOfWordNode);
        node = endOfWordNode;

        node.phraseId = id;
    }

}

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    int wordLength = DEFAULT_WORD_LENGTH;

    ArrayList<String> wordList = new ArrayList<String>();
    HashSet<String> wordSet = new HashSet<String>();
    HashMap<String, ArrayList<String>> lettersToWord = new HashMap<String, ArrayList<String>>();
    HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<Integer, ArrayList<String>>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();

            wordSet.add(word);
            wordList.add(word);

            ArrayList<String> temp_words1 = new ArrayList<String>();

            ArrayList<String> temp_words2 = new ArrayList<String>();

            int l = word.length();

            if (sizeToWords.containsKey(l)) {
                temp_words2 = sizeToWords.get(l);
                temp_words2.add(word);
                sizeToWords.put(l, temp_words2);
            } else {
                temp_words2.add(word);
                sizeToWords.put(l, temp_words2);
            }

            String sortWord;
            sortWord = alphabeticalOrder(word);

            if (lettersToWord.containsKey(sortWord)) {

                temp_words1 = lettersToWord.get(sortWord);
                temp_words1.add(word);
            } else {
                temp_words1.add(word);
                lettersToWord.put(sortWord, temp_words1);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {

        if (wordSet.contains(word) && !word.contains(base))
            return true;

        else
            return false;
    }


    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {

        ArrayList<String> result = new ArrayList<String>();

        String sortedTargetWord = alphabeticalOrder(word);
        String newWord;

        for (char c = 'a'; c <= 'z'; c++) {
            newWord = c + word;
            newWord = alphabeticalOrder(newWord);

            if (lettersToWord.containsKey(newWord)) {
                result.addAll(lettersToWord.get(newWord));
            }

        }
        for (int i = 0; i < result.size(); i++) {
            Log.d("AD List", result.get(i));
            if (isGoodWord(result.get(i), word)) {
                result.remove(i);
            }
        }

        return result;
    }


    public String pickGoodStarterWord() {

        String word = new String();
        int j;

        ArrayList<String> lengthWords = new ArrayList<>();

        if (wordLength <= MAX_WORD_LENGTH) {
            lengthWords = sizeToWords.get(wordLength);
        }

        int i = random.nextInt(lengthWords.size());

        for (j = i; j < lengthWords.size(); j++) {
            if (getAnagramsWithOneMoreLetter(lengthWords.get(j)).size() >= MIN_NUM_ANAGRAMS) {
                word = lengthWords.get(j);
                break;
            }
        }

        if (j == lengthWords.size() && word == null) {

            for (j = 0; j < i; j++) {
                if (getAnagramsWithOneMoreLetter(lengthWords.get(j)).size() >= MIN_NUM_ANAGRAMS) {
                    word = lengthWords.get(j);
                    break;
                }
            }

        }
        return word;
    }


    public String alphabeticalOrder(String word) {
        char[] letters = word.toCharArray();
        Arrays.sort(letters);
        String sortedLetters = letters.toString();
        return sortedLetters;
    }

}
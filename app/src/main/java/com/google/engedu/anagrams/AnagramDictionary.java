package com.google.engedu.anagrams;

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
    private static final int MAX_WORD_LENGTH = 6;
    private Random random = new Random();
    private HashSet wordSet;
    private ArrayList wordList;
    private HashMap lettersToWord;
    private String key;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        wordSet = new HashSet();
        wordList = new ArrayList();
        lettersToWord = new HashMap();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            ArrayList al = new ArrayList();
            key = alphabeticalOrder(word);
            if(!lettersToWord.containsKey(key)){
                al.add(word);
                lettersToWord.put(key,al);
            }
            else{
                al = (ArrayList) lettersToWord.get(key);
                al.add(word);
                lettersToWord.put(key, al);
            }
        }
    }

    public String alphabeticalOrder(String word){

        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);

        return key;
    }
    public boolean isGoodWord(String word, String base) {
        boolean check = true;
        if (wordSet.contains(word) && !base.contains(word))
            return true;
        else
            return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> resultant;
        ArrayList<String> result = new ArrayList<String>();
        for(char alphabet = 'a'; alphabet <= 'z';alphabet++) {
            String newWord = word + alphabet;
            String extendedKey = alphabeticalOrder(newWord);
            if(lettersToWord.containsKey(extendedKey) ){
                resultant = new ArrayList();
                resultant = (ArrayList) lettersToWord.get(extendedKey);
                for(int i = 0;i< resultant.size();i++)
                    result.add(String.valueOf(resultant.get(i)));
            }
        }
//            if(lettersToWord.containsKey(extendedKey) && isGoodWord(word,newWord)){
//                resultant = new ArrayList();
//                resultant = (ArrayList) lettersToWord.get(extendedKey);
//                for(int i = 0;i< resultant.size();i++)
//                    result.add(String.valueOf(resultant.get(i)));
//            }

        return result;
    }

    public String pickGoodStarterWord() {

        int num;
        String randomWord = null;
        ArrayList res;
        boolean goOn = true;
        while(goOn) {
            num = random.nextInt(9000) + 1;
            randomWord = (String) wordList.get(num);
            res = new ArrayList();
            res = (ArrayList) lettersToWord.get(alphabeticalOrder(randomWord));
            if(res.size() >= MIN_NUM_ANAGRAMS && randomWord.length() > MAX_WORD_LENGTH){
                goOn = false;

            }
        }
        return randomWord;
    }
}

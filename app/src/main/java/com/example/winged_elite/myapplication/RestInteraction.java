package com.example.winged_elite.myapplication;

/**
 * Created by winged_elite on 23/4/16.
 */
public class RestInteraction {
    static String wordComplete(String partialWord) {
        String url = "http://52.160.99.213:5000/word_completion";
        String completeWord = restInteract(url, partialWord);
        return completeWord;
    }

    static String[] textSentimentInteract(String text) {
        String url = "http://52.160.99.213:5000/text_sentiment";
        String returnedText = restInteract(url, text);
        int separator = returnedText.indexOf(":::");
        String result[] = new String[2];
        result[0] = returnedText.substring(0, separator);   //sentiment
        result[1] = returnedText.substring(separator+3);    //colour
        return result;
    }

    static String sentenceComplete(String partialSentence) {
        String url = "http://52.160.99.213:5000/sentence_completion";
        String completeSentence = restInteract(url, partialSentence);
        return completeSentence;
    }

    static String[] imageSentimentInteract(String imageBinary) {
        String url = "http://52.160.99.213:5000/image_sentiment";
        String returnedText = restInteract(url, imageBinary);
        int separator = returnedText.indexOf(":::");
        String result[] = new String[2];
        result[0] = returnedText.substring(0, separator);   //sentiment
        result[1] = returnedText.substring(separator+3);    //colour
        return result;
    }

    static String restInteract(String url, String parameter) {
        return "";
    }
}

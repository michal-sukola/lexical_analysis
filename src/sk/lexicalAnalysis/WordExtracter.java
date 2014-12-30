/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.lexicalAnalysis;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

/**
 *
 * @author User
 */
public class WordExtracter {
    Document inputDocument;
    public final String wordPattern = "[a-zA-ZäöüÄÖÜßàáâåÀÁÂÃæÆçÇêéëèÊËÉÈïíîìÍÌÎÏñÑœŒôòõóøÓÔÕØÒšŠúûùÙÚÛÿŸýÝžŽªÞþƒßµðÐ]+";

    public WordExtracter(String fileName) {
        inputDocument = new Document(new File(fileName), fileName);
    }
    
    /**
     *
     * @return
     */
    public List<WordString> getWordList() {
        List<WordString> wordList = new ArrayList<>();
        String text = inputDocument.getContents();
        
        Matcher m = (Pattern.compile(wordPattern)).matcher(text);
        while(m.find()) {
            String wordLabel = m.group();
            //System.out.println("Word: \"" + word + "\"\tSurrounded by: \"" + text.substring(Math.max(0, m.start() - 20), Math.min(m.end() + 20, text.length())) + "\"");
            WordString word = new WordString(wordLabel);
            
            // build context
            
            // get previous word
            int lastIndex = wordList.size() - 1;
            WordString lastWord = (lastIndex > 0 ) ? wordList.get(lastIndex) : null;            
            
            Context c = new Context(inputDocument, m.start(), m.end(), lastWord, word);
            
            word.addContext(c);
            
            
            
            // now add new word
            wordList.add(word);
        }        
        
        return wordList;
    }
    
    public static final String RESULTS_FILE = "WUD-cut-slova-kontext.txt";
    
    public static void clearWordStringsFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(RESULTS_FILE);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordExtracter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
    
    public static void saveWordStringsToFile(String line, String fileName) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(fileName, true)));
            pw.println(line);
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordExtracter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
        
    }
    
    
    public static void main(String[] args) {
        String fileName = ""; 
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileName = fc.getSelectedFile().getAbsolutePath();
        } else {
            System.exit(0);
        }        
        
        String outFileName = fileName + "_analysis.txt";
        
        clearWordStringsFile();
        
        
        WordExtracter wordExtracter = new WordExtracter(fileName);
        
        List<WordString> wordList = wordExtracter.getWordList();
        
        Collections.sort(wordList);
        
        for (WordString wordString : wordList) {
            System.out.println(wordString);    
            saveWordStringsToFile(wordString.toString(), outFileName);
        }
        
        

    }
    
    
}

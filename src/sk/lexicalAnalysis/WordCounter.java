/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.lexicalAnalysis;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import static sk.lexicalAnalysis.WordExtracter.RESULTS_FILE;

/**
 *
 * @author User
 */
public class WordCounter {
    
    HashMap<String, Integer> wordCounts;

    public WordCounter(List<WordString> words) {
        wordCounts = new HashMap<>(words.size());
        
        for(WordString word: words) {
            if(wordCounts.keySet().contains(word.getLabel())) {
                //System.out.println(word.getLabel());
                Integer count = wordCounts.get(word.getLabel());
                wordCounts.put(word.getLabel(), count+1);
            } else {                
                wordCounts.put(word.getLabel(), 1);
            }            
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(500);
        //sb.append("Words,Count\n=============================\n");
        
        for(String wordLabel: wordCounts.keySet()) {
            int count = wordCounts.get(wordLabel);
            sb.append(wordLabel);
            sb.append("\t");
            sb.append(count);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static void saveLineToFile(String line, String fileName) {
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
    
    public static void clearWordStringsFile(String fileName) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordExtracter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
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
       
       WordCounter wc = new WordCounter((new WordExtracter(fileName)).getWordList());
       System.out.println(wc);
       
       String outFileName = fileName + "_counts.txt";
       clearWordStringsFile(outFileName);
       saveLineToFile(wc.toString(), fileName + "_counts.txt");
    }
    
    
    
}

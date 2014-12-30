/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.lexicalAnalysis;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author User
 */
public class Corpus {
    // intialize to empty list
    // to be loaded from db later
    List<Document> sourceDocuments = new LinkedList<>();
    List<Tag> tagList = new LinkedList<>();
    
    private void loadDocument(File file, String description) {
        Document newDoc = new Document(file, description);
        sourceDocuments.add(newDoc);
    }
    
    private void analyseDoc(Document doc) {
        
    }
    
}

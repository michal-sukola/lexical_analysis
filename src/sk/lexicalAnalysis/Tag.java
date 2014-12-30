/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.lexicalAnalysis;

/**
 *
 * @author User
 */
public class Tag {
    private Long id;
    
    private String label;
    private String description;
    private Document parentDocument;

    public Tag(String label, String description) {
        this.id = 0L;
        this.label = label;
        this.description = description;
    } 
    
    public void setParentDocument(Document parentDocument) {
        this.parentDocument = parentDocument;
    }
    
}

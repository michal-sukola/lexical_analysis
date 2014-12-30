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
public class WordString implements Comparable<WordString> {
    private final String label;
    private Context context;

    
    public WordString(String label) {
        this.label = label;
    }

    public Context getContext() {
        return context;
    }
    
      
    public Document getSourceDocument() {
        if(context != null) {
            return context.getSourceDocument();
        }
        
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WordString) {
            WordString wObj = (WordString) obj;            
            return wObj.label.equals(this.label) && wObj.context.equals(this.context);
        } else {
            return false;
        }
    }    
    
    public void addContext(Context context) {
        this.context = context;
    }

    @Override
    public int hashCode() {
        return label.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }    

    public String getLabel() {
        return label;
    }    

    @Override
    public String toString() {
        String result = label;
        
        if(this.context != null) {
            result += context ;
        } else {
            result += "[NULL]";
        }
        
        return result;
    }

    @Override
    public int compareTo(WordString o) {
        int labelComp = o.label.compareTo(this.label);
        
        if(labelComp == 0) {
            return o.context.compareTo(this.context);
        }
        
        return labelComp;
    }
    
    
    
    
}

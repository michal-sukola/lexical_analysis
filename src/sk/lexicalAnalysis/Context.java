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
public class Context implements Comparable<Context> {
    private Integer positionStart;
    private Integer positionEnd;
    private Document sourceDocument;
    
    private WordString previousWord;
    private WordString nextWord;
    private WordString word;
    
    private String contextContent;
    
    public static int PADDING = 200;
    //public static ContextDisplayType DISPLAY_TYPE = ContextDisplayType.SURROUNDING_TEN_WORDS;
    public static ContextDisplayType DISPLAY_TYPE = ContextDisplayType.SURROUNDING_TEN_WORDS;

    public Context(Document sourceDocument, Integer positionStart, Integer positionEnd, WordString previousWord, WordString currentWord) {
        this.positionStart = positionStart;
        this.positionEnd = positionEnd;
        this.sourceDocument = sourceDocument;
        this.previousWord = previousWord;
        this.word = currentWord;
        
        // link back previous to current
        if(this.previousWord != null) {
            Context previousContext = this.previousWord.getContext();
            if(previousContext != null) {
                previousContext.nextWord = word;
            }
        }
        
        Integer start =  Math.max(0, positionStart - Context.PADDING);
        Integer end = Math.min(positionEnd + Context.PADDING, sourceDocument.getContents().length());
        //this.contextContent = sourceDocument.getContents().substring(start, end);
        
        this.contextContent  = sourceDocument.getContents().substring(start, positionStart);        
        this.contextContent += "###" + sourceDocument.getContents().substring(positionStart,positionEnd) + "###";
        this.contextContent += sourceDocument.getContents().substring(positionEnd,end);
    }   
    
    public int getLineNumber() {
        int numLineChars = 0;
        String sourceDocText = this.sourceDocument.getContents();
        for(int i = 0; i<positionStart; i++) {
            if(sourceDocText.charAt(i) == '\n') {
                numLineChars++;
            }
        }
        // start from 1
        return numLineChars + 1;
    }

    public Document getSourceDocument() {
        return sourceDocument;
    }    
    /**
     * Looks up specified character(s) in the string, if backward parameter is true then it looks up back
     * 
     * @param where
     * @param backwards
     * @param startPosition
     * @param what
     * @return 
     */
    private static int findCharPos(String where, boolean backwards, int startPosition, char... what ) {
        boolean found = false;
        int pos = startPosition;
        
        if(!backwards) {
            pos = pos - 1 ; // if we are going forward then the start position is already after
        }
        
        while(!found) {
            if(backwards && (pos <= 0) ) {
                return -1;
            }
            
            if(!backwards && (pos >= where.length() - 1)) {
                return where.length();
            }
            
            
            if(!found) { 
                pos += backwards ? -1 : +1;
            } 
            
            char curChar = where.charAt(pos);
                        
            for(int i=0; i<what.length; i++) {
                if(curChar == what[i]) {
                    found = true;
                    break;
                }
            }
            
        }
       
        return Math.min(Math.max(0, pos), where.length() - 1);
    }
    
    final int NUM_WORDS = 10;
    
    private String getNwords(int numWords, boolean before) {
        String contextBeforeAfter = "";
        WordString wordIter = null;
        
        if(before) {
            wordIter = this.previousWord;
        } else {
            wordIter = this.nextWord;
        }
        WordString lastIterated = wordIter;

        for(int i = 1; i<=numWords - 1; i++) { // -1 because we are already shifted before cycle
           if(wordIter == null) {
               break;
           }
           
           lastIterated = wordIter;
           
           if(before) {
                //contextBeforeAfter =  wordIter.getLabel() + " " + contextBeforeAfter;
                wordIter = wordIter.getContext().previousWord;
           } else {
                //contextBeforeAfter =  contextBeforeAfter + " " + wordIter.getLabel();
                wordIter = wordIter.getContext().nextWord;
           }
        }
        
        int from = 0;
        int to = 0;
        
        if(wordIter != null) {
            // BLABLA BLOBLO currentWord = BLABLA BLOBLO 
            if(before) {
                from = wordIter.getContext().positionStart;
                to = word.getContext().positionStart;
            } else { // currentWord BLE BLA BLI
                from = word.getContext().positionEnd;
                to = wordIter.getContext().positionEnd;
            }
        } else {
            if(lastIterated == null) {
                    return "";
            }
            
            if(before) { // if we reached begining, so there is less than N words from current word
                //System.out.println("TEST==========");
                from = lastIterated.getContext().positionStart;
                to = word.getContext().positionStart;
            } else { // if we reached end, so there is less than N words from current word
                from = word.getContext().positionEnd;
                to = lastIterated.getContext().positionEnd;
            }
            //return "";
        }
        
        contextBeforeAfter = this.sourceDocument.getContents().substring(from,to);
        
        return contextBeforeAfter.replace("\r", "").replace("\n"," ").trim();
    }
 
    @Override
    public String toString() {
        
        int lineNum = getLineNumber();
        
        if(DISPLAY_TYPE.equals(ContextDisplayType.SURROUNDING)) {
            return positionStart + "###" + contextContent.replaceAll("\r", "").replaceAll("\n", " ") + "###";
        }
        
        if(DISPLAY_TYPE.equals(ContextDisplayType.SURROUNDING_TEN_WORDS)) {
            String contextText = "###" + lineNum + "###";
            contextText += this.getNwords(NUM_WORDS, true) + "###";
            contextText += this.word.getLabel() + "###";
            contextText += this.getNwords(NUM_WORDS, false) + "###";           
            
            return contextText;
        }
        
        if(DISPLAY_TYPE.equals(ContextDisplayType.TO_NEAREST_DOT)) {
            // find where last comma or dot is
            int previousDelimiterPos = findCharPos(sourceDocument.getContents(), true, positionStart, '.',';');
            int nextDelimiterPos = findCharPos(sourceDocument.getContents(), false, positionEnd, '.',';');
            
            String sourceDocText = sourceDocument.getContents();
            
            String delimitedContextBefore = sourceDocText.substring(previousDelimiterPos+1,positionStart).trim();
            //System.out.println(sourceDocText.substring(positionStart,positionEnd));
            //System.out.println("positionEnd:" + positionEnd + ", nextDelPos:" + nextDelimiterPos + ", textLength:"+sourceDocText.length());
            String delimitedContextAfter = (positionEnd == nextDelimiterPos) ? "" : sourceDocText.substring(positionEnd, nextDelimiterPos).trim();
            //System.out.println(positionEnd + " " + (nextDelimiterPos - 1) + "" + sourceDocText.charAt(nextDelimiterPos - 1));
            String contextText = "###" + lineNum + "###";
            contextText += delimitedContextBefore.replaceAll("\r", "").replaceAll("\n", " ") + "###";
            contextText += sourceDocText.substring(positionStart,positionEnd) + "###";
            contextText += delimitedContextAfter.replaceAll("\r", "").replaceAll("\n", " ") + "###";
            
            return contextText;
        }
        
        return lineNum + "[" + positionStart + "," + positionEnd + "]" + "###" + contextContent.replaceAll("\r", "").replaceAll("\n", " ") + "###";
        
       
    }    

    @Override
    public int compareTo(Context o) {
        return o.positionStart.compareTo(this.positionStart);
    }
}

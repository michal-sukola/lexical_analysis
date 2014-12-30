/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.lexicalAnalysis;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Document {
    private File file;
    private String description;
    private String contents;
    
    Date timeCreated = new Date();

    public Document(File file, String description) {
        this.file = file;
        this.description = description;
    }   
    
    public void loadContents() {
        try {
            byte[] encodedFileContent = Files.readAllBytes(file.toPath());
            contents = Charset.defaultCharset().decode(ByteBuffer.wrap(encodedFileContent)).toString();
        } catch (IOException ex) {
            Logger.getLogger(WordExtracter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    public String getContents() {
        if(contents == null) {
            loadContents();
        }
        
        return contents;
    }
    
    
}

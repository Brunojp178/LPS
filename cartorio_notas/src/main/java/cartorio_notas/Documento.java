/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartorio_notas;

import org.bson.Document;

/**
 *
 * @author Bruno
 */
public class Documento {
    private int id;
    
    // NOSQL não implementado ainda!
    public Documento(int id){
        this.id = id;
    }
    
    public Document toDocument(){
        Document doc = new Document("_id", id);
        return doc;
    }
            
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartorio_notas;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import javax.swing.JOptionPane;


/**
 *
 * @author Bruno
 */
public class Database_control {
    
    private static Database_control instance = null;
    private static MongoClient mongoClient;
    private MongoDatabase database;
    
    private Database_control(){
        // define atributtes here
        try{
            // Connect to the database on localhost
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            database = mongoClient.getDatabase("LPS");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Não foi possivel conectar ao banco de dados!\nErro: " + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static Database_control getInstance(){
        
        if(instance == null) {
            instance = new Database_control();
        }
        return instance;
    }
    
    public MongoDatabase getDatabase(){
        return database;
    }
    
    public void closeConnection(){
        mongoClient.close();
    }
}

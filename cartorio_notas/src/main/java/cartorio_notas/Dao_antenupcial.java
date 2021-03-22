package cartorio_notas;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.bson.Document;

/**
 *
 * @author Bruno
 */
public class Dao_antenupcial {
    
    private static Dao_antenupcial instancia = null;
    private final MongoCollection<Document> database_funcionarios;
    private final MongoCollection<Document> database_antenupcial;
    
    private Dao_antenupcial(){
        database_funcionarios = Database_control.getInstance().getDatabase().getCollection("funcionarios");
        database_antenupcial = Database_control.getInstance().getDatabase().getCollection("antenupcial");
    }
    
    public static Dao_antenupcial getInstance(){
        if(instancia == null){
            instancia = new Dao_antenupcial();
        }
        return instancia;
    }
    
    public int verifica_id(){
        Document doc;
        int id = -1;
        MongoCursor<Document> cursor = database_antenupcial.find().iterator();
        try {
            while (cursor.hasNext()) {
                doc = cursor.next();
                System.out.println(doc.toJson());
                if(id < doc.getInteger("_id")){
                    id = doc.getInteger("_id") + 1;
                    System.out.println("verfica_id: id disponivel " + id);
                }
            }
            return id;
        } finally {
            cursor.close();
            if(id == -1) return 0;
        }
    }
            
    public ArrayList<Antenupcial> carregar_collection() throws Exception{
        Antenupcial t;
        ArrayList<Antenupcial> antenupciais = new ArrayList<Antenupcial>();
        MongoCursor<Document> cursor = database_antenupcial.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
                t = new Antenupcial(doc);
                antenupciais.add(t);
            }
        } finally {
            cursor.close();
        }
        return antenupciais;
    }
    
    public void inserir_document(Antenupcial antenupcial, int opc) throws Exception{
        Document Antenupcial_doc = antenupcial.toDocument();
        if(opc == 0){
            database_antenupcial.insertOne(Antenupcial_doc);
            JOptionPane.showMessageDialog(null, "Registrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }
        if(opc == 1){
            database_antenupcial.updateOne(Filters.eq("_id", antenupcial.getId()), Updates.set("Cpf_noiva", antenupcial.getCpf_noiva()));
            database_antenupcial.updateOne(Filters.eq("_id", antenupcial.getId()), Updates.set("Cpf_noivo", antenupcial.getCpf_noivo()));
            database_antenupcial.updateOne(Filters.eq("_id", antenupcial.getId()), Updates.set("Data", antenupcial.getData()));
            database_antenupcial.updateOne(Filters.eq("_id", antenupcial.getId()), Updates.set("id_funcionario", antenupcial.getId_funcionario()));
            JOptionPane.showMessageDialog(null, "Editado com sucesso!", "Edição", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void deletar_document(Antenupcial delete) throws Exception{
        database_antenupcial.deleteOne(Filters.eq("_id", delete.getId()));
    }
}

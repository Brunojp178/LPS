package cartorio_notas;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.awt.HeadlessException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.bson.Document;

/**
 *
 * @author Bruno
 */
public class Dao_testamento {
    private static Dao_testamento instancia = null;
    private final MongoCollection<Document> database_funcionarios;
    private final MongoCollection<Document> database_testamentos;
    
    private Dao_testamento(){
        database_funcionarios = Database_control.getInstance().getDatabase().getCollection("funcionarios");
        database_testamentos = Database_control.getInstance().getDatabase().getCollection("testamentos");
    }
    
    //TODO Retornar arquivos do bd em arraylist para a view, Pegar dados da view como um model e salvar no bd daq
    public static Dao_testamento getInstance(){
        if(instancia == null){
            instancia = new Dao_testamento();
        }
        return instancia;
    }
    
    public int verifica_id(){
        Document doc;
        int id = -1;
        MongoCursor<Document> cursor = database_testamentos.find().iterator();
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
            
    public ArrayList<Testamento> carregar_collection(){
        Testamento t;
        ArrayList<Testamento> testamentos = new ArrayList<Testamento>();
        MongoCursor<Document> cursor = database_testamentos.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
                t = new Testamento(doc);
                testamentos.add(t);
            }
        } finally {
            cursor.close();
        }
        return testamentos;
    }
    
    public void inserir_document(Testamento testamento, int opc) throws Exception{
        Document testamento_doc = testamento.toDocument();
        if(opc == 0){
            database_testamentos.insertOne(testamento_doc);
            JOptionPane.showMessageDialog(null, "Registrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }
        if(opc == 1){
            database_testamentos.updateOne(Filters.eq("_id", testamento.getId()), Updates.set("Cpf_testador", testamento.getCpf_testador()));
            database_testamentos.updateOne(Filters.eq("_id", testamento.getId()), Updates.set("Cpf_testemunha", testamento.getCpf_testemunha()));
            database_testamentos.updateOne(Filters.eq("_id", testamento.getId()), Updates.set("id_funcionario", testamento.getId_funcionario()));
            database_testamentos.updateOne(Filters.eq("_id", testamento.getId()), Updates.set("Data", testamento.getData()));
            JOptionPane.showMessageDialog(null, "Editado com sucesso!", "Edição", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void deletar_document(Testamento delete) throws Exception{
        database_testamentos.deleteOne(Filters.eq("_id", delete.getId()));
    }
}

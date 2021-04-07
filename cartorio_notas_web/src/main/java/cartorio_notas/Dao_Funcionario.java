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
public class Dao_Funcionario {
    
    private final MongoCollection<Document> database_funcionarios;
    private static Dao_Funcionario instancia = null;
    
    private Dao_Funcionario(){
        database_funcionarios = Database_control.getInstance().getDatabase().getCollection("funcionarios");
    }
    
    public static Dao_Funcionario getInstance(){
        if(instancia == null) instancia = new Dao_Funcionario();
        return instancia;
    }
    
    public int verifica_id(){
        
        Document doc;
        int id = -1;
        MongoCursor<Document> cursor = database_funcionarios.find().iterator();
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
    
    public ArrayList<Funcionario> carregar_collection() throws Exception{
        Funcionario t;
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        MongoCursor<Document> cursor = database_funcionarios.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
                t = new Funcionario(doc);
                funcionarios.add(t);
            }
        } finally {
            cursor.close();
        }
        return funcionarios;
    }
    
    public void inserir_document(Funcionario funcionario, int opc) throws Exception{
        
        Document doc = funcionario.toDocument();

        if(opc == 0){
            database_funcionarios.insertOne(doc);
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }
        if(opc == 1){
            database_funcionarios.updateOne(Filters.eq("_id", funcionario.getId()), Updates.set("Nome", funcionario.getNome()));
            database_funcionarios.updateOne(Filters.eq("_id", funcionario.getId()), Updates.set("Cpf", funcionario.getCpf()));
            database_funcionarios.updateOne(Filters.eq("_id", funcionario.getId()), Updates.set("Email", funcionario.getEmail()));
            database_funcionarios.updateOne(Filters.eq("_id", funcionario.getId()), Updates.set("Nivel", funcionario.getNivel()));
            database_funcionarios.updateOne(Filters.eq("_id", funcionario.getId()), Updates.set("Senha", String.valueOf(funcionario.getSenha())));
            JOptionPane.showMessageDialog(null, "Editado com sucesso!", "Edição", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void deletar_document(Funcionario deletar) throws Exception{
        database_funcionarios.deleteOne(Filters.eq("_id", deletar.getId()));
    }
}

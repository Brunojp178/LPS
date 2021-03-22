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
public class Dao_procuracao {
    
    private static Dao_procuracao instancia = null;
    private final MongoCollection<Document> database_procuracao;
    
    private Dao_procuracao(){
        database_procuracao = Database_control.getInstance().getDatabase().getCollection("procuracao");
        
    }
    
    public static Dao_procuracao getInstance(){
        if(instancia == null) instancia = new Dao_procuracao();
        return instancia;
    }
    
    public int verifica_id(){
        Document doc;
        int id = -1;
        MongoCursor<Document> cursor = database_procuracao.find().iterator();
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
    
    public ArrayList<Procuracao> carregar_collection(){
        Procuracao p;
        MongoCursor<Document> cursor = database_procuracao.find().iterator();
        ArrayList<Procuracao> procuracoes = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
                p = new Procuracao(doc);
                procuracoes.add(p);
            }
        } finally {
            cursor.close();
        }
        return procuracoes;
    }
    
    public void inserir_document(Procuracao procuracao, int opc) throws Exception{
        
        Document doc = procuracao.toDocument();

        if(opc == 0){
            database_procuracao.insertOne(doc);
            JOptionPane.showMessageDialog(null, "Registrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
        }
        if(opc == 1){
            database_procuracao.updateOne(Filters.eq("_id", procuracao.getId()), Updates.set("Cpf_mandante", procuracao.getCpf_mandante()));
            database_procuracao.updateOne(Filters.eq("_id", procuracao.getId()), Updates.set("Cpf_mandatario", procuracao.getCpf_mandatario()));
            database_procuracao.updateOne(Filters.eq("_id", procuracao.getId()), Updates.set("Data", procuracao.getData()));
            JOptionPane.showMessageDialog(null, "Editado com sucesso!", "Edição", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void deletar_document(Procuracao deletar){
        database_procuracao.deleteOne(Filters.eq("_id", deletar.getId()));
    }
}

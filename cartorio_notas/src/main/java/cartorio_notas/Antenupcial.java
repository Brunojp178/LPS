/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartorio_notas;

import java.util.Date;
import org.bson.Document;

/**
 *
 * @author Bruno
 */
public class Antenupcial extends Documento{
    
    private String cpf_noivo;
    private String cpf_noiva;
    private Date data;
    private int id_funcionario;
    
    // TODO Fazer o atributo id_funcionario ser tipo Funcionario para armazenamento
    // no banco de dados.
    
    /**
     * @param id
     * @param id_funcionario
     * @param cpf_noivo
     * @param cpf_noiva
     * @param data 
     */
    public Antenupcial(int id, int id_funcionario, String cpf_noivo, String cpf_noiva, Date data){
        super(id);
        this.id_funcionario = id_funcionario;
        this.cpf_noiva = cpf_noiva;
        this.cpf_noivo = cpf_noivo;
        this.data = data;
    }
    
    public Antenupcial(Document doc){
        super(doc.get("_id", Integer.class));
        this.cpf_noiva = doc.get("Cpf_noiva", String.class);
        this.cpf_noivo = doc.get("Cpf_noivo", String.class);
        this.data = doc.get("Data", Date.class);
        this.id_funcionario = doc.get("id_funcionario", Integer.class);
    }
    
    @Override
    public Document toDocument(){
        Document doc = new Document("_id", this.getId())
                .append("Cpf_noiva", this.getCpf_noiva())
                .append("Cpf_noivo", this.getCpf_noivo())
                .append("Data", this.getData())
                .append("id_funcionario", this.getId_funcionario())
                ;
        return doc;
    }    

    /**
     * @return the cpf_noivo
     */
    public String getCpf_noivo() {
        return cpf_noivo;
    }

    /**
     * @return the cpf_noiva
     */
    public String getCpf_noiva() {
        return cpf_noiva;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @return the id_funcionario
     */
    public int getId_funcionario() {
        return id_funcionario;
    }

    /**
     * @param cpf_noivo the cpf_noivo to set
     */
    public void setCpf_noivo(String cpf_noivo) {
        this.cpf_noivo = cpf_noivo;
    }

    /**
     * @param cpf_noiva the cpf_noiva to set
     */
    public void setCpf_noiva(String cpf_noiva) {
        this.cpf_noiva = cpf_noiva;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @param id_funcionario the id_funcionario to set
     */
    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }
}

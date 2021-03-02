/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartorio_notas;

import java.util.Date;

/**
 *
 * @author Bruno
 */
public class Procuracao extends Documento{
    
    private String cpf_mandante;
    private String cpf_mandatario;
    private Date data;
    
    /**
     * 
     * @param id
     * @param cpf_mandante
     * @param cpf_mandatario
     * @param data 
     */
    public Procuracao(int id, String cpf_mandante, String cpf_mandatario, Date data){
        super(id);
        this.cpf_mandante = cpf_mandante;
        this.cpf_mandatario = cpf_mandatario;
        this.data = data;
    }

    /**
     * @return the cpf_mandante
     */
    public String getCpf_mandante() {
        return cpf_mandante;
    }

    /**
     * @return the cpf_mandatario
     */
    public String getCpf_mandatario() {
        return cpf_mandatario;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param cpf_mandante the cpf_mandante to set
     */
    public void setCpf_mandante(String cpf_mandante) {
        this.cpf_mandante = cpf_mandante;
    }

    /**
     * @param cpf_mandatario the cpf_mandatario to set
     */
    public void setCpf_mandatario(String cpf_mandatario) {
        this.cpf_mandatario = cpf_mandatario;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }
}

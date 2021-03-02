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
public class Antenupcial extends Documento{
    
    private String cpf_noivo;
    private String cpf_noiva;
    private Date data;
    private int id_funcionario;
    
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

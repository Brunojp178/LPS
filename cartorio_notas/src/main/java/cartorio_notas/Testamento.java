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
public class Testamento extends Documento{
    private String cpf_testador;
    private String cpf_testemunha;
    private Date data_registro;
    private int id_funcionario;
     
    /**
     * @param id
     * @param cpf_testador
     * @param cpf_testemunha
     * @param data
     * @param id_funcionario 
     */
    public Testamento(int id, String cpf_testador, String cpf_testemunha, Date data, int id_funcionario){
        super(id);
        this.id_funcionario = id_funcionario;
        this.cpf_testador = cpf_testador;
        this.cpf_testemunha = cpf_testemunha;
        this.data_registro = data;
    }    
     
    /**
     * @return the cpf_testador
     */
    public String getCpf_testador() {
        return cpf_testador;
    }

    /**
     * @return the cpf_testemunha
     */
    public String getCpf_testemunha() {
        return cpf_testemunha;
    }

    /**
     * @return the data_registro
     */
    public Date getData() {
        return data_registro;
    }

    /**
     * @return the id_funcionario
     */
    public int getId_funcionario() {
        return id_funcionario;
    }

    /**
     * @param cpf_testador the cpf_testador to set
     */
    public void setCpf_testador(String cpf_testador) {
        this.cpf_testador = cpf_testador;
    }

    /**
     * @param cpf_testemunha the cpf_testemunha to set
     */
    public void setCpf_testemunha(String cpf_testemunha) {
        this.cpf_testemunha = cpf_testemunha;
    }

    /**
     * @param data_registro the data_registro to set
     */
    public void setData(Date data_registro) {
        this.data_registro = data_registro;
    }

    /**
     * @param id_funcionario the id_funcionario to set
     */
    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }
     
}

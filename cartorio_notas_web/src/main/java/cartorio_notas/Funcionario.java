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
public class Funcionario {
    
    private int id;
    private String nome;
    private String cpf;
    private String email;
    // TODO Change to enum
    private int nivel;
    private char[] senha;
    
    /***
     * 
     * @param id
     * @param nome
     * @param cpf
     * @param email
     * @param nivel
     * @param senha 
     */
    public Funcionario(int id, String nome, String cpf, String email, int nivel, char[] senha){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.nivel = nivel;
    }
    
    public Funcionario(Document doc){
        this.id = doc.get("_id", Integer.class);
        this.nome = doc.get("Nome", String.class);
        this.cpf = doc.get("Cpf", String.class);
        this.email = doc.get("Email", String.class);
        String aux = doc.get("Senha", String.class);
        this.senha = aux.toCharArray();
        this.nivel = doc.get("Nivel", Integer.class);
    }
    
    public Document toDocument(){
        // Converte os números para String por práticidade
        String aux = String.valueOf(senha);
        System.out.println(aux);
        Document doc = new Document("_id", this.getId())
                .append("Nome", this.getNome())
                .append("Cpf", this.getCpf())
                .append("Email", this.getEmail())
                .append("Nivel", this.getNivel())
                .append("Senha", aux)
                ;
        return doc;
    }    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }
    
    /**
     * @return the senha
     */
    public char[] getSenha() {
        return senha;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(char[] senha) {
        this.senha = senha;
    }
}

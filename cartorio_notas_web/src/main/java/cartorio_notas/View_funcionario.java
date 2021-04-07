/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartorio_notas;

import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Bruno
 */
public class View_funcionario extends javax.swing.JFrame {
    
// TODO Singleton para cuidar do armazenamento dos arraylists.
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();
    // -1 - nenhum
    // 0 - add
    // 1 - update
    // 2 - delete
    private int opcao = -1;
    private final Controller_funcionario controller = new Controller_funcionario();
    
    public View_funcionario() {
        initComponents();
        init_function();
    }
    
    private void init_function(){
        this.setVisible(true);
        hab_mask();
        hab_campos(false);
        carregar_tabela();
        desab_botoes(opcao);
    }
    
    private void hab_mask(){
        try {
            MaskFormatter mask_cpf = new MaskFormatter("###.###.###-##");
            MaskFormatter mask_nivel = new MaskFormatter("#");
            
            mask_cpf.install(ftxt_cpf);
            mask_nivel.install(ftxt_nivel);

        } catch (ParseException e) {
            System.out.println("Erro ao aplicar mask nos campos!\nerro: " + e);
        }
    }
    
    public void hab_campos(boolean enable){
        for (int i = 0; i < pnl_form.getComponents().length; i++){
            pnl_form.getComponent(i).setEnabled(enable);
        }
        ftxt_id.setEnabled(false);
    }
    
    private void carregar_tabela(){
        
        funcionarios = controller.carregar_tabela();
        
        tb_tabela.removeAll();
        
        String [] colunas = {"Id", "Nome", "Email"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        
        for(int i = 0; i < funcionarios.size(); i++){
            Object [] linha = {funcionarios.get(i).getId(), funcionarios.get(i).getNome(), funcionarios.get(i).getEmail()};
            model.addRow(linha);
        }
        
        tb_tabela.setModel(model);
    }
    
    private void limpar_campos(){
        ftxt_id.setText("");
        txt_nome.setText("");
        txt_email.setText("");
        ftxt_nivel.setText("");
        ftxt_cpf.setText("");
        ptxt_password.setText("");
    }
    
    // Faz a validação dos campos
    private boolean validar_campos(){
                
        String name = txt_nome.getText();
        if(name.isEmpty()){
            JOptionPane.showMessageDialog(this, "Campo \"Nome\" vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String email = txt_email.getText();
        if(email.isEmpty()){
            JOptionPane.showMessageDialog(this, "Campo \"Email\" vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!email.contains("@gmail.com") && !email.contains("@hotmail.com") && !email.contains("@outlook.com")){
            JOptionPane.showMessageDialog(this, "Email invalido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String cpf = ftxt_cpf.getText();
        if(cpf.isEmpty()){
            JOptionPane.showMessageDialog(this, "Campo \"Cpf\" vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        cpf = cpf.replaceAll("\\.", "");
        cpf = cpf.replaceAll("-", "");
        if(!controller.validar_cpf(cpf)){
            JOptionPane.showMessageDialog(this, "Cpf invalido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String nivel_string = ftxt_nivel.getText();
        if(nivel_string.isEmpty()){
            JOptionPane.showMessageDialog(this, "Campo \"Nível\" vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int nivel = Integer.parseInt(nivel_string);
        if(nivel < 0 || nivel > 3) {
            JOptionPane.showMessageDialog(this, "Nível invalido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        char [] senha = ptxt_password.getPassword();
        if(senha.length < 8){
            JOptionPane.showMessageDialog(this, "Senha invalida!\nA senha deve conter 8 caracteres", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private int cadastrar_funcionario(){
        // Recarrega o arraylist e a tabela com a base de dados
        carregar_tabela();
        // Se esta cadastrando e a validação de campos der false, retorna erro.
        if(!validar_campos()) return 1;
        
        // Se n tem nada no banco de dados, id = 0.
        int id;
        if(this.opcao == 0){
            id = controller.verifica_id();
        }else id = Integer.parseInt(ftxt_id.getText());
                
        String nome = txt_nome.getText();
        String email = txt_email.getText();
        
        String cpf = ftxt_cpf.getText();
        char [] senha = ptxt_password.getPassword();
        
        String nivel_string = ftxt_nivel.getText();
        int nivel = Integer.parseInt(nivel_string);
        
        controller.cadastrar_funcionario(id, nome, email, cpf, nivel, senha, opcao);
                
        limpar_campos();
        hab_campos(false);
        return 0;
    }
    
    private int editar_funcionario(int edit_index){
        
        carregar_tabela();
        hab_campos(true);
        // Bloqueia o campo id
        ftxt_id.setEditable(false);
        
        if(edit_index == -1){
            JOptionPane.showMessageDialog(this, "Selecione um item da lista", "Erro", JOptionPane.ERROR_MESSAGE);
            hab_campos(false);
            opcao = -1;
            desab_botoes(opcao);
            return 1;
        }
        try{
            Funcionario func = funcionarios.get(edit_index);
            // Carregar os campos
            ftxt_id.setText(Integer.toString(func.getId()));
            txt_nome.setText(func.getNome());
            txt_email.setText(func.getEmail());
            ftxt_nivel.setText(Integer.toString(func.getNivel()));
            ftxt_cpf.setText(func.getCpf());
                                    
            return 0;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Funcionário não encontrado no banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
    }
    
    private int deletar_funcionario(int delete_index){
        
        if(delete_index == -1){
            JOptionPane.showMessageDialog(this, "Selecione um item da lista", "Erro", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        controller.deletar_funcionario(delete_index);
        carregar_tabela();
        return 0;
    }
    
    private void desab_botoes(int opcao){
        if(opcao == -1){
            btn_add.setEnabled(true);
            btn_delete.setEnabled(true);
            btn_edit.setEnabled(true);
        }
        if(opcao == 0){
            btn_delete.setEnabled(false);
            btn_edit.setEnabled(false);
        }
        if(opcao == 1){
            btn_add.setEnabled(false);
            btn_delete.setEnabled(false);
        }
        if(opcao == 2){
            btn_add.setEnabled(false);
            btn_edit.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_main = new javax.swing.JPanel();
        lbl_title = new javax.swing.JLabel();
        pnl_buttons = new javax.swing.JPanel();
        btn_add = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_cancel = new javax.swing.JButton();
        btn_confirm = new javax.swing.JButton();
        pnl_form = new javax.swing.JPanel();
        lbl_id = new javax.swing.JLabel();
        lbl_nome = new javax.swing.JLabel();
        txt_nome = new javax.swing.JTextField();
        lbl_cpf = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        lbl_senha = new javax.swing.JLabel();
        ftxt_cpf = new javax.swing.JFormattedTextField();
        ptxt_password = new javax.swing.JPasswordField();
        lbl_nivel = new javax.swing.JLabel();
        ftxt_id = new javax.swing.JFormattedTextField();
        ftxt_nivel = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_tabela = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lbl_title.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lbl_title.setText("Cadastro de Funcionários");

        btn_add.setText("Adicionar");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_edit.setText("Editar");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_delete.setText("Deletar");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_cancel.setText("Cancelar");
        btn_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelActionPerformed(evt);
            }
        });

        btn_confirm.setText("Confirm");
        btn_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_confirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_buttonsLayout = new javax.swing.GroupLayout(pnl_buttons);
        pnl_buttons.setLayout(pnl_buttonsLayout);
        pnl_buttonsLayout.setHorizontalGroup(
            pnl_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_buttonsLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_buttonsLayout.setVerticalGroup(
            pnl_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_add, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(btn_edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_cancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_confirm, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnl_form.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_id.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_id.setText("Id");

        lbl_nome.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_nome.setText("Nome");

        lbl_cpf.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_cpf.setText("Cpf");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Email");

        lbl_senha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_senha.setText("Senha");

        lbl_nivel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_nivel.setText("Nível");

        javax.swing.GroupLayout pnl_formLayout = new javax.swing.GroupLayout(pnl_form);
        pnl_form.setLayout(pnl_formLayout);
        pnl_formLayout.setHorizontalGroup(
            pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_formLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_formLayout.createSequentialGroup()
                        .addComponent(lbl_cpf)
                        .addGap(159, 159, 159)
                        .addComponent(jLabel1)
                        .addGap(174, 174, 174)
                        .addComponent(lbl_senha))
                    .addGroup(pnl_formLayout.createSequentialGroup()
                        .addComponent(ftxt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ptxt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_formLayout.createSequentialGroup()
                        .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_formLayout.createSequentialGroup()
                                .addComponent(lbl_id, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79))
                            .addGroup(pnl_formLayout.createSequentialGroup()
                                .addComponent(ftxt_id)
                                .addGap(11, 11, 11)))
                        .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_nome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_nivel)
                            .addComponent(ftxt_nivel))))
                .addGap(12, 12, 12))
        );
        pnl_formLayout.setVerticalGroup(
            pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_formLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_id)
                    .addComponent(lbl_nome)
                    .addComponent(lbl_nivel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftxt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftxt_nivel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_cpf)
                    .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lbl_senha)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_email)
                        .addComponent(ptxt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ftxt_cpf))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        tb_tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tb_tabela);

        javax.swing.GroupLayout pnl_mainLayout = new javax.swing.GroupLayout(pnl_main);
        pnl_main.setLayout(pnl_mainLayout);
        pnl_mainLayout.setHorizontalGroup(
            pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_mainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_title)
                .addGap(149, 149, 149))
            .addGroup(pnl_mainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnl_mainLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_form, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        pnl_mainLayout.setVerticalGroup(
            pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_mainLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbl_title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_form, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        hab_campos(true);
        opcao = 0;
        desab_botoes(opcao);
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confirmActionPerformed
        // TODO add your handling code here:
        switch (opcao){
            case 0:
                int cadastrar = cadastrar_funcionario();
                limpar_campos();
                hab_campos(false);
                break;
            case 1:
                
                // Remove antigo
                int id = Integer.parseInt(ftxt_id.getText().trim());
                
                for(int i = 0; i < funcionarios.size(); i++){
                    if(id == funcionarios.get(i).getId()) funcionarios.remove(i);
                }
                
                // Criar novo funcionario
                int editado = cadastrar_funcionario();
                
                break;
            default:
                break;
                
        }
        // reseta o atributo opcao
        opcao = -1;
        desab_botoes(opcao);
        carregar_tabela();
    }//GEN-LAST:event_btn_confirmActionPerformed

    private void btn_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelActionPerformed
        
        int i = JOptionPane.showConfirmDialog(this, "Você tem certeza?", "Cancelar", JOptionPane.OK_CANCEL_OPTION);
        if(i == 0) {
            limpar_campos();
            opcao = -1;
            desab_botoes(opcao);
            hab_campos(false);
            carregar_tabela();
        }
        
    }//GEN-LAST:event_btn_cancelActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        opcao = 1;
        desab_botoes(opcao);
        int edicao = editar_funcionario(tb_tabela.getSelectedRow());
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        
        int deletar = deletar_funcionario(tb_tabela.getSelectedRow());
        carregar_tabela();
    }//GEN-LAST:event_btn_deleteActionPerformed
    
        
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_confirm;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JFormattedTextField ftxt_cpf;
    private javax.swing.JFormattedTextField ftxt_id;
    private javax.swing.JFormattedTextField ftxt_nivel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_cpf;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_nivel;
    private javax.swing.JLabel lbl_nome;
    private javax.swing.JLabel lbl_senha;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JPanel pnl_buttons;
    private javax.swing.JPanel pnl_form;
    private javax.swing.JPanel pnl_main;
    private javax.swing.JPasswordField ptxt_password;
    private javax.swing.JTable tb_tabela;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_nome;
    // End of variables declaration//GEN-END:variables
}

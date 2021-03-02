/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartorio_notas;

import java.awt.HeadlessException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Bruno
 */
public class View_procuracao extends javax.swing.JFrame {

    ArrayList<Procuracao> procuracoes = new ArrayList<>();
    int opcao = -1;
    
    public View_procuracao() {
        initComponents();
        init_function();
    }
    
    private void init_function(){
        this.setVisible(true);
        hab_mask();
        carregar_tabela();
        hab_campos(false);
    }
    
    private void hab_mask(){
        try {
            // TODO aplicar mask
            MaskFormatter mask_cpf = new MaskFormatter("###.###.###-##");
            MaskFormatter mask_cpf2 = new MaskFormatter("###.###.###-##");
            MaskFormatter mask_date = new MaskFormatter("##/##/####");
            // Id aceita qualquer caracter para ter espaços vazios.
            MaskFormatter mask_id = new MaskFormatter("AAA");
            mask_id.setPlaceholder(" ");
            mask_id.setValidCharacters("0123456789");
            
            
            mask_id.install(ftxt_id);
            mask_cpf.install(ftxt_cpf);
            mask_cpf2.install(ftxt_cpf_mandatario);
            mask_date.install(ftxt_data);

        } catch (ParseException ex) {
            System.out.println("Erro ao aplicar mask nos campos!\nerro: " + ex);
        }
    }
    
    public void hab_campos(boolean enabled){
        for (int i = 0; i < pnl_form.getComponents().length; i++){
            pnl_form.getComponent(i).setEnabled(enabled);
        }
        lbl_arquivo.setEnabled(false);
        txt_arquivo.setEnabled(false);
    }
    
    private void carregar_tabela(){
        
        tb_tabela.removeAll();
        
        String [] colunas = {"Id", "Data da procuração", "Cpf Mandante", "Cpf Mandatário"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        
        for(int i = 0; i < procuracoes.size(); i++){
            // TODO carregar tabela
            // funcionarios.get(i).getId(), funcionarios.get(i).getNome(), funcionarios.get(i).getEmail()
            Object [] linha = {procuracoes.get(i).getId(), procuracoes.get(i).getData(), procuracoes.get(i).getCpf_mandante(), procuracoes.get(i).getCpf_mandatario()};
            model.addRow(linha);
        }
        
        tb_tabela.setModel(model);
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
    
    private void limpar_campos(){
        ftxt_id.setText("");
        ftxt_cpf.setText("");
        ftxt_cpf_mandatario.setText("");
        ftxt_data.setText("");
        txt_arquivo.setText("");
    }
    
    private boolean validar_campos(){
        
        String id_string = ftxt_id.getText();
        if (id_string.isEmpty()){
            JOptionPane.showMessageDialog(this, "Campo \"Id\" vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        id_string = id_string.trim();
        
        int id = Integer.parseInt(id_string);
        if(!verifica_id(id)){
            JOptionPane.showMessageDialog(this, "Documento já registrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String cpf = ftxt_cpf.getText();
        if(cpf.isEmpty()){
            JOptionPane.showMessageDialog(this, "Campo \"Cpf do testador\" vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        cpf = cpf.replaceAll("\\.", "");
        cpf = cpf.replaceAll("-", "");
        if(!validar_cpf(cpf)){
            JOptionPane.showMessageDialog(this, "Cpf do testador invalido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String cpf_testemunha = ftxt_cpf_mandatario.getText();
        if(cpf_testemunha.isEmpty()){
            JOptionPane.showMessageDialog(this, "Campo \"Cpf da testemunha\" vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        cpf_testemunha = cpf_testemunha.replaceAll("\\.", "");
        cpf_testemunha = cpf_testemunha.replaceAll("-", "");
        if(!validar_cpf(cpf_testemunha)){
            JOptionPane.showMessageDialog(this, "Cpf da testemunha invalido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String data = ftxt_data.getText();
        if(data.isEmpty()){
            JOptionPane.showMessageDialog(this, "Campo \"Data do registro\" vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(!validar_data(data)){
            JOptionPane.showMessageDialog(this, "Data invalida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validação de arquivo não implementada!
        
        return true;
    }
    
    private boolean verifica_id(int id){
        for(int i = 0 ; i < procuracoes.size(); i++){
            if(id == procuracoes.get(i).getId()) return false;
        }
        return true;
    }
    
    private boolean validar_data(String data_string){
        try {
            SimpleDateFormat formatted_date = new SimpleDateFormat("dd/MM/yyyy");
            Date data = formatted_date.parse(data_string);
            
            if(data.after(formatted_date.parse("31/12/2021"))){
                return false;
            }
            
            if(data.before(formatted_date.parse("01/01/1950"))){
                return false;
            }
            
            return true;
            
        } catch (ParseException ex) {
            System.out.println("Erro ao validar a data!\nerro: " + ex);
            return false;
        }
    }
    
    private boolean validar_cpf(String cpf){
        if (cpf.equals("00000000000") ||
            cpf.equals("11111111111") ||
            cpf.equals("22222222222") || cpf.equals("33333333333") ||
            cpf.equals("44444444444") || cpf.equals("55555555555") ||
            cpf.equals("66666666666") || cpf.equals("77777777777") ||
            cpf.equals("88888888888") || cpf.equals("99999999999") ||
            (cpf.length() != 11))
            return(false);
        char dig10, dig11;
        int sm, i, r, num, peso;
        
        sm = 0;
        peso = 10;
        for (i=0; i<9; i++) {
            // converte o i-esimo caractere do CPF em um numero:
            // por exemplo, transforma o caractere '0' no inteiro 0
            // (48 eh a posicao de '0' na tabela ASCII)
            num = (int)(cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
            dig10 = '0';
        else dig10 = (char)(r + 48); // converte no respectivo caractere numerico
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
             dig11 = '0';
        else dig11 = (char)(r + 48);
        if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
             return true;
        else return false;
    }
    
    private int cadastrar_procuracao(){
        if(!validar_campos()) return 1;
            
        try{
            SimpleDateFormat formatted_date = new SimpleDateFormat("dd/MM/yyyy");

            // pegar campos
            String id_string = ftxt_id.getText().trim();
            String cpf_mandante = ftxt_cpf.getText();
            String cpf_mandatario = ftxt_cpf_mandatario.getText();
            String data_string = ftxt_data.getText();

            int id = Integer.parseInt(id_string);
            
            Date data = formatted_date.parse(data_string);
            
            Procuracao procuracao = new Procuracao(id, cpf_mandante, cpf_mandatario, data);
            
            procuracoes.add(procuracao);
            
            limpar_campos();
            hab_campos(false);
            if(this.opcao == 0) JOptionPane.showMessageDialog(this, "Registrado com sucesso!", "Registrar", JOptionPane.INFORMATION_MESSAGE);
            if(this.opcao == 1) JOptionPane.showMessageDialog(this, "Editado com sucesso!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            carregar_tabela();
            return 0;
        }catch(HeadlessException | NumberFormatException | ParseException ex){
            System.out.println("Erro ao registrar procuração!\nerro: " + ex);
        }
        return 0;
    }
    
    private int editar_procuracao(int edit_index){
        hab_campos(true);
        carregar_tabela();
        ftxt_id.setEditable(false);
        
        if(edit_index == -1){
            ftxt_id.setEditable(true);
            JOptionPane.showMessageDialog(this, "Selecione um item da lista", "Erro", JOptionPane.ERROR_MESSAGE);
            hab_campos(false);
            opcao = -1;
            desab_botoes(opcao);
            return 1;
        }
        
        try{
            
            SimpleDateFormat formatted_date = new SimpleDateFormat("dd/MM/yyyy");
            Procuracao procuracao = procuracoes.get(edit_index);
            
            ftxt_id.setText(Integer.toString(procuracao.getId()));
            ftxt_cpf.setText(procuracao.getCpf_mandante());
            ftxt_cpf_mandatario.setText(procuracao.getCpf_mandatario());
            ftxt_data.setText(formatted_date.format(procuracao.getData()));
            
            // Campo arquivo não implementado;
            return 0;
            
        }catch(Exception ex){
            System.out.println("Erro ao acessar o banco de dados!\nerro: " + ex);
            return 1;
        }
    }
    
    private int deletar_procuracao(int delete_index){
        if(delete_index == -1){
            JOptionPane.showMessageDialog(this, "Selecione um item da lista", "Erro", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        try{
            int op = JOptionPane.showConfirmDialog(this, "Você tem certeza?", "Deletar", JOptionPane.OK_CANCEL_OPTION);
            if(op == 0){
                procuracoes.remove(delete_index);
                JOptionPane.showMessageDialog(this, "Registro deletado!", "Delete", JOptionPane.INFORMATION_MESSAGE);
                return 0;
            }else{
                return 1;
            }
        }catch(HeadlessException e){
            System.out.println("Erro ao deletar!\nErro: " + e);
            return 1;
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
        lbl_cpf = new javax.swing.JLabel();
        ftxt_cpf = new javax.swing.JFormattedTextField();
        ftxt_id = new javax.swing.JFormattedTextField();
        lbl_cpf_testemunha = new javax.swing.JLabel();
        ftxt_cpf_mandatario = new javax.swing.JFormattedTextField();
        lbl_arquivo = new javax.swing.JLabel();
        txt_arquivo = new javax.swing.JTextField();
        lbl_data = new javax.swing.JLabel();
        ftxt_data = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_tabela = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lbl_title.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lbl_title.setText("Registro de Procurações");

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

        lbl_cpf.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_cpf.setText("Cpf do Mandante");

        lbl_cpf_testemunha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_cpf_testemunha.setText("Cpf do Mandatário");

        lbl_arquivo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_arquivo.setText("Arquivo");

        txt_arquivo.setEditable(false);

        lbl_data.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_data.setText("Data do registro");

        javax.swing.GroupLayout pnl_formLayout = new javax.swing.GroupLayout(pnl_form);
        pnl_form.setLayout(pnl_formLayout);
        pnl_formLayout.setHorizontalGroup(
            pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_formLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnl_formLayout.createSequentialGroup()
                        .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_id, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftxt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ftxt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_cpf))
                        .addGap(50, 50, 50)
                        .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ftxt_cpf_mandatario, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_formLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(lbl_cpf_testemunha))))
                    .addComponent(lbl_arquivo)
                    .addComponent(txt_arquivo))
                .addGap(43, 43, 43)
                .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_formLayout.createSequentialGroup()
                        .addComponent(lbl_data)
                        .addGap(34, 34, 34))
                    .addComponent(ftxt_data, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(22, 22, 22))
        );
        pnl_formLayout.setVerticalGroup(
            pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_formLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_formLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_formLayout.createSequentialGroup()
                            .addComponent(lbl_cpf_testemunha)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ftxt_cpf_mandatario, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnl_formLayout.createSequentialGroup()
                            .addComponent(lbl_id)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ftxt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnl_formLayout.createSequentialGroup()
                            .addComponent(lbl_cpf)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ftxt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnl_formLayout.createSequentialGroup()
                        .addComponent(lbl_data)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftxt_data)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_arquivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_arquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        SimpleDateFormat formatted_date = new SimpleDateFormat("dd/MM/yyyy");
        Date data_hoje = new Date();
        ftxt_data.setText(formatted_date.format(data_hoje));
        hab_campos(true);
        opcao = 0;
        desab_botoes(opcao);
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        opcao = 1;
        desab_botoes(opcao);
        int editar = editar_procuracao(tb_tabela.getSelectedRow());
        carregar_tabela();
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed

        int deletar = deletar_procuracao(tb_tabela.getSelectedRow());
        carregar_tabela();
    }//GEN-LAST:event_btn_deleteActionPerformed

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

    private void btn_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_confirmActionPerformed
        // TODO add your handling code here:
        switch (opcao){
            case 0:
            int cadastrar = cadastrar_procuracao();
            break;

            case 1:

            // Remove antigo
            int id = Integer.parseInt(ftxt_id.getText().trim());

            for(int i = 0; i < procuracoes.size(); i++){
                if(id == procuracoes.get(i).getId()) procuracoes.remove(i);
            }

            int editado = cadastrar_procuracao();
            break;

            default:
            break;

        }
        // reseta o atributo opcao
        opcao = -1;
        desab_botoes(opcao);
        carregar_tabela();
    }//GEN-LAST:event_btn_confirmActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_cancel;
    private javax.swing.JButton btn_confirm;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JFormattedTextField ftxt_cpf;
    private javax.swing.JFormattedTextField ftxt_cpf_mandatario;
    private javax.swing.JFormattedTextField ftxt_data;
    private javax.swing.JFormattedTextField ftxt_id;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_arquivo;
    private javax.swing.JLabel lbl_cpf;
    private javax.swing.JLabel lbl_cpf_testemunha;
    private javax.swing.JLabel lbl_data;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JPanel pnl_buttons;
    private javax.swing.JPanel pnl_form;
    private javax.swing.JPanel pnl_main;
    private javax.swing.JTable tb_tabela;
    private javax.swing.JTextField txt_arquivo;
    // End of variables declaration//GEN-END:variables
}

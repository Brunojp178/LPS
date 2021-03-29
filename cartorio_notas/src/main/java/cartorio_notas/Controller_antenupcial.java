package cartorio_notas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Bruno
 */
public class Controller_antenupcial {
    private ArrayList<Antenupcial> antenupciais;
    private final Dao_antenupcial dao;
    
    public Controller_antenupcial(){
        
        this.antenupciais = new ArrayList<>();
        this.dao = Dao_antenupcial.getInstance();
    }
    
    public ArrayList<Antenupcial> carregar_tabela(){
        try{
            antenupciais = dao.carregar_collection();
            return antenupciais;
        }catch(Exception e){
            System.out.println("Erro ao carregar a coleção!");
            return null;
        }
    }
    
    public void cadastrar_antenupcial(int id, int id_funcionario, String cpf_noivo, String cpf_noiva, Date data, int opc){
        
        Antenupcial antenupcial = new Antenupcial(id, id_funcionario, cpf_noivo, cpf_noiva, data);
        try{
            dao.inserir_document(antenupcial, opc);
            antenupciais.add(antenupcial);
            JOptionPane.showMessageDialog(null, "Registrado com sucesso!", "Info", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao armazenar o registro!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void deletar_antenupcial(int delete_index){
        
        int op = JOptionPane.showConfirmDialog(null, "Você tem certeza?", "Deletar", JOptionPane.OK_CANCEL_OPTION);
        if(op == 0){
            Antenupcial a = antenupciais.get(delete_index);
            try {
                dao.deletar_document(a);
                antenupciais.remove(a);
                JOptionPane.showMessageDialog(null, "Registro deletado!", "Delete", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao deletar o registro!\nErro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Erro ao deletar o registro!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public int verifica_id(){
        return dao.verifica_id();
    }
    
    public boolean validar_data(String data_string){
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
    
    public boolean validar_cpf(String cpf){
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
}

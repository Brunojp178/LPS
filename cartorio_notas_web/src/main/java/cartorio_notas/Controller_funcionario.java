package cartorio_notas;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Bruno
 */
public class Controller_funcionario {
    
    ArrayList<Funcionario> funcionarios;
    private final Dao_Funcionario dao;
    
    public Controller_funcionario(){
        this.funcionarios = new ArrayList<>();
        this.dao = Dao_Funcionario.getInstance();
    }
    
    public ArrayList<Funcionario> carregar_tabela(){
        funcionarios.clear();
        try{
            funcionarios = dao.carregar_collection();
            return funcionarios;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao carregar o banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public void cadastrar_funcionario(int id, String nome, String email, String cpf, int nivel, char[] senha, int opc){
        
        Funcionario funcionario = new Funcionario(id, nome, cpf, email, nivel, senha);
        
        try{
            dao.inserir_document(funcionario, opc);
            funcionarios.add(funcionario);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao deletar o registro!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void deletar_funcionario(int delete_index){
        
        int op = JOptionPane.showConfirmDialog(null, "Você tem certeza?", "Deletar", JOptionPane.OK_CANCEL_OPTION);
        if(op == 0){
            Funcionario f = funcionarios.get(delete_index);
            try{
                dao.deletar_document(f);
                funcionarios.remove(f);
                JOptionPane.showMessageDialog(null, "Funcionário deletado!", "Delete", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Erro ao deletar o registro!\nErro: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Erro ao deletar o registro!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public int verifica_id(){
        return dao.verifica_id();
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

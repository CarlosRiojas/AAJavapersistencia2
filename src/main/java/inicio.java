
import java.io.IOException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author carlo
 */
public class inicio {
    public static void main(String[] args) throws IOException {
        int opcion_menu= -1;
        String[] botones = {
            "1. Ver gatos","2. Ver Favoritos",
            "3. Salir"
        };
    do{
        String opcion =(String) JOptionPane.showInputDialog(null,"Gatitos Java", "Menu principal",
                JOptionPane.INFORMATION_MESSAGE,null, botones,botones[0]);
        
        //validamos la opcion que selecciona el usuario
              for (int i = 0; i < botones.length; i++) {
                    if(opcion.equals(botones[i])){
                         opcion_menu = i;
           }
            switch(opcion_menu){
                case 0 :
                    gatosService.verGatos();
                    break;
                    
                case 1:
                    gatos gato = new gatos();
                    gatosService.verFavorito(gato.getApiKey());
                default :
                    break;
          
            }
        }
  

}while(opcion_menu != 1);
                
    }
}

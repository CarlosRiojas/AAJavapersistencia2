
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
public class gatosService {
    public static void verGatos() throws IOException {
       //1.traemos los el dato de la API
       OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
  .url("https://api.thecatapi.com/v1/images/search?")
  .method("GET", null)
  .build();
    
    Response response = client.newCall(request).execute();
    
    String elJson = response.body().string();
            
            //cortemos los corchetes
            elJson = elJson.substring(1,elJson.length());
            elJson = elJson.substring(0,elJson.length()-1);
            
            //crear un objeto de la clase gson
            Gson gson = new Gson();
            gatos gatos = gson.fromJson(elJson, gatos.class);
            
            
            //redimensionamos
            Image image = null;
            try{
                URL url = new URL(gatos.getUrl());
                image = ImageIO.read(url);
                
                ImageIcon fondoGato = new ImageIcon(image);
                
                if(fondoGato.getIconWidth()> 800){
                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 600, 0);
                    fondoGato = new ImageIcon(modificada);
                    
                }
                String menu = "Opciones: "+ "1. Ver otra imagen"+
                        "2. Favorito"+
                        "3. Volver \n";
                
                String[] botones = {"ver otra imagen","favorito","volver"};
                String id_gato = gatos.getId();
                String opcion = (String) JOptionPane.showInputDialog(
                null,menu,id_gato, JOptionPane.INFORMATION_MESSAGE, fondoGato,botones,
                        botones[0]);
                
                int seleccion = -1;
                
                   for (int i = 0; i < botones.length; i++) {
                    if(opcion.equals(botones[i])){
                         seleccion = i;
                     }
                   }
                   
                switch(seleccion){
                    case 0:
                         verGatos();
                         break;
                    case 1:
                         favoritoGato(gatos);
                         break;
                         
                         default:
                             break;
                }   
                   
                    
            }catch(Exception e){
                System.out.println(e);
                
            }
    }
   public static void favoritoGato(gatos gato){
        try{
        OkHttpClient client = new OkHttpClient();

MediaType mediaType = MediaType.parse("application/json");
RequestBody body = RequestBody.create("{\n\t\"image_id\": \""+gato.getId()+"\"\n}",null);

Request request = new Request.Builder()
  .url("https://api.thecatapi.com/v1/favourites")
  .post(body)
  .addHeader("content-type", "application/json")
  .addHeader("x-api-key", gato.getApiKey())
  .build();

Response response = client.newCall(request).execute();
        
        }catch(IOException e){
            System.out.println(e);
        
        
        }
   
   }
   
   public static void verFavorito(String apiKey) throws IOException{
        OkHttpClient client = new OkHttpClient();
  
Request request = new Request.Builder()
  .url("https://api.thecatapi.com/v1/favourites")
  .method("GET", null)
  .addHeader("x-api-key", "2d1330b4-afd0-46d0-b311-09da315c1cd4")
  .build();
    Response response = client.newCall(request).execute();
    String elJson = response.body().string();
    
    //creamos el objeto del tipo gson
    Gson gson = new Gson();
    
    gatosFav[] gatosArray = gson.fromJson(elJson,gatosFav[].class);
    if(gatosArray.length>0){
        int min = 1;
        int max = gatosArray.length;
        int aleatorio = (int) (Math.random() * ((max-min)+1))+min;
        int indice = aleatorio-1;
    
            gatosFav gatofav = gatosArray[indice];
            
                    //redimensionamos
                   Image image = null;
                   try{
                       URL url = new URL(gatofav.image.getUrl());
                       image = ImageIO.read(url);

                       ImageIcon fondoGato = new ImageIcon(image);

                       if(fondoGato.getIconWidth()> 800){
                           Image fondo = fondoGato.getImage();
                           Image modificada = fondo.getScaledInstance(800, 600, 0);
                           fondoGato = new ImageIcon(modificada);

                       }
                       String menu = "Opciones: "+ "1. Ver otra imagen \n"+
                               "2. Favorito \n"+
                               "3. Eliminar favorito \n"+
                               "4. Volver \n";

                       String[] botones = {"ver otra imagen","favorito","eliminar favorito","volver"};
                       String id_gato = gatofav.getId();
                       String opcion = (String) JOptionPane.showInputDialog(
                       null,menu,id_gato, JOptionPane.INFORMATION_MESSAGE, fondoGato,botones,
                               botones[0]);

                       int seleccion = -1;

                          for (int i = 0; i < botones.length; i++) {
                           if(opcion.equals(botones[i])){
                                seleccion = i;
                            }
                          }

                       switch(seleccion){
                           case 0:
                                verFavorito(apiKey);
                                break;
                           case 1:
                                borrarFavorito(gatofav);
                                break;

                                default:
                                    break;
                       }   


                   }catch(Exception e){
                       System.out.println(e);

                   }
            
    }
    
   }
   
   public static void borrarFavorito(gatosFav gatosFav){
       try{
       
           OkHttpClient client = new OkHttpClient();
                   
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
            .url("https://api.thecatapi.com/v1/favourites/"+gatosFav.getId()+"")
            .method("DELETE", body)
            .addHeader("x-api-key", gatosFav.getApikey())
            .build();
                Response response = client.newCall(request).execute();
       }catch(Exception e){
               
               
               }
       
       
       }
       
       
       
       
       
       
   
   }
}

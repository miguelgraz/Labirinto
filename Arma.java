import java.util.*;

public class Arma extends Item
{
   private String nome;
   private int dano;
   /**
    * @param nome tipo de arma
    */
   public Arma(String nome){
        this.nome = nome;
        if(nome.equals("faca")){
            this.dano = 1;
        }else{
            if(nome.equals("espada")){
                this.dano = 2;
            }
            else{
                if(nome.equals("cimitarra")){
                    this.dano = 3;
                }
            }
        }
   }
   
   public static Arma Arma(){
       int numero = (int) (Math.random()*3);
       String n = "";
       switch (numero) {
           case 0:
               n = "faca";
               break;
           case 1:
               n = "espada";
               break;
           case 2:
               n = "cimitarra";
               break;
       }
       return new Arma(n);
   }
   
   public int getDano(){
       return dano;
   }
   /**
    * retorna o nome da arma. O nome funciona como tipo da arma.
    * @return valor do nome
    */
   public String getNome(){
       return nome;
   }
}

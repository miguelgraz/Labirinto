import java.util.*;

public class Armadura extends Item
{
   private String nome;
   private int protecao;
   public Armadura(String nome){
        this.nome = nome;
        if(nome.equals("couro")){
            this.protecao = 1;
        }else{
            if(nome.equals("metal")){
                this.protecao = 2;
            }
            else{
                if(nome.equals("mithrill")){
                    this.protecao = 3;
                }
            }
        }
   }

   public static Armadura Armadura(){
       int numero = (int) (Math.random()*3);
       String n = "";
       switch (numero) {
           case 0:
               n = "couro";
               break;
           case 1:
               n = "metal";
               break;
           case 2:
               n = "mithrill";
               break;
       }
       return new Armadura(n);
   }
   
   public int getProtecao(){
       return protecao;
   }

   public String getNome(){
       return nome;
   }
   
}

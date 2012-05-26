public class Jogador
{
     private String nome;
     private int vida=10;
     private Arma arma;
     private Armadura armadura;
     private Chave chave;

     public Jogador(String nome){
        this.nome=nome;
        this.arma = new Arma("faca");
     }
     public String getNome(){
        return nome;
     }
     public int getVida(){
         return vida;
     }
     public void sofreDano(int valor){
         if (qualArmadura() != ""){
             valor -= getArmadura().getProtecao();
             if (valor < 0)
                 valor = 0;
         }
         if (valor > 0) {
            System.out.println("O bicho te causou " + valor + " pontos de dano!");
         } else {
            System.out.println("Ataque absorvido pela armadura!");
         }
         vida-=valor;
     }
     public boolean morto(){
         return (vida <= 0 ? true : false);
     }
     public String qualArma(){
         return (arma != null ? arma.getNome() : "");
     }
     public String qualArmadura(){
         return (armadura != null ? armadura.getNome() : "");
     }
     public void pegaArma(Arma a){
         this.arma = a;
     }
     public void pegaArmadura(Armadura a){
         this.armadura = a;
     }
     public Arma getArma(){
         return arma;
     }
     public Armadura getArmadura(){
         return armadura;
     }
     public void listaEquips(){
         String s = "Você tem uma " + qualArma() + " com dano extra de " + (new Arma(qualArma()).getDano());
         if (qualArmadura() != "")
            s += " e uma armadura de " + qualArmadura() + " com protecao de " + (new Armadura(qualArmadura()).getProtecao());
         s += " e " + getVida() + " pontos de vida";
         System.out.println(s);
     }
     /*public void recebeDano(){ //metodo para receber um valor fixo de dano. Neste caso 1.
        recebeDano(1); //é melhor reutilizar o método anterior existente do que reinplementar.   
     }*/
}
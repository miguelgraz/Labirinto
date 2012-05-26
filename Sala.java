import java.util.*;

public class Sala {
    private static final int INVALIDA = -1;
    private static final int NORTE    = 0;
    private static final int SUL      = 1;
    private static final int LESTE    = 2;
    private static final int OESTE    = 3;
    private static final int ACIMA    = 4;
    private static final int ABAIXO   = 5;
    
    private static int translateDirecao(String dir) {
        if (dir.equalsIgnoreCase("north") || dir.equalsIgnoreCase("norte"))
            return NORTE;
        if (dir.equalsIgnoreCase("south") || dir.equalsIgnoreCase("sul"))
            return SUL;
        if (dir.equalsIgnoreCase("east") || dir.equalsIgnoreCase("leste"))
            return LESTE;
        if (dir.equalsIgnoreCase("west") || dir.equalsIgnoreCase("oeste"))
            return OESTE;
        if (dir.equalsIgnoreCase("up") || dir.equalsIgnoreCase("acima"))
            return ACIMA;
        if (dir.equalsIgnoreCase("down") || dir.equalsIgnoreCase("abaixo"))
            return ABAIXO;
        return INVALIDA;
    }
    
    private String descricao;
    private int[] saidas = new int[6];
    private Armadilha[] traps = new Armadilha[6];
    private Arma arma;
    private Armadura armadura;
    private Chave chave;
    private Inimigo inimigo;

    public Sala() {
        for(int i=0;i<=5;i++){
            saidas[i] = -1;
        }
        teraArma();
        teraArmadura();
        teraInimigo();
        atualizaDesc();
    }
    
    public void atualizaDesc(){
        boolean tem = false;
        String s = "Você está numa sala que";
        if (getArma() != ""){
            s += " tem uma " + getArma();
            tem = true;
        }
        if (getArmadura() != ""){
            s += " tem uma armadura de " + getArmadura();
            tem = true;
        }
        if (getNomeInimigo() != ""){
            Inimigo i = getInimigo();
            s += " tem um inimigo " + i.getTipo() + " com " + i.getVida() + " pontos de vida e " + i.getDano() + " de porradaria";
            tem = true;
        }
        if (! tem)
            s += " não tem nada";
        descricao = s;
    }
    
    public void addSaida(String dir, int salaId) {
        /**/
        saidas[translateDirecao(dir)] = salaId;
    }
    
    public void addTrap(String dir) {
        /**/
        traps[translateDirecao(dir)] = new Armadilha();
    }
    
    public void setDescricao(String value) {
        descricao = value;  
    }
    
    public void setArma(Arma a) {
        arma = a;
        atualizaDesc();
    }
    
    public void setArmadura(Armadura a) {
        armadura = a;
        atualizaDesc();
    }
    
    public void setInimigo(Inimigo i) {
        inimigo = i;
        atualizaDesc();
    }
    
    public void setArma() {
        this.arma = null;
        atualizaDesc();
    }
    
    public void setArmadura() {
        this.armadura = null;
        atualizaDesc();
    }
    
    public void setInimigo() {
        this.inimigo = null;
        atualizaDesc();
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public String getArma() {
        return (arma != null ? arma.getNome() : "");
    }
    
    public String getArmadura() {
        return (armadura != null ? armadura.getNome() : "");
    }
    
    public Inimigo getInimigo(){
        return inimigo;
    }
    
    public String getNomeInimigo() {
        return (inimigo != null ? inimigo.getTipo() : "");
    }
    
    public String toString() {
        return getDescricao();
    }
    
    public int[] getSaidas(){
        return saidas;
    }
    
    public void imprimeSaidas(){
        int dir = -1;
        for(int i : getSaidas()){
            dir ++;
            if(i >= 0){
                String ret = "";
                switch (dir) {
                    case 0:
                        ret = "norte";
                        break;
                    case 1:
                        ret = "sul";
                        break;
                    case 2:
                        ret = "leste";
                        break;
                    case 3:
                        ret = "oeste";
                        break;
                    case 4:
                        ret = "acima";
                        break;
                    case 5:
                        ret = "abaixo";
                        break;
                 }
                System.out.println("Essa sala tem saída para: " + ret);
            }
        }
    }
    
    public int retornaSaida(String dir){
        return saidas[translateDirecao(dir)];
    }
    
    public boolean temPorta(String dir){
        if (translateDirecao(dir) == -1)
            return false;
        return (saidas[translateDirecao(dir)] != -1 ? true : false);
    }
    
    private void teraArma(){
        if(chance20porcento())
            this.arma = Arma.Arma();
    }

    private void teraArmadura(){
        if(chance20porcento())
            this.armadura = Armadura.Armadura();
    }

    private void teraInimigo(){
        if(chance30porcento()){
            this.inimigo = Inimigo.Inimigo();
            // aleatoriedade pra ver qual a saída que ele guarda
        }
    }
    
    private boolean chance20porcento(){
        int numero = (int) (Math.random()*5);
        return (numero == 0 ? true : false);
    }

    private boolean chance30porcento(){
        int numero = (int) (Math.random()*3);
        return (numero == 0 ? true : false);
    }

}
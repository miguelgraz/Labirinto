import java.util.*;
import java.io.*;

class Labirinto {
    public static final Scanner teclado = new Scanner(System.in);

    private Sala[] salas = new Sala[35];
    private int salasCount = 0;
    private Sala salaAtual;
    private Jogador player;
    
    private void importaArquivo(String filename) {
        Scanner sc = null;
        
        try {
            sc = new Scanner(new FileInputStream(filename));
            /**/
            String comando = sc.next();
            while (comando.equalsIgnoreCase("room")) {
                /**/
                int salaId = sc.nextInt();
                salas[salaId] = new Sala();
                Sala sala = salas[salaId];
                /**/
                if (salaId > salasCount)
                    salasCount = salaId;
                /**/
                String direcao = sc.next();
                while (sc.hasNextInt()) {
                    int saida = sc.nextInt();
                    /**/
                    sala.addSaida(direcao, saida);
                    /**/
                    if (!sc.hasNext()) return;
                    comando = sc.next();
                    /**/
                    if (comando.equalsIgnoreCase("TRAP")) {
                        sala.addTrap(direcao);
                        /**/
                        if (sc.hasNext())
                            comando = sc.next();
                    }
                    if (comando.equalsIgnoreCase("room"))
                        break;
                    
                    direcao = comando;
                }
            }
        }
        catch (FileNotFoundException fnfe) {
            System.err.println("Nao foi possivel abrir o arquivo " + filename);
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("Arquivo invalido.");
            System.exit(1);
        }
        finally {
            if (sc != null)
                sc.close();
        }
    }
    
    public Labirinto(String filename) {
        /**/
        Sala sala = new Sala();
        sala.setDescricao("Parabens, voce fugiu do Labirinto.");
        salas[0] = sala;

        /**/
        importaArquivo(filename);
    }
    
    public static void main(String[] args) {
        String filename;
        
        if (args.length > 0) {
            filename = args[0];
        } else {
            System.out.println("Digite o caminho para o nome do arquivo: ");
            filename = teclado.next();
        }
        Labirinto labirinto = new Labirinto(filename);
        labirinto.run();
    }
    
    public void run() {
        iniciaUser();
        player.listaEquips();
        System.out.println(salaAtual.getDescricao());
        salaAtual.imprimeSaidas();
        System.out.println("Digite a próxima ação do personagem (acao e sentido): ");
        String acao = teclado.next();
        String sentido = teclado.next();
        while(! (acao.equals("fim") && sentido.equals("fim"))) {
            interpretaComando(acao, sentido);
            seraConseguiu();
            
            System.out.println("Digite a próxima ação do personagem (acao e sentido): ");
            acao = teclado.next();
            sentido = teclado.next();
        }
    }
    
    public void iniciaUser(){
        System.out.println("Qual o nome do seu aventureiro badass?");
        String nome = teclado.next();
        player = new Jogador(nome);
        salaAtual = salas[1];
        System.out.println("Bem vindo ao Jogo do Labirinto, "+ player.getNome());
        System.out.println("Ande pelo labirinto digitando o comando 'andar DIRECAO'");
        System.out.println("Pegue um item digitando o comando 'pegar NOMEDOITEM'");
        System.out.println("Ataque um monstro digitando o comando 'bater monstro' quando em uma sala que tenha um monstro");
        System.out.println("Termine o programa digitando 'fim fim' (duas vezes mesmo)");
    }
    
    public void interpretaComando(String verbo, String subst){
        if (verbo.equals("bater")){
            if(salaAtual.getNomeInimigo() != ""){
                Inimigo ini = salaAtual.getInimigo();
                playerBate(ini);
                if (ini.morto()){
                    salaAtual.setInimigo();
                    System.out.println("Tu matou o monstrão feio!");
                    salaAtual.imprimeSaidas();
                } else {
                    salaAtual.setInimigo(ini);
                    monstroBate(ini);
                }
            }
            System.out.println(salaAtual.getDescricao());
            player.listaEquips();
        } else {
            if(salaAtual.getNomeInimigo() != ""){
                Inimigo ini = salaAtual.getInimigo();
                monstroBate(ini);
            }
            
            if(verbo.equals("pegar")){
                if (salaAtual.getArma().equals(subst)){
                    salaAtual.setArma();
                    if (player.qualArma() != "")
                        salaAtual.setArma(new Arma(player.qualArma()));
                    player.pegaArma(new Arma(subst));
                }
                if (salaAtual.getArmadura().equals(subst)){
                    salaAtual.setArmadura();
                    if (player.qualArmadura() != "")
                        salaAtual.setArmadura(new Armadura(player.qualArmadura()));
                    player.pegaArmadura(new Armadura(subst));
                }
                player.listaEquips();
                System.out.println(salaAtual.getDescricao());
            } else {
                if(verbo.equals("andar")){
                    if (salaAtual.temPorta(subst))
                        salaAtual = salas[salaAtual.retornaSaida(subst)];
                    System.out.println(salaAtual.getDescricao());
                    salaAtual.imprimeSaidas();
                } else {
                    System.out.println("Comando inválido");
                }
            }           
        }
    }
    
    public void playerBate(Inimigo ini){
        int numero = (int) (Math.random()*5);
        if (numero != 0){
            int dano = player.getArma().getDano() + 2;
            ini.sofreDano(dano);
            System.out.println("Tu causou " + dano + " de dano no bicho!");
        } else {
            System.out.println("Tu errou o ataque!");
        }
    }
    
    public void monstroBate(Inimigo ini){
        int numero = (int) (Math.random()*2);
        if (numero != 0){
            player.sofreDano(ini.getDano());
            if (player.morto())
                vish();
        } else {
            System.out.println("O monstro errou o ataque!");
        }
    }
    
    public void vish(){
        System.out.println("Ih rapaiz, tu morreu, não deu dessa vez.");
        System.exit(1);
    }
    
    public void seraConseguiu(){
        if (salaAtual == salas[0]){
            System.out.println("Caraio, tu conseguiu mesmo, valeu tchê!");
            System.exit(1);
        }
    }
    
}
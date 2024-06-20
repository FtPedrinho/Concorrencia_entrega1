import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    
    // Classe do banco com uma variável manipulável chamada Saldo
    public static class BancoConta{
        private int saldo;

        // Criação do Lock | Em casos de não sincronização, retiram-se os locks e unlocks entre comentários
        private final Lock lock = new ReentrantLock();

        public BancoConta(int saldo){
            this.saldo = saldo;
        }

        // Função para manipular o saldo que puxa o valor, o comando entre "retirar" e "depositar" e o nome do agente
        public void operacaoConta(int valor, String comando, String nome) throws InterruptedException{
            if (comando == "depositar"){
                System.out.println(nome + " quer depositar " + valor);
                lock.lock();
                try{
                    saldo += valor;
                    System.out.println(nome + " depositou: " + valor + ". Agora o saldo da conta é: " + saldo);
                }
                finally{
                    Thread.sleep(0);
                    lock.unlock();
                }
            } else{
                System.out.println(nome + " quer retirar " + valor);
                lock.lock();
                try{
                    if (saldo - valor >= 0){
                        saldo -= valor;
                        System.out.println(nome + " retirou um valor de: " + valor + ". O novo saldo da conta é: " + saldo);
                    } else{
                        System.out.println(nome + " não conseguiu retirar um valor de: " + valor + ". A conta possui apenas: " + saldo);
                    }
                }
                finally{
                    Thread.sleep(0);
                    lock.unlock();
                }
            }
        }
    }

    // Criação da classe dos Clientes, chamda de Operação | Eles são constituidos pelo nome, banco, comando "retirar" e "depositar" e o valor trabalhado
    public static class Operacao implements Runnable{
        private BancoConta conta;
        private int valor;
        private String comando;
        private String nome;

        public Operacao(BancoConta conta, int valor, String comando, String nome){
            this.conta = conta;
            this.valor = valor;
            this.comando = comando;
            this.nome = nome;
        }

        // Função que liga os Clientes ao Banco
        public void run(){
            try{
                conta.operacaoConta(valor, comando, nome);
            } catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }

    }

// Main com a criação do banco, prints iniciais e finais e criação das Threads dos Clientes
    public static void main(String[] args) throws InterruptedException{
        BancoConta ContaFamilia = new BancoConta(0);

        System.out.println("Saldo inicial: " + ContaFamilia.saldo + ".");

        Operacao Pai = new Operacao(ContaFamilia, 150, "depositar", "Pai");
        Thread Pai_Thread = new Thread(Pai);
        Pai_Thread.start();

        Operacao Mae = new Operacao(ContaFamilia, 500, "retirar", "Mae");
        Thread Mae_Thread = new Thread(Mae);
        Mae_Thread.start();

        Operacao Tio = new Operacao(ContaFamilia, 300, "depositar", "Tio");
        Thread Tio_Thread = new Thread(Tio);
        Tio_Thread.start();

        Operacao Avo = new Operacao(ContaFamilia, 400, "retirar", "Avo");
        Thread Avo_Thread = new Thread(Avo);
        Avo_Thread.start();

        Operacao Filho = new Operacao(ContaFamilia, 50, "retirar", "Filho");
        Thread Filho_Thread = new Thread(Filho);
        Filho_Thread.start();

        Operacao Filha = new Operacao(ContaFamilia, 75, "retirar", "Filha");
        Thread Filha_Thread = new Thread(Filha);
        Filha_Thread.start();

        Operacao Sobrinho = new Operacao(ContaFamilia, 250, "depositar", "Sobrinho");
        Thread Sobrinho_Thread = new Thread(Sobrinho);
        Sobrinho_Thread.start();

        // Ordenando prints e ordenando respostas
        Pai_Thread.join();
        Mae_Thread.join();
        Tio_Thread.join();
        Avo_Thread.join();
        Filho_Thread.join();
        Filha_Thread.join();
        Sobrinho_Thread.join();

        System.out.println("Saldo final: " + ContaFamilia.saldo + ".");
    }
}

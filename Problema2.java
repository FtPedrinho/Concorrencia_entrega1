import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    // Criação da classe da ponte com a variável que servirá como semáforo chamada Quantidade
    public static class Ponte{
        private int direita;
        private int esquerda;

        private final Lock lock = new ReentrantLock();

        public Ponte(){
            this.direita = 0;
            this.esquerda = 0;
        }

        // Criação da função de passar pela ponte | Ela puxa os parâmetros dos carros: nome e direção
        // Em casos de Sincronização, as Threads vão dar lock e proibir que a quantidade máxima na ponte ultrapasse 1.
        // Em casos sem sincronização, as Threads não vão ativar locks e unlocks, permitindo que mais de uma Thread manipule a quantidade na ponte ao mesmo tempo
        // Impedindo que os carros passem pela ponte única.
        public void passarPonte(String direcao, String nome) throws InterruptedException {
            if (Objects.equals(direcao, "direita")){
                System.out.println(nome + " quer atravessar para a direita.");
//                lock.lock();
                esquerda += 1;
                System.out.println(nome + " está atravessando a ponte para a direita.");
                if (1 <= direita){
                    System.out.println(nome + " não conseguiu atravessar a ponte porque tem " + direita + " carro(s) impedindo.");
                }else{
                    try {
                        System.out.println(nome + " atravessou a ponte. Sem carros no sentido contrário");
                    } finally {
                        Thread.sleep(1000);
                        esquerda -= 1;
//                        lock.unlock();
                    }
                }
            } else{
                System.out.println(nome + " quer atravessar para a esquerda.");
//                lock.lock();
                direita += 1;
                System.out.println(nome + " está atravessando a ponte para a esquerda.");
                if (1 <= esquerda){
                    System.out.println(nome + " não conseguiu atravessar a ponte porque tem " + esquerda + " carro(s) impedindo.");
                }else {
                    try {
                        System.out.println(nome + " atravessou a ponte. Sem carros no sentido contrário");
                    }finally {
                        Thread.sleep(1000);
                        direita -= 1;
//                        lock.unlock();
                    }
                }
            }
        }
    }

    // Criação da classe dos carros, contendo a ponte relacionada, o comando "direita" ou "esquerda" e o nome do carro
    public static class Carro implements Runnable {
        private final Ponte ponte;
        private final String comando;
        private final String nome;

        public Carro(Ponte ponte, String comando, String nome) {
            this.ponte = ponte;
            this.comando = comando;
            this.nome = nome;
        }

        // Função que liga os carros à ponte e ocorrem as passagens
        public void run() {
            try {
                ponte.passarPonte(comando, nome);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Main contendo a criação da ponte, das Threads dos carros e os prints finais e iniciais.
        public static void main(String[] args) throws InterruptedException {
            Ponte Ponte1 = new Ponte();

            Carro Moto = new Carro(Ponte1, "esquerda", "Moto");
            Thread Moto_Thread = new Thread(Moto);

            Carro Carro = new Carro(Ponte1, "esquerda", "Carro");
            Thread Carro_Thread = new Thread(Carro);

            Carro Limusine = new Carro(Ponte1, "direita", "Limusine");
            Thread Limusine_Thread = new Thread(Limusine);

            Carro Taxi = new Carro(Ponte1, "esquerda", "Taxi");
            Thread Taxi_Thread = new Thread(Taxi);

            Carro Uber = new Carro(Ponte1, "direita", "Uber");
            Thread Uber_Thread = new Thread(Uber);

            Carro Van = new Carro(Ponte1, "esquerda", "Van");
            Thread Van_Thread = new Thread(Van);

            Carro Onibus = new Carro(Ponte1, "direita", "Onibus");
            Thread Onibus_Thread = new Thread(Onibus);

            Carro Caminhao = new Carro(Ponte1, "direita", "Caminhao");
            Thread Caminhao_Thread = new Thread(Caminhao);
            Carro Kwid = new Carro(Ponte1, "direita", "Kwid");
            Thread Kwid_Thread = new Thread(Kwid);

            Carro Macqueen = new Carro(Ponte1, "esquerda", "Macqueen");
            Thread Macqueen_Thread = new Thread(Macqueen);

            Moto_Thread.start();
            Carro_Thread.start();
            Limusine_Thread.start();
            Taxi_Thread.start();
            Uber_Thread.start();
            Van_Thread.start();
            Onibus_Thread.start();
            Caminhao_Thread.start();
            Kwid_Thread.start();
            Macqueen_Thread.start();

            // Ordenando respostas e prints na tela
            Moto_Thread.join();
            Carro_Thread.join();
            Limusine_Thread.join();
            Taxi_Thread.join();
            Uber_Thread.join();
            Onibus_Thread.join();
            Van_Thread.join();
            Caminhao_Thread.join();
            Kwid_Thread.join();
            Macqueen_Thread.join();
        }
    }
}

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static class Ponte{
        private int quantidade;

        private final Lock lock = new ReentrantLock();

        public Ponte(int quantidade){
            this.quantidade = quantidade;
        }

        public void passarPonte(String direcao, String nome) throws InterruptedException{
            if (direcao == "direita"){
                System.out.println(nome + " quer atravessar para a direita.");
                lock.lock();
                try{
                    quantidade += 1;
                    System.out.println(nome + " está atravessando a ponte para a direita...");
                    if (quantidade == 1){
                        System.out.println("Apenas 1 carro na ponte");
                    }else{
                        System.out.println("Tem " + quantidade +" na ponte!!!");
                    }
                }
                finally{
                    Thread.sleep(1000);
                    quantidade -= 1;
                    lock.unlock();
                }
            } else{
                System.out.println(nome + " quer atravessar para a esquerda.");
                lock.lock();
                try{
                    quantidade += 1;
                    System.out.println(nome + " está atravessando a ponte para a esquerda...");
                    if (quantidade == 1){
                        System.out.println("Apenas 1 carro na ponte");
                    }else{
                        System.out.println("Tem " + quantidade +" na ponte!!!");
                    }
                }
                finally{
                    Thread.sleep(1000);
                    quantidade -= 1;
                    lock.unlock();
                }
            }
        }
    }

    public static class Carro implements Runnable{
        private Ponte ponte;
        private String comando;
        private String nome;

        public Carro(Ponte ponte, String comando, String nome){
            this.ponte = ponte;
            this.comando = comando;
            this.nome = nome;
        }

        public void run(){
            try{
                ponte.passarPonte(comando, nome);
            } catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }

    }


    public static void main(String[] args) throws InterruptedException{
        Ponte Ponte1 = new Ponte(0);

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

        System.out.println("Todos os carros atravessaram!");
    }
}

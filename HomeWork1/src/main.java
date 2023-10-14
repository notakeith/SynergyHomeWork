class Counter {
    private int count = 0;
    public void increment() {count++;}
    public int getValue() {return count;}
}

public class main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread thread1 = new Thread(() -> {for (int i = 0; i < 1000; i++) {counter.increment();}});
        Thread thread2 = new Thread(() -> {for (int i = 0; i < 1000; i++) {counter.increment();}});

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Значение count: " + counter.getValue());
    }
}

//у переменной count разное значение каждый запуск программы из-за одновременного доступа нескольких потоков к общим данным, происходит так называемая "гонка за данные". Нужно использовать synchronized переменной
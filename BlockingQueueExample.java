import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingQueueExample {
    public static void main(String[] args) throws InterruptedException {
      System.out.println("生产者1秒生产1个，消费者2秒消费个，形成背压，从打印中可以看出");
      BlockingQueue<Integer> queue = new LinkedBlockingDeque<>(10);

      Thread producer = new Thread(() -> {
        try {
          for (int i = 1; i <= 30; i++) {
            queue.put(i);
            System.out.println("生产者线程 put:" + i);
            Thread.sleep(1000);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });

      Thread consumer = new Thread(()-> {
        try {
          for (int i = 1; i <= 30; i++) {
            int data = queue.take();
            System.out.println("消费者线程 take" + data);
            Thread.sleep(2000);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });

      producer.start();
      consumer.start();

      producer.join();
      consumer.join();
    }
}
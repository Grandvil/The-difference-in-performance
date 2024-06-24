import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int NUM_THREADS = 8;
    private static final int NUM_ELEMENTS = 100000; // Изменять это значение для повторного эксперимента

    public static void main(String[] args) throws InterruptedException {
        // Генерация массива чисел
        int[] numbers = generateNumbers(NUM_ELEMENTS);

        // Тестирование ConcurrentHashMap
        long startTime = System.currentTimeMillis();
        testMap(new ConcurrentHashMap<>(), numbers);
        long endTime = System.currentTimeMillis();
        System.out.println("ConcurrentHashMap время выполнения: " + (endTime - startTime) + " мс");

        // Тестирование synchronizedMap
        startTime = System.currentTimeMillis();
        testMap(Collections.synchronizedMap(new HashMap<>()), numbers);
        endTime = System.currentTimeMillis();
        System.out.println("synchronizedMap время выполнения: " + (endTime - startTime) + " мс");
    }

    private static int[] generateNumbers(int numElements) {
        Random random = new Random();
        int[] numbers = new int[numElements];
        for (int i = 0; i < numElements; i++) {
            numbers[i] = random.nextInt();
        }
        return numbers;
    }

    private static void testMap(Map<Integer, Integer> map, int[] numbers) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(new MapWorker(map, numbers));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }
}

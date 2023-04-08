import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static int threadCount = 1000;

    public static void main(String[] args) {

        final ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            Callable<Map<Integer, Integer>> mapCallable = new CallableTask(sizeToFreq);
            threadPool.submit(mapCallable);
        }
        threadPool.shutdown();

        int value = 0;
        int key = 0;
        Set<Integer> keyMap = sizeToFreq.keySet();
        for (Integer integer : keyMap) {
            if (sizeToFreq.get(integer) > value) {
                value = sizeToFreq.get(integer);
                key = integer;
            }
        }

        sizeToFreq.remove(key, value);

        System.out.println("Самое частое количество повторений " + key + " (встретилось " + value + " раз)");
        System.out.println("Другие размеры:");
        for (Integer integer : sizeToFreq.keySet()) {
            System.out.println("- " + integer + " (" + sizeToFreq.get(integer) + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
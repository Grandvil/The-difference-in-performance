import java.util.Map;

class MapWorker implements Runnable {
    private final Map<Integer, Integer> map;
    private final int[] numbers;

    public MapWorker(Map<Integer, Integer> map, int[] numbers) {
        this.map = map;
        this.numbers = numbers;
    }

    @Override
    public void run() {
        for (int number : numbers) {
            map.put(number, number);
            map.get(number);
        }
    }
}
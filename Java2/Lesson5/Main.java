class Task extends Thread {
    private static float arr[];

    @Override
    public void run() {
        Main.formula(arr);
    }

    Task(float[] a) {
        arr = a;
    }
};


public class Main {
    static final int SIZE = 40000000;
    static final int THREADS = 20;
    static float[] arr = new float[SIZE];

    public static void main(String[] args) {
        initVal(arr);
        for (int i = 1; i <= THREADS; i++) {
            main(arr, i);
        }
    }

    private static void main(float[] arr, int threads) {
        long a;
        a = System.currentTimeMillis();
        calcVal(arr, threads);
        System.out.printf("size: %d threads: %d time: %d%n", SIZE, threads, (System.currentTimeMillis() - a));
    }

    private static void initVal(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
    }

    private static float[] getArrPart(float[] srcArr, int begIdx, int len) {
        float[] destArr = new float[len];
        System.arraycopy(srcArr, begIdx, destArr, 0, len);
        return destArr;
    }

    private static float[][] splitArr(float[] srcArr, int parts) {
        if (parts <= 0) throw new RuntimeException(String.format(
                "Invalid parts number: %d ", parts));
        float[][] destArr = new float[parts][];
        int arrDefLen = arr.length / parts;
        for (int i = 0; i < parts; i++) {
            int begIdx = i * arrDefLen;
            int arrLen = (i == parts - 1) ? arr.length - (arrDefLen * i) : arrDefLen;
            destArr[i] = getArrPart(arr, begIdx, arrLen);
        }
        return destArr;
    }

    private static float[] assembleArr(float[][] srcArr) {
        int idx = 0;
        for (int i = 0; i < srcArr.length; i++) {
            idx += srcArr[i].length;
        }
        float[] destArr = new float[idx];
        idx = 0;
        for (int i = 0; i < srcArr.length; i++) {
            System.arraycopy(srcArr[i], 0, destArr,idx, srcArr[i].length);
            idx = idx + srcArr[i].length - 1;
        }
        return destArr;
    }

    static void formula(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    private static void calcVal(float[] arr, int threads) {
        Task[] taskPool = new Task[threads];
        if (threads <= 0) throw new RuntimeException(String.format(
                "invalid threads number: %d", threads));
        float[][] arrPart = splitArr(arr, threads);
        for (int i = 0; i < threads; i++) {
            taskPool[i] = new Task(arrPart[i]);
            taskPool[i].start();
        }
        try {
            for (int i = 0; i < threads; i++) {
                taskPool[i].join();
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Multi-thread calculation error: %s", e.getMessage()));
        }
        assembleArr(arrPart);
    }
}

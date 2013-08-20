package Catman2D.Logic;

import java.util.HashMap;

public class LogicManager {

    private static LogicThread lt;

    public static void init() {
        lt = new LogicThread();
        new Thread(lt).start();
    }

    public static void addListener(LogicListener ll) {
        lt.addListener(ll);
    }
}

class LogicThread implements Runnable {

    public static final String TAG = "LogicThread";
    private HashMap<Integer, LogicListener> listeners = new HashMap<Integer, LogicListener>();
    private int nextKey = 0;

    public void addListener(LogicListener ll) {
        listeners.put(nextKey, ll);
        nextKey++;
    }

    @Override
    public void run() {
        while (true) {
            for (int key : listeners.keySet()) {
                listeners.get(key).onUpdate();
            }
        }
    }
}

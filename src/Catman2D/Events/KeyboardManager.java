package Catman2D.Events;

import org.lwjgl.input.Keyboard;

import java.util.*;

public class KeyboardManager {

    private static HashMap<Integer, KeyboardListener> listeners = new HashMap<Integer, KeyboardListener>();
    private static int nextKey = 0;
    private static KeyboardListener tempListener = null;

    public static void addListener(KeyboardListener listener) {
        listeners.put(nextKey, listener);
        nextKey++;
    }

    public static void onUpdate() {
        while (Keyboard.next()) {
            Set<Integer> entries = listeners.keySet();
            List<Integer> list = new ArrayList<Integer>();

            for(int key : entries){
                list.add(key);
            }

            KeyboardListener kl = null;

            ListIterator<Integer> li = list.listIterator();
            while(li.hasNext()){
                kl = listeners.get(li.next());

                if (Keyboard.getEventKeyState()) {
                    kl.onKeyPress(Keyboard.getEventKey());
                } else {
                    kl.onKeyRelease(Keyboard.getEventKey());
                }
            }
        }

    }

}

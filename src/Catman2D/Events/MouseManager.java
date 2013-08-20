package Catman2D.Events;

import Catman2D.Toolbox.CMConfig;
import org.lwjgl.input.Mouse;

import java.util.*;

public class MouseManager {

    private static HashMap<Integer, MouseListener> listeners = new HashMap<Integer, MouseListener>();
    private static int nextKey = 0;
    private static KeyboardListener tempListener = null;
    private static boolean isPressed = false;

    public static void addListener(MouseListener listener) {
        listeners.put(nextKey, listener);
        nextKey++;
    }

    public static void onUpdate() {

        Set<Integer> entries = listeners.keySet();
        List<Integer> list = new ArrayList<Integer>();

        for (int key : entries) {
            list.add(key);
        }

        MouseListener ml = null;

        if (Mouse.isButtonDown(0) && isPressed == false) {
            isPressed = true;

            ListIterator<Integer> li = list.listIterator();
            while (li.hasNext()) {
                ml = listeners.get(li.next());
                ml.onButtonPress(Mouse.getEventButton(), Mouse.getX(),  CMConfig.WINDOW_HEIGHT - Mouse.getY());
            }
        } else {
            if (isPressed == true && Mouse.isButtonDown(0) == false) {
                ListIterator<Integer> li = list.listIterator();
                while (li.hasNext()) {
                    ml = listeners.get(li.next());
                    ml.onButtonRelease(Mouse.getEventButton(), Mouse.getX(), CMConfig.WINDOW_HEIGHT - Mouse.getY());
                }

                isPressed = false;
            }
        }
    }

    public static int getY(){
        return CMConfig.WINDOW_HEIGHT - Mouse.getY();
    }

    public static int getX(){
        return Mouse.getX();
    }
}

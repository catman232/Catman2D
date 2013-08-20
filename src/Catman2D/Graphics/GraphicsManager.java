package Catman2D.Graphics;

import Catman2D.Events.KeyboardManager;
import Catman2D.Events.MouseManager;
import Catman2D.Objects.Entity;
import Catman2D.Packaging.PackageManager;
import Catman2D.Toolbox.CMConfig;
import Catman2D.Toolbox.Toolbox;
import Catman2D.UI.Console;
import Catman2D.UI.MenuBar;
import Catman2D.UI.Window;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.HashMap;

public class GraphicsManager {

    private GraphicsThread gt;

    public GraphicsManager(){
        gt = new GraphicsThread();
        new Thread(gt).start();
    }

    public int addEntity(Entity e){
        return gt.addEntity(e);
    }
}

class GraphicsThread implements Runnable{

    public static final String TAG = "GraphicsThread";
    private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
    private int nextKey = 0;

    public int addEntity(Entity entity){
        entities.put(nextKey, entity);
        nextKey++;
        return nextKey;
    }

    @Override
    public void run() {
        try{
            Display.setDisplayMode(new DisplayMode(CMConfig.WINDOW_WIDTH, CMConfig.WINDOW_HEIGHT));
            Toolbox.debugPrint(TAG, "Creating new Display with resoloution " + CMConfig.WINDOW_WIDTH + "x" + CMConfig.WINDOW_HEIGHT);
            Display.create();

            GL11.glEnable(GL11.GL_TEXTURE_2D);

            GL11.glDisable(GL11.GL_DEPTH_TEST);

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();

            GL11.glOrtho(0, CMConfig.WINDOW_WIDTH, CMConfig.WINDOW_HEIGHT, 0, -1, 1);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
            GL11.glViewport(0, 0, CMConfig.WINDOW_WIDTH, CMConfig.WINDOW_HEIGHT);

            MenuBar mb = new MenuBar();
            Window w = new Window();
            w.setX(100);
            w.setY(100);
            w.setWidth(300);
            w.setHeight(300);

            Console.init();

            while(!Display.isCloseRequested()){
                KeyboardManager.onUpdate();
                MouseManager.onUpdate();

                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

                for(Integer key : entities.keySet()){
                    entities.get(key).onRender();
                }

                mb.onRender();
                w.onRender();
                Console.onRender();

                Display.update();
                Display.sync(120);
            }

            Display.destroy();

            if(CMConfig.RMV_TEMP_FILES){
                PackageManager.deleteDir(new File("data"));
            }

            System.exit(0);
        }catch(LWJGLException e){
            e.printStackTrace();
        }
    }
}

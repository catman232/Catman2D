package Catman2D.UI;

import Catman2D.Events.GenericListener;
import Catman2D.Events.KeyboardListener;
import Catman2D.Events.KeyboardManager;
import Catman2D.Scripting.ScriptManager;
import Catman2D.Toolbox.CMConfig;
import Catman2D.Toolbox.Toolbox;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.File;

public class Console{

    private static final String TAG = "Console";

    private static int numLines = (CMConfig.WINDOW_HEIGHT / 17) - 1;
    private static Label[] lines;
    private static TextBox tb;
    private static Boolean hasInit = false;
    private static String[] text = new String[numLines];
    private static Boolean visible = false;
    private static KeyboardListener kl;

    public static void init(){
        Toolbox.debugPrint(TAG, numLines + "");

        tb = new TextBox();
        lines = new Label[numLines];

        tb.setX(3);
        tb.setY(CMConfig.WINDOW_HEIGHT - 17);
        tb.setHintText("Enter Command");
        tb.getLabel().setFont("data/roboto_small.fnt", "data/roboto_small_0.png");

        tb.setListener(new GenericListener<Integer>() {
            @Override
            public void onEvent(Integer param) {
                if(param == Keyboard.KEY_RETURN){
                    String[] cmd = tb.getLabel().getText().split(" ");
                    if(cmd.length > 0){
                        if(cmd[0].equalsIgnoreCase("loadscript")){
                            if(cmd.length >= 2){
                                File script = new File(cmd[1]);
                                if(script.exists()){
                                    ScriptManager.doScript(script);
                                }else{
                                    Toolbox.errPrint(TAG, "Invalid File");
                                }
                            }else{
                                Toolbox.errPrint(TAG, "Usage: loadscript (Path)");
                            }
                        }else if(cmd[0].equalsIgnoreCase("do")){
                            if(cmd.length >= 2){
                                ScriptManager.doScript("ConsoleScript", cmd[1]);
                            }else{
                                Toolbox.errPrint(TAG, "Usage: do (Script)");
                            }
                        }
                    }else{
                        printLine("Please enter a command");
                    }
                    tb.getLabel().setText("");
                }
            }
        });

        kl = new KeyboardListener() {
            @Override
            public void onKeyPress(int key) {
                if(key == Keyboard.KEY_GRAVE){
                    toggleVisibility();
                }
            }

            @Override
            public void onKeyRelease(int key) {

            }
        };

        KeyboardManager.addListener(kl);

        for(int x = 0; x < lines.length; x++){
            lines[x] = new Label();
            lines[x].setFont("data/roboto_small.fnt", "data/roboto_small_0.png");
            lines[x].setY((CMConfig.WINDOW_HEIGHT - 34) - (x * 17));
            lines[x].setX(3);
        }

        hasInit = true;
    }

    public static void onUpdate() {

    }

    public static void onRender() {
        if(hasInit){
            if(visible){
                GL11.glColor4f(0.1f, 0.1f, 0.1f, 1.0f);

                GL11.glBegin(GL11.GL_QUADS);
                    GL11.glVertex2f(0.0f, 0.0f);
                    GL11.glVertex2f(CMConfig.WINDOW_WIDTH, 0.0f);
                    GL11.glVertex2f(CMConfig.WINDOW_WIDTH, CMConfig.WINDOW_HEIGHT);
                    GL11.glVertex2f(0.0f, CMConfig.WINDOW_HEIGHT);
                GL11.glEnd();

                tb.onRender();
                for(int x = 0; x < lines.length; x++){
                    if(text[x] != null){
                        lines[x].setText(text[x]);
                    }
                    lines[x].onRender();
                }
            }
        }
    }

    public static void printLine(String msg){
        String[] tempText = text.clone();
        for(int x = 0; x < tempText.length; x++){
            if(x > 0){
                tempText[x] = text[x - 1];
            }
        }
       tempText[0] = msg;
       text = tempText;
    }

    public static void toggleVisibility(){
        if(visible == true){
            visible = false;
            tb.setFocused(false);
        }else{
            visible = true;
            tb.setFocused(true);
        }
    }
}

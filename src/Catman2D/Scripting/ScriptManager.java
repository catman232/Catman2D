package Catman2D.Scripting;

import Catman2D.Toolbox.Toolbox;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ScriptManager {

    public static String TAG = "ScriptManager";

    private static ScriptEngineManager factory;
    private static ScriptEngine engine;

    public static void init(){
        factory = new ScriptEngineManager();
        engine = factory.getEngineByName("JavaScript");
    }

    public static void exposeObject(Object object, String name){
        engine.put(name, object);
    }

    public static void doScript(String name, String script){
        try {
            Toolbox.debugPrint(TAG, "Executing Script '" + name + "'");
            engine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public static void doScript(File script){
        try {
            Toolbox.debugPrint(TAG, "Loading Script from '" + script.getPath() + "'");
            BufferedReader reader = new BufferedReader(new FileReader(script));

            String line;
            String contents = "";
            while((line = reader.readLine()) != null){
                contents = contents + line;
            }

            doScript(script.getName(), contents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

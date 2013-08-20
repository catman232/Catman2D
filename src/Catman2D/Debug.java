package Catman2D;

import Catman2D.Graphics.GraphicsManager;
import Catman2D.Logic.LogicManager;
import Catman2D.Packaging.PackageManager;
import Catman2D.Scripting.Objects.ScriptOutput;
import Catman2D.Scripting.ScriptManager;
import Catman2D.Toolbox.CMConfig;
import Catman2D.Toolbox.Toolbox;

import java.io.File;

public class Debug {

    public static final String TAG = "Main";

    public static void main(String[] args){
        System.setProperty("org.lwjgl.librarypath", new File("native").getAbsolutePath());
        Toolbox.print(TAG, "Init Catman2D v" + CMConfig.ENGINE_VER);
        Toolbox.print(TAG, "------------------");

        //PackageManager.openPackage("packages/main.pack");

        while(PackageManager.isLoading){

        }

        GraphicsManager gm = new GraphicsManager();
        LogicManager.init();

        ScriptManager.init();
        ScriptManager.exposeObject(new ScriptOutput(), "Out");
        ScriptManager.exposeObject(gm, "Graphics");
        ScriptManager.exposeObject(CMConfig.WINDOW_HEIGHT, "windowHeight");


        ScriptManager.doScript(PackageManager.loadFile("test.js"));
    }

}

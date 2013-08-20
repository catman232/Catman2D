package Catman2D.Scripting.Objects;

import Catman2D.Toolbox.Toolbox;

public class ScriptOutput {

    public void print(String tag, String msg){
        Toolbox.print(tag, msg);
    }

    public void debugPrint(String tag, String msg){
        Toolbox.debugPrint(tag, msg);
    }

    public void errPrint(String tag, String msg){
        Toolbox.errPrint(tag, msg);
    }

}

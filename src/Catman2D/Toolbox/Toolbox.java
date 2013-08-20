package Catman2D.Toolbox;

import Catman2D.UI.Console;

public class Toolbox {

    /**
     * Toolbox Class
     *
     * Provides misc methods that don't need their
     * own class.
     */

    public static void print(String tag, String msg){
        if(CMConfig.REG_MSG){
            printFormattedMsg("Normal", tag, msg);
        }
    }

    public static void debugPrint(String tag, String msg){
        if(CMConfig.DEBUG_MSG){
            printFormattedMsg("Debug", tag, msg);
        }
    }

    public static void errPrint(String tag, String msg){
        if(CMConfig.ERR_MSG){
            printFormattedMsg("Error", tag, msg);
        }
    }

    private static void printFormattedMsg(String type, String tag, String msg){
        String output = "[" + type + "][" + tag + "]: " + msg;
        System.out.println(output);
        Console.printLine(output);
    }

}

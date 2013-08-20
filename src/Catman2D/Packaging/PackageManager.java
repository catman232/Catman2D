package Catman2D.Packaging;


import Catman2D.Toolbox.CMConfig;
import Catman2D.Toolbox.Toolbox;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;

public class PackageManager {

    /**
     * PackageManager Class
     *
     * This class is used to load files from .pack files.
     * .pack files are simply .zip files with a different
     * extension. Uses the zip4j library.
     */

    public static final String TAG = "PackageManager";

    public static Boolean isLoading = false;

    public static void openPackage(String name){
        openPackage(name, CMConfig.PACKAGE_EXT_LOC);
    }

    public static void openPackage(String name, String extLocation){
        try {
            isLoading = true;
            ZipFile zfile = new ZipFile(name);
            Toolbox.debugPrint(TAG, "Package File Loaded from '" + name + "'");
            zfile.extractAll(extLocation);
            Toolbox.debugPrint(TAG, "Package File Extracted to '" + extLocation + "'");
            isLoading = false;
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static File loadFile(String name){
        return loadFile(name, CMConfig.PACKAGE_EXT_LOC);
    }

    public static File loadFile(String name, String location){
        Toolbox.debugPrint(TAG, "Loading File '"+ name +"' from Path '"+ location + "/" + name +"'");
        return new File(location + "/" +  name);
    }

    public static void deleteDir(File dir){
        while(dir.listFiles().length > 0){
            if (dir.isDirectory()) {
                for (File f : dir.listFiles()){
                    f.delete();
                }
            }
        }
    }
}

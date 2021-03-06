package com.arrayprolc.shaderspatcher.core;

import java.io.File;

import com.arrayprolc.shaderspatcher.actions.Action;
import com.arrayprolc.shaderspatcher.actions.manager.ActionsManager;
import com.arrayprolc.shaderspatcher.utils.file.UtilZIP;

public class ShadersPatcherCore {
    
    public static String original = "./original.zip";
    
    public static String distribute = "./distribute.txt";

    public static void main(String[] args) {

        File file = new File("./data");
        File modifiedFolder = new File("./data/modified/shaders");
        File originalFolder = new File("./data/original/shaders");
        File newFolder = new File("./data/new/shaders");
        File header = new File("./data/modified/header.txt");
        file.delete();
        file.mkdirs();
        modifiedFolder.mkdirs();
        originalFolder.mkdirs();
        newFolder.mkdirs();
        header.mkdirs();

        ActionsManager.registerActions();
        
        try {
            UtilZIP.extractFolder("./modified.zip", "./data/modified/", null);
        } catch (Exception ex) {
        }
        for(File f : new File(".").listFiles()){
            if(f.getName().contains(".zip") && !f.getName().contains("modified")){
                UtilZIP.extractFolder("./" + f.getName(), "./data/original/", null);
                original = f.getAbsolutePath();
            }
        }
        for(File f : new File(".").listFiles()){
            if(f.getName().contains(".txt")){
                distribute = f.getAbsolutePath();
            }
        }
        String defaultAction = "extract";
        if(args.length == 0){
            for(Action a : ActionsManager.getActions()){
                if(a.getActionName().equals(defaultAction)){
                    a.runAction();
                    return;
                }
            }
            System.out.println("Action " + defaultAction + " not found.");
            System.exit(0);
            return;
        }
        for(Action a : ActionsManager.getActions()){
            if(a.getActionName().equals(args[0])){
                a.runAction();
                return;
            }
        }
        System.out.println("Action " + args[0] + " not found.");
        System.exit(0);
        return;
    }

}

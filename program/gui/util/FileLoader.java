package program.gui.util;

import java.io.File;

public class FileLoader
{
    public static String find(String path, boolean debug)
    {
        File path1 = new File(System.getProperty("user.dir"), path);
        File path2 = new File(System.getProperty("user.dir")).getParentFile() != null
                ? new File(System.getProperty("user.dir")).getParentFile().toPath().resolve(path).toFile()
                : null;

        String finalPath = null;
        if (path1.exists())
            finalPath = path1.getAbsolutePath();
        else if (path2 != null && path2.exists())
            finalPath = path2.getAbsolutePath();
        
        if (debug)
        {
            if (finalPath == null)
                System.err.println("[FileLoaderFinder] Error: File not found -> " + path);
            else System.out.println("[FileLoaderFinder] Path: " + finalPath);
        }

        return finalPath;
    }
}
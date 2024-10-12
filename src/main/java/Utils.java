import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.List;

public class Utils {
    public static String getDefaultDesktopPath() {
        return System.getProperty("user.home") + File.separator + "Desktop";
    }

    public static File[] getNonBlacklistedFiles(File desktopFolder) {
        Config config = Config.getInstance();

        List<String> blacklistedFiles = config.getBlacklistedFiles();
        List<String> blacklistedExtensions = config.getBlacklistedExtensions();

        //Includes all files if no blacklists are provided
        if (blacklistedFiles == null && blacklistedExtensions == null) {
            return desktopFolder.listFiles(File::isFile);
        }

        //Checks if files are blacklisted by name
        if (blacklistedFiles == null) {
            return desktopFolder.listFiles(file -> file.isFile() && !blacklistedExtensions.contains(FilenameUtils.getExtension(file.getName())));
        }

        //Checks if files are blacklisted by extension
        if (blacklistedExtensions == null) {
            return desktopFolder.listFiles(file -> file.isFile() && !blacklistedFiles.contains(file.getName()));
        }

        //Checks if files are blacklisted by either name or extension
        return desktopFolder.listFiles(file -> {
            String fileName = file.getName();
            String fileExtension = FilenameUtils.getExtension(fileName);
            return  file.isFile() && !blacklistedFiles.contains(fileName) && !blacklistedExtensions.contains(fileExtension);
        });
    }

    public static boolean generateFiles() {
        File configFile = new File("config.yml");
        File runFile = new File("run.bat");

        if (configFile.exists() && runFile.exists()) {
            return false;
        }

        if (!configFile.exists()) {
            try {
                generateConfigFile();
            } catch (IOException ioException) {
                System.out.println("Failed to generate config.yml");
                return false;
            }
        }

        if (!runFile.exists()) {
            try {
                generateRunFile();
            } catch (IOException ioException) {
                System.out.println("Failed to generate run.bat");
                return false;
            }
        }
        return true;
    }

    private static void generateConfigFile() throws IOException {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("config.yml");
        if (inputStream != null) {
            try (FileOutputStream outputStream = new FileOutputStream("config.yml")) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    private static void generateRunFile() throws IOException {
        FileWriter writer = new FileWriter("run.bat");
        writer.write("@echo off\n");
        writer.write("java -jar DesktopClearer-1.0.jar\n");
        writer.write("pause\n");
        writer.close();
    }
}

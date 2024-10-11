import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class Config {
    private static Config instance;
    private String desktopPath;
    private String destinationPath;
    private List<String> blacklistedFiles;
    private List<String> blacklistedExtensions;

    public String getDesktopPath() {
        return desktopPath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public List<String> getBlacklistedFiles() {
        return blacklistedFiles;
    }

    public List<String> getBlacklistedExtensions() {
        return blacklistedExtensions;
    }

    public void setDesktopPath(String desktopPath) {
        this.desktopPath = desktopPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public void setBlacklistedFiles(List<String> blacklistedFiles) {
        this.blacklistedFiles = blacklistedFiles;
    }

    public void setBlacklistedExtensions(List<String> blacklistedExtensions) {
        this.blacklistedExtensions = blacklistedExtensions;
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
            loadConfig();
        }
        return instance;
    }

    private static void loadConfig() {
        Yaml yaml = new Yaml();

        //Loads an external config.yml if it exists
        String jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath())
                .getParent();
        String externalConfigFilePath = jarDir + File.separator + "config.yml";

        File externalConfigFile = new File(externalConfigFilePath);

        if (externalConfigFile.exists()) {
            try (InputStream inputStream = new FileInputStream(externalConfigFile)) {
                System.out.println("Loaded external config from " + externalConfigFilePath);
                instance = yaml.loadAs(inputStream, Config.class);
                return;
            } catch (Exception e) {
                System.out.println("Error loading external config: " + e.getMessage());
            }
        }

        //Defaults to the internal config.yml
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.yml")) {
            if (inputStream != null) {
                System.out.println("Loaded default config.");
                instance = yaml.loadAs(inputStream, Config.class);
            } else {
                System.out.println("Internal config not found in JAR.");
            }
        } catch (Exception e) {
            System.out.println("Error loading internal config: " + e.getMessage());
        }
    }
}
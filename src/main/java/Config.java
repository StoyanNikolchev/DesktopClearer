import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class Config {
    private String desktopPath;
    private String destinationPath;
    private List<String> blacklist;

    public String getDesktopPath() {
        return desktopPath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }

    public void setDesktopPath(String desktopPath) {
        this.desktopPath = desktopPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public void setBlacklist(List<String> blacklist) {
        this.blacklist = blacklist;
    }

    public static Config loadConfig(String externalConfigFilePath) {
        Yaml yaml = new Yaml();

        //Loads an external config.yml if it exists
        File externalConfigFile = new File(externalConfigFilePath);

        if (externalConfigFile.exists()) {
            try (InputStream inputStream = new FileInputStream(externalConfigFile)) {
                System.out.println("Loaded external config from " + externalConfigFilePath);
                return yaml.loadAs(inputStream, Config.class);

            } catch (Exception e) {
                System.out.println("Error loading external config: " + e.getMessage());
            }
        }

        //Defaults to the internal config.yml
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.yml")) {
            if (inputStream != null) {
                System.out.println("Loaded default config.");
                return yaml.loadAs(inputStream, Config.class);
            } else {
                System.out.println("Internal config not found in JAR.");
            }
        } catch (Exception e) {
            System.out.println("Error loading internal config: " + e.getMessage());
        }

        return null;
    }
}
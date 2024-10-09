import org.yaml.snakeyaml.Yaml;

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

    public static Config loadConfig(String filePath) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(filePath)) {
            return yaml.loadAs(inputStream, Config.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
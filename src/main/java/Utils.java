import java.io.File;

public class Utils {
    public static String getDefaultDesktopPath() {
        return System.getProperty("user.home") + File.separator + "Desktop";
    }
}

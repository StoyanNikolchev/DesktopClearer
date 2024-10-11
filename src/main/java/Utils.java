import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;

public class Utils {
    public static String getDefaultDesktopPath() {
        return System.getProperty("user.home") + File.separator + "Desktop";
    }

    public static File[] getNonBlacklistedFiles(File desktopFolder, Config config) {
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
}

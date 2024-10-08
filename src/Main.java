import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String DESKTOP_PATH = System.getProperty("user.home") + "\\Desktop";
    private static final String FIRST_SUBFOLDER_PATH = DESKTOP_PATH + "\\Stuff";
    public static final String CURRENT_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    public static final String TARGET_FOLDER_PATH = FIRST_SUBFOLDER_PATH + "\\" + CURRENT_DATE;

    public static void main(String[] args) {
        File desktopFolder = new File(DESKTOP_PATH);

        File[] files = getNonBlacklistedFiles(desktopFolder);

        //Stops the program if there are no eligible files
        if (files == null) {
            System.out.println("No files to move.");
            return;
        }

        File firstSubfolder = new File(FIRST_SUBFOLDER_PATH);
        createDirectoryIfMissing(firstSubfolder);

        File targetFolder = new File(TARGET_FOLDER_PATH);
        createDirectoryIfMissing(targetFolder);

        for (File file : files) {
            try {
                Path newPath = new File(targetFolder, file.getName()).toPath();
                Files.move(file.toPath(), newPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Moved " + file.getName() + " to " + TARGET_FOLDER_PATH);
            } catch (IOException ioException) {
                System.out.println("Could not move " + file.getName() + ": " + ioException.getMessage());
            }
        }
    }

    private static void createDirectoryIfMissing(File firstSubfolder) {
        if (!firstSubfolder.exists()) {
            firstSubfolder.mkdirs();
        }
    }

    private static File[] getNonBlacklistedFiles(File desktopFolder) {
        List<String> blacklist = Arrays.stream(Blacklist.values())
                .map(Blacklist::getFileName)
                .toList();

        return desktopFolder.listFiles(file -> file.isFile() && !blacklist.contains(file.getName()));
    }
}
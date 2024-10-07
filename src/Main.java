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
    public static void main(String[] args) {
        String desktopPath = System.getProperty("user.home") + "\\Desktop";
        File desktopFolder = new File(desktopPath);

        List<String> blacklist = Arrays.stream(Blacklist.values())
                .map(Blacklist::getFileName)
                .toList();

        File[] files = desktopFolder.listFiles(file -> file.isFile() && !blacklist.contains(file.getName()));

        if (files == null || files.length == 0) {
            System.out.println("No files to move.");
            return;
        }

        String firstSubfolderPath = desktopPath + "\\Stuff";
        File firstSubfolder = new File(firstSubfolderPath);

        if (!firstSubfolder.exists()) {
            firstSubfolder.mkdirs();
        }

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String targetFolderPath = firstSubfolderPath + "\\" + currentDate;
        File targetFolder = new File(targetFolderPath);

        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        for (File file : files) {
            try {
                Path newPath = new File(targetFolder, file.getName()).toPath();
                Files.move(file.toPath(), newPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Moved " + file.getName() + " to " + targetFolderPath);
            } catch (IOException ioException) {
                System.out.println("Could not move " + file.getName() + ": " + ioException.getMessage());
            }
        }
    }
}
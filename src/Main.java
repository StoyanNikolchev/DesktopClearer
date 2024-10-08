import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

        int movedFiles = 0;

        for (File file : files) {
            try {
                Path newPath = new File(targetFolder, file.getName()).toPath();

                //Asks for confirmation to overwrite if the file already exists
                if (Files.exists(newPath)) {
                    if (!confirmOverwrite(file)) {
                     continue;
                    }
                }

                Files.move(file.toPath(), newPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.printf("Moved %s to %s%n", file.getName(), TARGET_FOLDER_PATH);
                movedFiles++;
            } catch (IOException ioException) {
                System.out.printf("Could not move %s: %s%n", file.getName(), ioException.getMessage());
            }
        }

        System.out.printf("Moved %d out of %d files.%n", movedFiles, files.length);
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

    private static boolean confirmOverwrite(File file) {
        System.out.println("File " + file.getName() + " already exists in target folder. Overwrite? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("yes")) {
            return true;
        } else if (input.equalsIgnoreCase("no")) {
            return false;
        }

        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
        return confirmOverwrite(file);
    }
}
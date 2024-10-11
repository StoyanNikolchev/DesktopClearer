import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String CURRENT_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    public static void main(String[] args) {
        String jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath())
                .getParent();
        String externalConfigFilePath = jarDir + File.separator + "config.yml";

        Config config = Config.loadConfig(externalConfigFilePath);
        if (config == null) {
            System.out.println("Failed to load configuration.");
            return;
        }

        String desktopPath = config.getDesktopPath();
        if (desktopPath == null) {
            desktopPath = Utils.getDefaultDesktopPath();
        }

        //Defaults a null destination path to the desktop
        String destinationPath = config.getDestinationPath();
        if (destinationPath == null) {
            destinationPath = desktopPath;
        }

        destinationPath = destinationPath + File.separator + CURRENT_DATE;

        File desktopFolder = new File(desktopPath);

        List<String> blacklistedFiles = config.getBlacklistedFiles();
        List<String> blacklistedExtensions = config.getBlacklistedExtensions();

        File[] files;

        if (blacklistedExtensions == null) {
            files = getNonBlacklistedFiles(desktopFolder, blacklistedFiles);
        } else {
            files = getNonBlacklistedFiles(desktopFolder, blacklistedFiles, blacklistedExtensions);
        }

        //Stops the program if there are no eligible files
        if (files == null || files.length == 0) {
            System.out.println("No files to move.");
            return;
        }

        File destinationFolder = new File(destinationPath);
        createDirectoryIfMissing(destinationFolder);

        int movedFiles = 0;

        for (File file : files) {
            try {
                Path newPath = new File(destinationFolder, file.getName()).toPath();

                //Asks for confirmation to overwrite if the file already exists
                if (Files.exists(newPath)) {
                    if (!confirmOverwrite(file)) {
                        continue;
                    }
                }

                Files.move(file.toPath(), newPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.printf("Moved %s to %s%n", file.getName(), destinationPath);
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

    private static File[] getNonBlacklistedFiles(File desktopFolder, List<String> blacklistedFiles, List<String> blacklistedExtensions) {
        return desktopFolder.listFiles(file -> {
            String fileName = file.getName();

            if (file.isFile() && !blacklistedFiles.contains(fileName)) {

                for (String blacklistedExtension : blacklistedExtensions) {
                    if (fileName.endsWith(blacklistedExtension)) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        });
    }

    private static File[] getNonBlacklistedFiles(File desktopFolder, List<String> blacklistedFiles) {
        return desktopFolder.listFiles(file -> file.isFile() && !blacklistedFiles.contains(file.getName()));
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
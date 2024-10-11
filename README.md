# DesktopClearer

DesktopClearer is a customizable Java application that sorts files from your desktop into dated folders at a custom destination while ignoring certain files based on a blacklist. The paths and blacklist can be customized using a `config.yml` file.

## Prerequisites

- Java 17 or higher

## How to Build the Project

1. **Clone the repository**:
    ```bash
    git clone https://github.com/StoyanNikolchev/DesktopClearer.git
    cd DesktopClearer
    ```
2. **Open the Project in IntelliJ**:
    - Open IntelliJ IDEA and choose `File > Open...`.
    - Navigate to the directory where you cloned the project and select the folder to open it.

3. **Set Up the Artifact**:
    - Go to `File > Project Structure`.
    - Under the `Artifacts` section, click the `+` icon and select `JAR > From modules with dependencies`.
    - In the pop-up window, select the `Main` class from the dropdown, and ensure `Extract to the target JAR` is selected.
    - Specify an output directory where you want the JAR file to be created.
    - Click `OK`.

4. **Build the JAR**:
    - Go to `Build > Build Artifacts > Build`.
    - The JAR file will be generated in the specified output directory as `DesktopClearer.jar`.

## Setting Up the Configuration

After building the JAR, you need to create a `config.yml` file that defines the desktop path, destination path, and blacklist.

1. Create a `config.yml` file in the same directory as your JAR file with the following structure:

    ```yaml
    #Example: "C:\\Users\\YourUsername\\Desktop"
    #Defaults to the Windows desktop directory if left blank
    desktopPath:

    #The application will create a subfolder named after the current date
    #Defaults to the set desktop directory if left blank
    destinationPath:

    #Add the full names of the files you don't want the app to touch.
    blacklistedFiles:
      - desktop.ini #Do not delete
	  
    #Example: exe
    blacklistedExtensions:
    ```

    - **desktopPath**: The path to your desktop folder.
    - **destinationPath**: The folder where files should be moved.
    - **blacklistedFiles**: A list of filenames that the application should ignore.
    - **blacklistedExtensions**: A list of file extensions that the application should ignore.

2. Place this `config.yml` file in the same directory as the JAR.

## Running the Application

To run the application and see console output, create a `.bat` file to launch the JAR. Here's how:

1. In the same folder as the JAR, create a new file with the `.bat` extension.

2. Add the following content to the `.bat` file:

    ```batch
    @echo off
    java -jar DesktopClearer.jar
    pause
    ```

3. Save the `.bat` file.

4. Double-click the `.bat` file to run the application.

## Notes

- If the application doesn't find a `config.yml` file in the directory, it will use the default configuration packaged within the JAR.
- When a file with the same name is found in the target destination, the application will ask you for confirmation on whether to override.

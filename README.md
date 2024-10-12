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

## Running the Application

1. Run the JAR file. It will generate a `config.yml` and a `run.bat` and then shutdown.

2. Edit the `config.yml` per your preferences.

3. Double-click the `run.bat` file to run the application.

## Notes

- When a file with the same name is found in the target destination, the application will ask you for confirmation on whether to override.

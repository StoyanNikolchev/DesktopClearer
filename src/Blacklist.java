public enum Blacklist {
    INI_FILE("desktop.ini"),
    RESUME("Stoyan_Nikolchev_Resume.pdf"),
    WARRANTY("warranty_912766681016608146-1.pdf"),
    INVOICE("66bb71073a04d.pdf"),
    NOTES("notes.txt"),
    LYRICS("когато ландкорът беше млад.txt"),
    WORKOUT("workout.pdf");

    private final String fileName;

    Blacklist(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
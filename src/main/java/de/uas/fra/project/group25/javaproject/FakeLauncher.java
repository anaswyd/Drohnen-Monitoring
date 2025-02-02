package de.uas.fra.project.group25.javaproject;

public class FakeLauncher {
    //Second main method that only calls the main method of the real launcher because creating artifacts with JavaFX sometimes doesn't work with main Launcher
    public static void main(String[] args) {
        Launcher.main(args);
    }
}
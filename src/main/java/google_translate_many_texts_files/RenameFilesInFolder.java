package google_translate_many_texts_files;

import java.io.File;

public class RenameFilesInFolder {
    public static void main(String[] args) {
        // Specify the folder path
    	String folderPath = "C:\\Users\\moham\\OneDrive\\Desktop\\Islamic_stories_dataset\\islamic_stories";

        // Create a File object representing the folder
        File folder = new File(folderPath);

        // Get all files in the folder
        File[] files = folder.listFiles();

        if (files != null) {
            // Iterate through each file and rename it
            for (File file : files) {
                // Check if it's a file (not a directory)
                if (file.isFile()) {
                    // Get the original file name
                    String originalName = file.getName();

                    // Create a new file name by appending ".txt"
                    String newName = originalName + ".txt";

                    // Create a File object representing the new file name
                    File newFile = new File(folder, newName);

                    // Rename the file
                    if (file.renameTo(newFile)) {
                        System.out.println("File renamed successfully: " + originalName + " -> " + newName);
                    } else {
                        System.out.println("Failed to rename file: " + originalName);
                    }
                }
            }
        } else {
            System.out.println("No files found in the folder: " + folderPath);
        }
    }
}


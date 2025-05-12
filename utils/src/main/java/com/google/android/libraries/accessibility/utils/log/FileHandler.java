package com.google.android.libraries.accessibility.utils.log;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileHandler {

    @SuppressLint("StaticFieldLeak")
    private static FileHandler fileHandler;

    LogHandler logHandler = createLogHandler();

    final Context context;

    final File file;

    public static void initialize(Context context, String fileName) {
        if (fileHandler == null) {
            fileHandler = new FileHandler(context, fileName);
        }
    }

    public static FileHandler getInstance() {
        if (fileHandler == null) {
            throw new IllegalStateException("FileHandler is not initialized. Call initialize() method first.");
        }
        return fileHandler;
    }

    private FileHandler(Context context, String fileName) {
        this.context = context;
        this.file = getFile(fileName);
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void appendToFile(String data) {
        logHandler.post(() -> {
            try (FileOutputStream fos = new FileOutputStream(file, true);
                 OutputStreamWriter osw = new OutputStreamWriter(fos);
                 BufferedWriter writer = new BufferedWriter(osw)) {
                writer.write(data);
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException("Error appending to file", e);
            }
        });
    }

    void log(String tag, String data) {
        String message = System.currentTimeMillis()+"#"+tag+"#"+data+"\n";
        appendToFile(message.replace("\n", "").replace("\r", ""));
    }

    private File logPath() {
        return context.getFilesDir();
    }

    File getFile(String fileName) {
        File directoryPath = logPath();
        File directory = new File(directoryPath.getAbsolutePath() + File.separator + "arc" + File.separator + "logs/");
        if (!directory.exists())
            directory.mkdirs();
        return new File(directory.getAbsolutePath() + File.separator + fileName);
    }


    private LogHandler createLogHandler() {
        LogThread logThread = new LogThread();
        logThread.start();
        return new LogHandler(logThread.getLooper());
    }

}

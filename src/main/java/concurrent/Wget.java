package concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String whereToDownload;

    public Wget(String url, int speed, String whereToDownload) {
        this.url = url;
        this.speed = speed;
        this.whereToDownload = whereToDownload;
    }

    @Override
    public void run() {
        var file = new File(whereToDownload);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[1024];
            var checkTime = System.currentTimeMillis();
            int totalBytes = 0;
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                totalBytes += bytesRead;
                if (totalBytes >= speed) {
                    long timeTaken = System.currentTimeMillis() - checkTime;
                    if (timeTaken < 1000) {
                        Thread.sleep(1000 - timeTaken);
                    }
                    totalBytes = 0;
                    checkTime = System.currentTimeMillis();
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3 || Integer.parseInt(args[1]) < 0) {
            throw new IllegalArgumentException("Invalid number of arguments or second arg is negative");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String placeToDownload = args[2];
        Thread wget = new Thread(new Wget(url, speed, placeToDownload));
        wget.start();
        wget.join();
    }
}

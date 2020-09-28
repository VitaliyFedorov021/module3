package ua.com.alevel.finder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RunProgram {

    public void action() {
        while(true) {
            ExecutorService service = Executors.newFixedThreadPool(2);
            Future<List<File>> future = service.submit(new Finder());
            service.shutdown();
            List<File> files = new ArrayList<>();
            try {
                files = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            for (File currentFile:
                    files) {
                System.out.println(currentFile.getAbsolutePath());
            }
        }

    }

}

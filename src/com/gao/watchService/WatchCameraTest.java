package com.gao.watchService;

import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User: gaopengxiang
 * Date: 12-4-18
 * Time: 上午10:23
 */
public class WatchCameraTest {
    public static void main(String[] args) {

        Path path = Paths.get("D:\\project");

        WatchCameraFactory watchCameraFactory = new WatchCameraFactory();
        watchCameraFactory.watchCameraMethod(path);
    }
}
class WatchCameraFactory{
    private WatchService watchService;

    public void watchCameraMethod(Path path) {
        try {
            watchService = FileSystems.getDefault().newWatchService();
            register(path, StandardWatchEventKinds.ENTRY_CREATE);

            OUTSIDE:
            while(true){
                WatchKey watchKey = watchService.poll(11, TimeUnit.SECONDS);
                if(watchKey != null && watchKey.isValid()){
                    List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                    for (WatchEvent<?> watchEvent : watchEvents) {
                        Path fileName = (Path) watchEvent.context();
                        WatchEvent.Kind<?> kind = watchEvent.kind();

                        if(kind == StandardWatchEventKinds.OVERFLOW)
                            continue;

                        if(kind == StandardWatchEventKinds.ENTRY_CREATE){
                            Path chilePath = path.resolve(fileName);
                            if (!Files.probeContentType(chilePath).equals("image/jpeg")) {
                                System.out.println("capture the created file at:"+new Date());
                            }else{
                                System.out.println("the captured format is a picture ");
                                break OUTSIDE;
                            }
                        }
                    }
                    boolean isValid = watchKey.reset();
                    if (!isValid) {
                        break;
                    }
                }else{
                    System.out.println("this watchKey is invalid.");
                    break;
                }
            }
            watchService.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void register(Path path,WatchEvent.Kind<Path> kind){
        try {
            path.register(watchService,kind);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.unisa.raspitesi.api;

import com.unisa.raspitesi.configuration.EventPublisherService;
import com.unisa.raspitesi.model.Read;
import com.unisa.raspitesi.model.ReadEvent;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Component
public class ReadComponent implements DisposableBean, Runnable {

    private Thread thread;
    private volatile boolean flag = true;


    public ReadComponent(){
        this.thread = new Thread(this);
        this.thread.start();
    }


    @Override
    public void run() {
        while(flag == true){

            Read read = null;
            String line = "";
            ProcessBuilder pb = new ProcessBuilder("python", this.getClass().getResource("/readNoT.py").getPath());
            try {
                Process p = pb.start();

                BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
                line = bfr.readLine();

                while ((line = bfr.readLine()) != null) {
                    System.out.println(line);
                    if (!line.equals("No card")) {
                        read = new Read(line);
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            if(read != null){
                ReadEvent event = new ReadEvent(read);
                EventPublisherService.eventPublisherService.publishEvent(event);
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    @Override
    public void destroy() throws Exception {

    }
}

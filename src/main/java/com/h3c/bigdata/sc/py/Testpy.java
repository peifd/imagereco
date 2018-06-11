package com.h3c.bigdata.sc.py;

import java.io.*;

public class Testpy {
    public static void main(String[] args) {
        String curPa = System.getProperty("user.dir");
        String command = curPa + "/image/hello.py";

        String exe = "python";
        String[] cmdArr = new String[]{exe, command};

        try {
            Process pro = Runtime.getRuntime().exec(cmdArr);
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();
            pro.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String f = "5.jpg";
        String aa = f.split("\\.")[0];
        System.out.println(aa);
    }
}

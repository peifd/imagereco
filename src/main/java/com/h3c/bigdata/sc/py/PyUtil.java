package com.h3c.bigdata.sc.py;

import com.h3c.bigdata.sc.model.FileUp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PyUtil {
    public static FileUp calPython(String command, String parm) {
        FileUp result = new FileUp();
        System.out.println("command: " + command);
        System.out.println("parm: " + parm);
        String[] cmdArr = new String[]{"python", command, "--image", parm};
        try {
            Process pro = Runtime.getRuntime().exec(cmdArr);
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                result.setContent(line);
            }
            br.close();
            pro.waitFor();
            String fileName = parm.substring(parm.lastIndexOf("/") + 1);
            result.setFilePath(fileName.split("\\.")[0]);
            return result;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}

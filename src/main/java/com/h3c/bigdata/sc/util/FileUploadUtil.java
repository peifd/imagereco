package com.h3c.bigdata.sc.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

public class FileUploadUtil {
    public static String getUploadFilePath(HttpServletRequest request) {
        InputStream in = null;
        FileOutputStream out = null;
        FileItem fileItem;
        String savePath;

        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(request.getSession().getServletContext().getRealPath("/WEB-INF/temp")));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(1024 * 1024 * 200);
            upload.setHeaderEncoding("UTF-8");
            fileItem = upload.parseRequest(request).get(0);
            String fileName = fileItem.getName();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            String relaPath = "upload" + "/" + fileName;

            savePath = request.getSession().getServletContext().getRealPath("/WEB-INF") + "/" + relaPath;

            if (!isFileExist(savePath)) {
                in = fileItem.getInputStream();
                out = new FileOutputStream(savePath);
                byte[] buffer = new byte[1024];
                while (-1 != (in.read(buffer))) {
                    out.write(buffer);
                }
            }

            //返回相对路径
            return relaPath;
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
//
//    public static void copyFile(String src, String dst) {
//        InputStream input = null;
//        OutputStream output = null;
//        try {
//
//            input = new FileInputStream(new File(src));
//            output = new FileOutputStream(new File(dst));
//
//            byte[] buf = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = input.read(buf)) > 0) {
//                output.write(buf, 0, bytesRead);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != input) {
//                    input.close();
//                }
//                if (null != output) {
//                    output.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private static boolean isFileExist(String savePath) {
        File file = new File(savePath);
        return file.exists();
    }
}

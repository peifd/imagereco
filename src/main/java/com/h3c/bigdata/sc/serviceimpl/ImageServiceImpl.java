package com.h3c.bigdata.sc.serviceimpl;

import com.h3c.bigdata.sc.model.FileUp;
import com.h3c.bigdata.sc.py.PyUtil;
import com.h3c.bigdata.sc.service.ImageService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service("ImageService")
public class ImageServiceImpl implements ImageService {

    public static final String CUR_PA = System.getProperty("user.dir");
    public static final String WEB_PA = "src/main/webapp/WEB-INF/";

    @Override
    public String imageHandle(String filePath, HttpServletRequest request) {
        //图片处理
        String command = CUR_PA + "/image/Image_transmission_java.py";
        FileUp con = PyUtil.calPython(command, WEB_PA + filePath);
        return "Image_Transmission/" + con.getFilePath() + ".png";
    }

    @Override
    public String imageRecoginze(String filePath, HttpServletRequest request) {
        //识别区域
        String command = CUR_PA + "/image/ROI_Find_java.py";
        FileUp con = PyUtil.calPython(command, WEB_PA + filePath);
        return "ROI_Find/" + con.getFilePath() + ".png";
    }

    @Override
    public FileUp contentReco(String filePath, String batchPy, HttpServletRequest request) {
        //内容识别
        String command = CUR_PA + "/image/" + batchPy;
        FileUp con = PyUtil.calPython(command, WEB_PA + filePath);
        String pyName = batchPy.split("\\.")[0];

        String suffix = ".png";
        String prefix = "";
        if ("Invoice_code_java".equalsIgnoreCase(pyName)) {
            prefix = "Invoice_code";
            suffix = "_code.png";
        } else if ("Invoice_date_java".equalsIgnoreCase(pyName)) {
            prefix = "Invoice_date";
            suffix = "_date.png";
        } else if ("Invoice_number_java".equalsIgnoreCase(pyName)) {
            prefix = "Invoice_number";
            suffix = "_number.png";
        } else if ("Invoice_value_java".equalsIgnoreCase(pyName)) {
            prefix = "Invoice_value";
            suffix = "_value.png";
        } else if ("Invoice_buy_id_java".equalsIgnoreCase(pyName)) {
            prefix = "Invoice_buy_id";
            suffix = "_buy_3.png";
        } else if ("Invoice_buy_name_java".equalsIgnoreCase(pyName)) {
            prefix = "Invoice_buy_name";
            suffix = "_buy_4.png";
        }
        con.setFilePath(prefix+ "/" + con.getFilePath() + suffix);

        return con;
    }
}

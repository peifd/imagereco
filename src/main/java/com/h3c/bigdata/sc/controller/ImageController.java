package com.h3c.bigdata.sc.controller;

import com.h3c.bigdata.sc.model.FileUp;
import com.h3c.bigdata.sc.service.ImageService;
import com.h3c.bigdata.sc.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ImageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity upload(HttpServletRequest request) {
        LOGGER.info("[FileUploadController] begin upload file");
        String filePath = FileUploadUtil.getUploadFilePath(request);
        FileUp file = new FileUp();
        file.setFilePath(filePath);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    //图片处理
    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    public ResponseEntity handle(@RequestBody FileUp f, HttpServletRequest request) {
        LOGGER.info("[FileUploadController] begin handle file");
        String imageHandle = imageService.imageHandle("upload/" + f.getFilePath(), request);
        FileUp file = new FileUp();
        file.setFilePath(imageHandle);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    //区域识别
    @RequestMapping(value = {"/recognize"}, method = RequestMethod.POST)
    public ResponseEntity recoginize(@RequestBody FileUp f, HttpServletRequest request) {
        LOGGER.info("[FileUploadController] begin recoginize file");
        String imageReco = imageService.imageRecoginze("Image_Transmission/" + f.getFilePath(), request);
        FileUp file = new FileUp();
        file.setFilePath(imageReco);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    //内容识别
    @RequestMapping(value = {"/content"}, method = RequestMethod.POST)
    public ResponseEntity content(@RequestBody FileUp f, HttpServletRequest request) {
        LOGGER.info("[FileUploadController] begin recoginize file");
        FileUp content = imageService.contentReco("Image_Transmission/" + f.getFilePath(), f.getContent(), request);
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

}

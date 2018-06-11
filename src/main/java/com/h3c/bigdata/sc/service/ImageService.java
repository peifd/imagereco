package com.h3c.bigdata.sc.service;

import com.h3c.bigdata.sc.model.FileUp;

import javax.servlet.http.HttpServletRequest;

public interface ImageService {

    String imageHandle(String filePath, HttpServletRequest request);

    String imageRecoginze(String filePath, HttpServletRequest request);

    FileUp contentReco(String filePath, String batchPy, HttpServletRequest request);
}

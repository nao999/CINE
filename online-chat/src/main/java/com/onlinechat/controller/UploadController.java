package com.onlinechat.controller;

import com.onlinechat.base.RestResponse;
import com.onlinechat.config.OssProperties;
import com.onlinechat.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : UploadController
 * @Description :
 * @Author : y
 * @Date: 2021-11-05 10:48
 */

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {
    @Autowired
    private OssProperties ossProperties;

    @PostMapping("/uploadImg")
    public RestResponse<String> uploadImg(@RequestParam("image") MultipartFile uploadImg) throws IOException {

        RestResponse<String> result = Utils.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                uploadImg.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                uploadImg.getOriginalFilename());


        return result;
    }
}

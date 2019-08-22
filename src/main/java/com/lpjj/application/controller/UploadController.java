package com.lpjj.application.controller;


import com.lpjj.application.annotation.Timer;
import com.lpjj.application.base.CodeCache;
import com.lpjj.application.entity.FDS_faceDB.FDS_Facedb;
import com.lpjj.application.entity.FDS_faceDB.FDS_Picture;
import com.lpjj.application.entity.User;


import com.lpjj.application.entity.result.Result;
import com.lpjj.application.service.UploadService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * com.lpjj.application.controller
 * 黄新乐
 * 2019/6/21
 */

@RestController
@RequestMapping("/upload")
@CrossOrigin
@Slf4j
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Value("${fds.username}")
    private String name;

    @Value("${fds.password}")
    private String password;


    /**
     * 图片上传接口
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @Timer

    public Result upload(HttpServletRequest request, HttpSession session) throws NullPointerException {
        Result result = null;
        //图片编码后的大字符串以;拼接的
        String imageBase64s = request.getParameter("imageBase64s");
        String device_id = request.getParameter("device_id");
        result = uploadService.ImageRecognition(imageBase64s, device_id);
        return result;
    }


    /**
     * 向FDS平台添加识别库
     */
    @RequestMapping(value = "/facedb")
    public String facedb(@RequestBody FDS_Facedb fds_facedb){
        fds_facedb.setType(1);
        fds_facedb.setEncrypt(0);
        fds_facedb.setClassify(0);
        //添加
        log.info("fds_facedb -- {}",fds_facedb);


        return uploadService.facedb(fds_facedb);
    }

    /**
     * FDS平台删除识别库
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public  void delete(int id){
        uploadService.delete(id);
    }

    /**
     * 向比对库中添加图片
     */
    @RequestMapping(value = "/picture")
    /*, int repository_id   ,String name*/
    public String picture(File file) throws IOException {
//        String picture_image_content_base64 = Base64Utils.encodeToString(file.getBytes());
        FDS_Picture fds_picture = FDS_Picture.builder()
//                .picture_image_content_base64(picture_image_content_base64)
                .name(name)
                .build();
        return   uploadService.picture(fds_picture);
    }
}
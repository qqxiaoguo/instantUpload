package com.lpjj.application.service;

import com.lpjj.application.entity.FDS_faceDB.FDS_Facedb;
import com.lpjj.application.entity.FDS_faceDB.FDS_Picture;
import com.lpjj.application.entity.User;
import com.lpjj.application.entity.faceDB.FaceDB;
import com.lpjj.application.entity.login.Login;
import com.lpjj.application.entity.parameters.JsonRootBean;
import com.lpjj.application.entity.picturesync.Picturesync;
import com.lpjj.application.entity.result.Result;
import com.lpjj.application.entity.retrieval.RootBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Future;

/**
 * com.lpjj.application.service
 * 黄新乐
 * 2019/6/21
 */
public interface UploadService {

    RootBean getRootBean(Picturesync picturesync);
    /**
     * 同步注册人脸
     *
     * @return
     */
    Picturesync picturesync(String image_base64);

    /**
     * 人脸检索
     *
     * @return
     */
    JsonRootBean retrieval(RootBean rootBean);


    Result ImageRecognition(String base64_image, String device_id);

    /**
     * 添加人脸库
     * @param fds_facedb
     * @return
     */
    String facedb(FDS_Facedb fds_facedb);

    /**
     * 向库中添加图片
     * @param fds_picture
     * @return
     */
    String picture(FDS_Picture fds_picture);

           void delete(int id);
}

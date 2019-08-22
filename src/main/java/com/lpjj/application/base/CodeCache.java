package com.lpjj.application.base;

import com.alibaba.fastjson.JSON;
import com.lpjj.application.entity.User;
import com.lpjj.application.entity.faceDB.FaceDB;
import com.lpjj.application.entity.faceDB.Results;
import com.lpjj.application.entity.login.Login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 项目启动登陆并进行库查询
 */
@Component
@Slf4j
public class CodeCache {

    public static Map<String, Object> codeMap = new HashMap<String, Object>();

    @Autowired
    private RestTemplate restTemplate;


    @Value("${fds.username}")
    private String name;

    @Value("${fds.password}")
    private String password;

    @Value("${fds.ip}")
    private String ip;



    private long sessionID;

    @PostConstruct
    public void init() {
        //存储设备id的集合
        List<Integer> repository_ids = new ArrayList<Integer>();

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        long login = login(user);
        this.sessionID = login;
        log.info("sessionID --  {}", login);
        FaceDB faceDB = findFaceDB();
        log.info("图像库数据 ---  {}", faceDB);

        List<Results> results = faceDB.getResults();
        for (Results result : results) {
            log.info("检索库ID -- {} ", result.getId());
            repository_ids.add(result.getId());
        }
        codeMap.put("session_ID", login);
        codeMap.put("repository_ids", repository_ids);
    }


    /**
     * 查询图像库
     *
     * @return
     */
    public FaceDB findFaceDB() {
        HttpEntity<Object> entity = getStringHttpEntity(null);
        String message = restTemplate.exchange("http://" + ip + ":22000/fds/config/facedb", HttpMethod.GET, entity, String.class).getBody();
        FaceDB faceDB = JSON.parseObject(message, FaceDB.class);
        return faceDB;
    }

    /**
     * 登陆
     *
     * @param user 用户对象
     * @return
     */
    public long login(User user) {
        String jsonString = JSON.toJSONString(user);
        String message = restTemplate.postForObject("http://" + ip + ":22000/fds/global/login", jsonString, String.class);
        Login login = JSON.parseObject(message, Login.class);
        return login.getSession_id();
    }

    /**
     * 构建请求头
     *
     * @param param JSON串
     * @return
     */
    private HttpEntity<Object> getStringHttpEntity(Object param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Cookie", "session_id=" + this.sessionID + ";isGlobal=0;face_platform_session_id=" + this.sessionID + ";");
        if (!"".equals(param) && param != null) {
            String jsonString = JSON.toJSONString(param);
            return new HttpEntity<>(jsonString, headers);
        }
        return new HttpEntity<>(null, headers);
    }
}
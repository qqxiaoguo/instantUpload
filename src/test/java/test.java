
import com.lpjj.application.UploadApplication;

import com.lpjj.application.service.UploadService;
import com.lpjj.application.xin.PinCutTest;


import com.lpjj.application.xin.PinService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.*;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



import java.io.*;



/**
 * PACKAGE_NAME
 * 黄新乐
 * 2019/6/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UploadApplication.class)
@Slf4j
public class test {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private  UploadService uploadService;
    @Autowired
    private PinService pinService;

    @Test
    public void test1() throws Exception{
        int a[] = {5,100,32,45,21,67,32,68,41,99,13,71};
        int  max=a[0];
        for (int i = 0; i < a.length; i++) {
             if (a[i]>max){
                 max = a[i];
             }
        }
        System.out.println(max);
    }

    @Test
    public void test4() throws Exception {
        File file = new File("E:\\010017_院_112_0017.jpg");
        String imageBase64s = ImageToBase64(file);
        String url = "http://39.97.104.66:8089/upload/image";
        HttpHeaders headers = new HttpHeaders();
//  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//  也支持中文
        params.add("imageBase64s", imageBase64s);
        params.add("device_id", "123123123");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        for (int i = 0; i < 5; i++) {
            //  执行HTTP请求
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//  输出结果
            System.out.println(response.getBody());
        }
    }

    public String ImageToBase64(File file) {
        InputStream is = null;
        byte[] date = null;
        try {
            is = new FileInputStream(file);
            date = new byte[is.available()];
            is.read(date);
            String image = Base64Utils.encodeToString(date);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    //删除多余的id
    @Test
    public  void deletID(){
        int []  deid={116,118,120,122,124,126,128};
        for (int i=0;i<deid.length;i++) {
           uploadService.delete(deid[i]);

            System.out.println("删除"+deid[i]);
        }

     }
  @Test
    public void guo(){
         pinService.test();

  }
}



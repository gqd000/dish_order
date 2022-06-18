package com.nanyan.dishorder.controller;

import com.nanyan.dishorder.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.http.HttpResponse;
import java.util.UUID;

/**
 * 文件上传下载
 */

@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${dish_order.path}")
    private String basePath;
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file)
    {
        String OriginalFilename = file.getOriginalFilename();
        String substring = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));
        //使用UUID随机生成文件名，防止因为文件名相同造成文件覆盖
        String fileName = UUID.randomUUID().toString()+substring;

        File dir = new File(basePath);
        if(!dir.exists())
        {
            dir.mkdirs();
        }

        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response)
    {
        String fileName = basePath + name;
        try {
            //将图片添加到输入流
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));

            ServletOutputStream outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1)
            {
                outputStream.write(bytes,0,len);
            }
            outputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

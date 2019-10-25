package com.jiejie.mall.filesys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@RestController
public class FileController {

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {

       byte [] data = file.getBytes();
       String fileName = file.getOriginalFilename();
       String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
       String newFileName =  UUID.randomUUID().toString().replaceAll("-","")+suffix;
       FileOutputStream out  = new FileOutputStream("d:/"+newFileName);
       out.write(data);
       out.flush();
       out.close();
       return newFileName;
    }

    /**
     * 下载文件
     * @param filename
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(@RequestParam ("filename")String filename, HttpServletResponse response)throws  IOException{

        FileInputStream in = new FileInputStream("d:/"+filename);
        OutputStream out = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setHeader("Content-Type","application/octet-stream");
        int len  = 0;
        byte [] buff = new byte[1024];
        while((len=in.read(buff))!=-1){
            out.write(buff,0,len);
        }
        out.flush();
        out.close();

    }

}

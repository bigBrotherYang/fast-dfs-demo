package com.yangml.controller;

import com.yangml.pojo.FastDFSFile;
import com.yangml.pojo.Fds;
import com.yangml.service.FdsService;
import com.yangml.utils.FastDFSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Slf4j
@Controller
public class FdsController {
    @Autowired
    private FdsService fdsService;
    @RequestMapping("/test")
    public String test(){
//        List<Fds> list = fdsService.list();
//        for (Fds f:list
//             ) {
//            System.out.println(f);
//        }
        return "main";
    }
    @ResponseBody
    @RequestMapping("/shangchaun")
    public Fds saveFile(@RequestParam(value="file",required=false)MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath={};
        //获取文件名
        String fileName=multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream=multipartFile.getInputStream();
        if(inputStream!=null){
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        try {
            fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs
        } catch (Exception e) {
            log.error("upload file eption!Exc",e);
        }
        if (fileAbsolutePath==null) {
            log.error("upload file failed,please upload again!");
        }
        String path=FastDFSClient.getTrackerUrl()+"/"+fileAbsolutePath[0]+ "/"+fileAbsolutePath[1];
        Fds fds = new Fds();
        fds.setFdtDis(fileName);
        fds.setFdtVal(path);
        fdsService.save(fds);
        return fds;
    }
}

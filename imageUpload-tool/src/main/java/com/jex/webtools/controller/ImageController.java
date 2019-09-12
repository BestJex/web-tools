package com.jex.webtools.controller;

import com.jex.webtools.common.ResponseMessage;
import com.jex.webtools.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/image")
public class ImageController {


    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final ResourceLoader resourceLoader;

    @Autowired
    public ImageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName( "/admin/image/index");
        return mv;
    }

    @Value("${web.upload-path}")
    private String path;



    /**
     *
     * @param file 上传的文件
     * @return
     */
    @ResponseBody
    @RequestMapping("/upload")
    public ResponseMessage upload(@RequestParam("file")MultipartFile file){

        String fileName=file.getOriginalFilename();
        String  newFileName = FileUtils.upload(file, path, fileName);

        if(newFileName.equals(null)){
            logger.info("上传失败,newFileName：" + newFileName);
            return new ResponseMessage(1,"上传失败");

        }
        return ResponseMessage.initializeSuccessMessage(newFileName);
    }

    /**
     * 显示图片
     * @param fileName 文件名
     * @return
     */

    @RequestMapping("show")
    public ResponseEntity show(String fileName){

        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }




}
package com.jex.webtools.controller;

import com.jex.webtools.utils.CodeImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @Description: 类描述
 **/
@Controller
public class QrCodeController {


    @GetMapping("getQrCode")
    public void getQrCode(HttpServletResponse response, @RequestParam(defaultValue = "undefined") String content) {
        try {
            OutputStream outputStream = response.getOutputStream();
            CodeImageUtil.writeToStream(content, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }
}

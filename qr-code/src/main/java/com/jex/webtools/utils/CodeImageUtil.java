package com.jex.webtools.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 **/

public class CodeImageUtil {
    /**
     * 默认二维码宽度
     */
    public static final int WIDTH = 300;
    /**
     * 默认二维码高度
     */
    public static final int HEIGHT = 300;
    /**
     * 默认二维码文件格式
     */
    public static final String FORMAT = "png";
    /**
     * 默认二维码文件格式
     */
    public static final Map<EncodeHintType, Object> HINTS = new HashMap<>();

    //初始化编码格式等参数
    static {
        // 字符编码
        HINTS.put(EncodeHintType.CHARACTER_SET, "utf-8");
        HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 二维码与图片边距
        HINTS.put(EncodeHintType.MARGIN, 2);
    }

    /**
     * @param content      二维码内容即要存储在二维码中的内容
     * @param outputStream 输出流
     * @throws WriterException
     * @throws IOException
     */
    public static void writeToStream(String content, OutputStream outputStream) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, HINTS);
        MatrixToImageWriter.writeToStream(bitMatrix, FORMAT, outputStream);
    }


    public static void main(String[] args) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 生成二维码图片
        try {
            CodeImageUtil.writeToStream("图片或者连接", out);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        InputStream in = new ByteArrayInputStream(out.toByteArray());
        //将生成的二维码写入图片，也可直接使用流
        String filePath = "F:\\qr-code\\qr_code.png";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            int length;
            byte[] b = new byte[1024];
            while ((length = in.read(b)) > 0) {
                fos.write(b, 0, length);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

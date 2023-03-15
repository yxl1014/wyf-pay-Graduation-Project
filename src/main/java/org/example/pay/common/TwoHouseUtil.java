package org.example.pay.common;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author yxl
 * @date 2023/3/15 下午1:15
 */

@Component
public class TwoHouseUtil {

    /**
     * 生成二维码
     *
     * @param d 需要生成二维码的数据
     * @return 返回一个二维码图片的byte数组
     */
    public byte[] get2WHouse(String d) throws IOException {
        InputStream inputStream = getInputStream(d);
        byte[] data = new byte[1024];
        int len = 0;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inputStream.read(data)) != -1) {
            outStream.write(data, 0, len);

        }
        return outStream.toByteArray();
    }

    /**
     * 获得服务器端数据，以InputStream形式返回
     *
     * @return
     * @throws IOException
     */
    private InputStream getInputStream(String d) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL("https://api.pwmqr.com/qrcode/create/?url=" + d);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置连接网络的超时时间
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            // 设置本次http请求使用get方式请求
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                // 从服务器获得一个输入流
                inputStream = httpURLConnection.getInputStream();
            }
        } catch (MalformedURLException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inputStream;
    }

}
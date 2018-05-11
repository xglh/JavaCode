package jmeterClass;

/**
 * Created by xglh on 2017-7-21.
 */


import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    //方法重写，实现headers默认为空
    public static String sendHead(String url,String host){
        String[][] headers = {};
        return  HttpRequest.sendHead(url,host,headers);
    }

    /**
     * 发送http head请求,只接受响应头，不接受响应内容
     * @param url 请求的url
     * @param host  代理,支持形如 192.168.2.235 192.168.2.2.235:8083的形式,为""表示不设代理
     * @param headers 请求头,二维String数组形式
     * @return
     */
    public static String sendHead(String url,String host,String[][] headers) {
        String result = "";

        BufferedReader in = null;
        String code = "";
        try {
            URL realUrl = new URL(url);
            URLConnection connection;
            String hostname = "";
            int  port = 0;
            if(host != ""){
                hostname = host;
                port = 80;
                if (host.indexOf(":") >= 0){
                    hostname = host.substring(0,host.indexOf(":"));
                    port = Integer.parseInt(host.substring(host.indexOf(":") + 1,host.length()));
                }
                InetSocketAddress addr = new InetSocketAddress(hostname,port);
                Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
                connection = realUrl.openConnection(proxy);
            }
            else{
                connection = realUrl.openConnection();
            }
            // 打开和URL之间的连接
            HttpURLConnection httpUrlConnection  =  (HttpURLConnection) connection;
            //发送head请求,不接受返回内容，只接受响应头
            httpUrlConnection.setRequestMethod("HEAD");
            // 设置请求头
            for(int i = 0;i<headers.length;i++){
                connection.setRequestProperty(headers[i][0], headers[i][1]);
            }

            // 建立实际的连接
            httpUrlConnection.connect();

            //获取响应码
            code = new Integer(httpUrlConnection.getResponseCode()).toString();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            httpUrlConnection.disconnect();
        } catch (Exception e) {

        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return code;
    }


}
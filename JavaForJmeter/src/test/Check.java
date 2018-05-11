package test;

import jmeterClass.HttpRequest;
import jmeterClass.JHelper;

public class Check {

    //CC视频防盗链
    public static void checkCC() {
        String uri = "/video/hls/mp4/1/test1/yf/playlist.m3u8";
        //String uri = "/2.ts";
        String url = "http://liuhuitest.com" + uri;

        String salt = "lionheart@cc";
        String time = JHelper.getTime(-60 * 80) + "";

        String key = salt + time + uri;
        System.out.println("key = " + key);
        String md5 = JHelper.getMD5(key);
        System.out.println("md5 = " + md5);
        url += "?t=" + time + "&key=" + md5;
        System.out.println("url = " + url);
    }

    //芒果tv ott
    public static void checkHntv() {

        String authkey = "hcclw";
        String uri = "/video/hls/mp4/testfile/3.mp4";
        String timeHex = JHelper.radixToHex(JHelper.getTime(-60 * 60 * 4));
        int arange = 10;

        String md5_str = authkey + uri + timeHex + arange;
        System.out.println("md5_str = " + md5_str);
        String md5 = JHelper.getMD5(md5_str);

        String url = "http://liuhuitest.com/" + md5 + "/" + timeHex + arange + uri;
        System.out.println("url = " + url);
    }

    //通用防盗链
    public static void checkCommon() {
        String domain = "vodpub.yf.nfile.cn";
        String uri = "/540p/22_9/9fe4/1144/9fe41144db584263de2253940b5adf43.m3u8";
        String secret_key = "";
        String expire = JHelper.getTime(-60 * 60) + "";
        String str = secret_key + '&' + expire + '&' + uri;

        String hash_str = JHelper.getMD5(str);
        String sstring = hash_str.substring(12, 20);
        String arg_upt = sstring + expire;
        String url = "http://" + domain + uri + "?upt=" + arg_upt;
        System.out.println("url = " + url);
    }

    //阿里防盗链
    public static void checkAli_A() {
        String domain = "test042204.com";
        String uri = "/11.txt";
        String secret_key = "123456";
        String etime = JHelper.getTime(-60 * 60) + "";
        String rand = "0";
        String uid = "0";
        String sstring = uri + "-" + etime + "-" + rand + "-" + uid + "-" + secret_key;

        String hashvalue = JHelper.getMD5(sstring);
        String arg_auth_key = etime + "-" + rand + "-" + uid + "-" + hashvalue;

        String url = "http://" + domain + uri + "?auth_key=" + arg_auth_key;
        System.out.println("url = " + url);
    }

    //七牛防盗链
    public static void checkQiniu() {
        String domain = "lh006.com";
        String uri = "/1.txt";
        String secret_key = "123456";
        String etime = JHelper.getTime(60 * 70) + "";
        String arg_t = JHelper.redixConvert(etime, 16);
        String sstring = (secret_key + uri + arg_t).toLowerCase();

        String arg_sign = JHelper.getMD5(sstring);

        String url = "http://" + domain + uri + "?t=" + arg_t + "&sign=" + arg_sign;
        System.out.println("url = " + url);
    }

    //七牛防盗链_2
    public static void checkQiniu_2() {
        String[] urls = new String[4];
        String domain = "qn.acgvideo.com";

        //String uri = "/autotest/mp4_drag.mp4";
        String uri = "/autotest/flv_drag.flv";
        //String uri = "/video/hls/mp4/testfile/2.mp4";
        String secret_key = "pbO6PY=@zaXW(!h*AQYR8eSpxWC_fWB{vQ|=(8;(+ap_pW|`tTgV9%cRHl!aMK:g";
        String qnTime = JHelper.getTime(60 * 60) + "";
        String rate = "" + 0;
        String oi = "3078818617";
        String stime = "" + 0;
        String etime = "" + 10;  //678630825
        //String platform = "pc";
        String md5_str = qnTime + uri + oi + rate + secret_key + stime + etime;
        String md5_str1 = qnTime + uri + oi + secret_key + stime + etime;
        String md5_str2 = qnTime + uri + oi + rate + secret_key;
        String md5_str3 = qnTime + uri + oi + secret_key;

        String md5 = JHelper.getMD5(md5_str);
        String md5_1 = JHelper.getMD5(md5_str1);
        String md5_2 = JHelper.getMD5(md5_str2);
        String md5_3 = JHelper.getMD5(md5_str3);


        urls[0] = "http://" + domain + uri + "?qnTime=" + qnTime +
                "&qnSecret=" + md5 + "&oi=" + oi + "&rate=" + rate + "&stime="
                + stime + "&etime=" + etime;

        urls[1] = "http://" + domain + uri + "?qnTime=" + qnTime +
                "&qnSecret=" + md5_1 + "&oi=" + oi + "&stime="
                + stime + "&etime=" + etime;

        urls[2] = "http://" + domain + uri + "?qnTime=" + qnTime
                + "&qnSecret=" + md5_2 + "&oi=" + oi + "&rate=" + rate;

        urls[3] = "http://" + domain + uri + "?qnTime=" + qnTime
                + "&qnSecret=" + md5_3 + "&oi=" + oi;

        String host = "192.168.2.235";
        String[][] headers = {{"name","liuhui"},{"age","18"}};
        for(int i= 0;i<urls.length;i++){
            String code = HttpRequest.sendHead(urls[i],host,headers);
            System.out.println( "状态码:" + code + ",链接:" +urls[i]);
        }
    }

    //酷我防盗链
    public static void checkKuWo() {
        //http://ar.player.ra02.sycdn.kuwo.cn/resource/n1/128/17/99/1566821832.mp3
        String domain = "liuhui.com";
        String uri = "/v2/nav/1.ts";
        String secret_key = "kuwo_web@1906";
        String etime = JHelper.getTime(-60 * 70) + "";
        String arg_t = JHelper.redixConvert(etime, 16);

        String md5_str = secret_key + uri + arg_t;

        String md5 = JHelper.getMD5(md5_str);

        String url = "http://" + domain + "/" + md5 + "/" + arg_t + uri;
        System.out.println("url = " + url);
    }

    //网易防盗链
    public static void checkWangYi() {

        String domain = "m12.music.126.net";
        String uri = "/ymusic/2857/579d/f988/a4371c36f070d92aedf342ae4674df85.flac";
        String secret_key = "dc7&63$1TB73c@l";
        String time = JHelper.stampToDate(JHelper.getTime(-60 * 10), "yyyyMMddHHmmss");
        String args = "u=xrcR/5Lr2/Pd6MwI0QnLEg==";
        String md5_str = secret_key + uri + time;
        String md5 = JHelper.getMD5(md5_str);

        String url = "http://" + domain + "/" + time + "/" + md5 + uri + "?" + args;
        System.out.println("url = " + url);
    }

    //咪咕防盗链
    public static void checkMigu() {

        String domain = "live.cmvideo.yfcdn.net:8088";
        //String domain = "vod.cmvideo.yfcdn.net:8088";
        //String domain = "dlsc.hcs.cmvideo.cn";
        //String uri = "/test.mp4.m3u8";test.3gp.m3u8;2.ts;02.m3u8;11.3gp;22.mp4
        String uri = "/envivo_x/SD/cctv13/711/index.m3u8";
        String secret_key = "71@uGiM";
        String time = JHelper.stampToDate(JHelper.getTime(0), "yyyyMMddHHmmss");
        //String args = "msisdn=&spid=600058&netType=4&sid=5500001775&pid=2028596353&ProgramID=617020738";
        String args = "msisdn=13817638862&sid=2202208864&Channel_ID=0117_43040206-00002-201800000000027&pid=2028597139&spid=699019&imei=00000000-0000-0000-0000-000000000000&netType=4&ProgramID=220220886420170328013&client_ip=221.181.101.37";
        args += "&timestamp=" + time;
        String md5_str = uri.substring(1) + "?" + args + secret_key;
        //System.out.println(md5_str);
        String md5 = JHelper.getMD5(md5_str);

        String url = "http://" + domain + uri + "?" + args + "&encrypt=" + md5
                + "&jid=5A2762E3-A7B2-4B3A-A4B8-84D76ED08BC71490668204&sjid=subsession_1490668208529" + "&playbackbegin=20170328054500&playbackend=20170328060000";
        //		+ ;"&preview=1&playseek=1"  &playbackbegin=1&playbackend=1
        System.out.println(url);
    }

    //新蓝网防盗链
    public static void checkCztv() {
        //http://yf.l.cztv.com/channels/lantian/channel05/360p.m3u8/1493164800000,1493215200000
        String domain = "yf.l.cztv.com";
        //String domain = "yf.l.cztv.com";
        String uri = "/channels/lantian/channel05/360p.m3u8/1493164800000,1493215200000";
        String lantian = "/lantian/";
        String channel = "channel05";

        String secret_key = "cztv";
        String time = JHelper.getTime(-60 * 70) + "";
        String md5_str = secret_key + lantian + channel + time;
        String md5 = JHelper.getMD5(md5_str);

        String url = "http://" + domain + uri + "?k=" + md5 + "&t=" + time;

        System.out.println(url);
    }


    //美图防盗链
    public static void checkMeitu() {
        //http://yf.l.cztv.com/channels/lantian/channel05/360p.m3u8/1493164800000,1493215200000
        String domain = "mvvideo1.meitudata.com";
        //String domain = "yf.l.cztv.com";
        String uri = "/58f9cad913ec13729.mp4";
        String uri_2 = "/11.txt";
        String uri_3 = "/lh/11/11.txt?stat";
        String uri_4 = "/lh/11/11.txt*thumb320";

        String secret_key = "K8cAotdITTls4XaU";
        String time = JHelper.redixConvert(JHelper.getTime(-60 * 60) + "", 16);

        String md5_str = secret_key + uri + time;
        String md5_str_2 = secret_key + uri_2 + time;

        String md5_str_3 = secret_key + uri_3.substring(0, uri_3.indexOf("?")) + time;
        String md5_str_4 = secret_key + uri_4 + time;

        System.out.println(md5_str_3);
        String md5 = JHelper.getMD5(md5_str);
        String md5_2 = JHelper.getMD5(md5_str_2);
        String md5_3 = JHelper.getMD5(md5_str_3);
        String md5_4 = JHelper.getMD5(md5_str_4);

        String url = "http://" + domain + uri + "?k=" + md5 + "&t=" + time;
        String url_2 = "http://" + domain + uri_2 + "?k=" + md5_2 + "&t=" + time;
        String url_3 = "http://" + domain + uri_3 + "&k=" + md5_3 + "&t=" + time;
        String url_4 = "http://" + domain + uri_4 + "?k=" + md5_4 + "&t=" + time;

        System.out.println(url);
        System.out.println(url_2);
        System.out.println(url_3);
        System.out.println(url_4);
    }

    //七牛防盗链_b站防盗链
    public static void checkBiliBili() {
        //String domain = "liuhuitest.com";
        //md5(um_deadline+{path}+oi+rate+secret_key+stime+etime)
        String[] urls = new String[4];
        String domain = "upos-hz-mirrorkodo.acgvideo.com";

        //String uri = "/upgcxcode/00/02/8060200/8060200-1-16.mp4";    //大小：678630825字节
        String uri = "/autotest/mp4_drag.mp4";
        //String secret_key = "00000000432fab4491100aab67fe2fabc125a5eb";
        String secret_key = "20170607920cbd5211831ce2a97066a8b544fa7b";
        String um_deadline = JHelper.getTime(60 * 60) + "";
        String rate = "" + 1024*300;
        String oi = "3078818617";
        String stime = "" + 10;
        String etime = "" +  20;  //678630825
        //String platform = "pc";
        String md5_str = um_deadline + uri + oi + rate + secret_key + stime + etime;
        String md5_str1 = um_deadline + uri + oi + secret_key + stime + etime;
        String md5_str2 = um_deadline + uri + oi + rate + secret_key;
        String md5_str3 = um_deadline + uri + oi + secret_key;

        String md5 = JHelper.getMD5(md5_str);
        String md5_1 = JHelper.getMD5(md5_str1);
        String md5_2 = JHelper.getMD5(md5_str2);
        String md5_3 = JHelper.getMD5(md5_str3);


        urls[0] = "http://" + domain + uri + "?um_deadline=" + um_deadline +
                "&um_sign=" + md5 + "&oi=" + oi + "&rate=" + rate + "&stime="
                + stime + "&etime=" + etime;

        urls[1] = "http://" + domain + uri + "?um_deadline=" + um_deadline +
                "&um_sign=" + md5_1 + "&oi=" + oi + "&stime="
                + stime + "&etime=" + etime;

        urls[2] = "http://" + domain + uri + "?um_deadline=" + um_deadline
                + "&um_sign=" + md5_2 + "&oi=" + oi + "&rate=" + rate;

        urls[3] = "http://" + domain + uri + "?um_deadline=" + um_deadline
                + "&um_sign=" + md5_3 + "&oi=" + oi;

        String host = "192.168.2.235";
        for(int i= 0;i<urls.length;i++){
            String code = HttpRequest.sendHead(urls[i],host);
            System.out.println( "状态码:" + code + ",链接:" +urls[i]);
        }
    }

    public static void main(String[] args) {

        Check.checkQiniu_2();
    }


}

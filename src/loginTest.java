import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;



public class loginTest {

    public static String sendGet(String url, String param,String username,String password) throws Exception{
        String result = "";
        BufferedReader in = null;
        //POST的URL
        HttpPost httppost=new HttpPost("http://newjw.lixin.edu.cn/sso/login");
        //建立HttpPost对象
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        //建立一个NameValuePair数组，用于存储欲传送的参数
        params.add(new BasicNameValuePair("username",username));
        params.add(new BasicNameValuePair("password",password));
        //添加参数
        httppost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
        DefaultHttpClient httpclient=new DefaultHttpClient();
        HttpResponse response=httpclient.execute(httppost);
        CookieStore cookiestore=httpclient.getCookieStore();

        String cook=cookiestore.getCookies().get(0).getValue();
        System.out.println("cookie:132456:  "+cook);


        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.addRequestProperty("cookie","URP_SID="+cook+"; path=/; domain=.newjw.lixin.edu.cn; HttpOnly; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
            // 建立实际的连接
            connection.connect();

            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
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
        return result;
    }


    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String cookieVal = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }


//            cookieVal=conn.getHeaderField("accept");
            cookieVal=conn.getRequestProperty("Upgrade-Insecure-Requests");
            conn.connect();
            System.out.println(cookieVal);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return result;
    }


    public static void main(String[] args){
//        String sr=loginTest.sendPost("http://newjw.lixin.edu.cn/sso/login", "username=1514010140&password=277023");
//        System.out.println(sr);
//        System.out.println(sr.length());
        String sr2= null;
        try {
            sr2 = loginTest.sendGet("http://newjw.lixin.edu.cn/webapp/std/edu/student/index.action","ticket=ST-12559-ysBcjeHibcyXpvxgFcyhg42eeRDDSdJemsH669022","1519030135","202067");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sr2);
        System.out.println(sr2.length());
        int pos=sr2.indexOf("姓名：</td>        <td>");
        String str="";
        while (sr2.charAt(pos)!='<'){
            str+=sr2.charAt(pos+20);
            pos++;
        }
        System.out.println(str);


    }
}

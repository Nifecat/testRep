
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class login {

    public static String sendPostTest(String url) throws Exception{

        //POST的URL
        HttpPost httppost=new HttpPost(url);
        //建立HttpPost对象
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        //建立一个NameValuePair数组，用于存储欲传送的参数
        params.add(new BasicNameValuePair("username","1514010144"));
        params.add(new BasicNameValuePair("password","064126"));
        //添加参数
        httppost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
        //设置编码
//        HttpResponse response=new DefaultHttpClient().execute(httppost);

        DefaultHttpClient httpclient=new DefaultHttpClient();
        HttpResponse response=httpclient.execute(httppost);
        CookieStore cookiestore=httpclient.getCookieStore();
        return cookiestore.getCookies().get(0).getValue();

        //发送Post,并返回一个HttpResponse对象
        //Header header = response.getFirstHeader("Content-Length");
        //String Length=header.getValue();
        // 上面两行可以得到指定的Header
//        if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
//            String result=EntityUtils.toString(response.getEntity());
//            //得到返回的字符串
//            System.out.println(result);
//            //打印输出
//            //如果是下载文件,可以用response.getEntity().getContent()返回InputStream
//        }else{
//            System.out.println("nonono");
//        }

//        return response.toString();
    }

    public static void main(String[] args) {
        String url="http://newjw.lixin.edu.cn/sso/login";
        String ans="";
        try {
            ans=sendPostTest(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(ans);
    }
}

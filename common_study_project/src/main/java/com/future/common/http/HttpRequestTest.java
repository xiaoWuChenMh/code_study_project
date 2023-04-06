package tool.http;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author xiaowuchen
 * @Date 2019/7/26 10:43
 */
public class HttpRequestTest {

    /**
     * 返回成功状态码
     */
    private static final int SUCCESS_CODE = 200;

    public static void main(String[] args) {
        String url  ="https://xxxxxxxxxx;
        List<NameValuePair> parame = new ArrayList<NameValuePair>();
        parame.add(new BasicNameValuePair("uid", "111111111111111111111111111"));
        JSONObject jsonObject = http_get(url,parame);
        String name = jsonObject.getJSONObject("data").getJSONObject("user").getString("name");
        System.out.println(jsonObject.toString());
        System.out.println(name);
    }


    public static JSONObject http_get(String url, List<NameValuePair> paramsList){
        try {
           return sendGet(url,paramsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject sendGet(String url, List<NameValuePair> paramsList ) throws IOException {

        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            // 创建HttpClient对象
           client = HttpClients.createDefault();
            //创建URIBuilder
            URIBuilder uriBuilder = new URIBuilder(url);
            //设置参数
            uriBuilder.addParameters(paramsList);
            //创建HttpGet
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            //设置请求头部编码
            httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            //设置返回编码
            httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
            //请求服务
            response = client.execute(httpGet);
            //获取响应吗
            int statusCode = response.getStatusLine().getStatusCode();
            if (SUCCESS_CODE == statusCode){
                //获取返回对象
                HttpEntity entity = response.getEntity();
                //通过EntityUitls获取返回内容
                String result = EntityUtils.toString(entity,"UTF-8");
                jsonObject =  JSONObject.fromObject(result);
                return jsonObject;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
               response.close();
               client.close();
        }
        return null;
    }
}

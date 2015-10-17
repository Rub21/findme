package rub21.findeme.server;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.util.List;
public class Request extends AsyncTask<String, Integer, String> {
        String URL;
        List<NameValuePair> parameters;

       public Request(String url, List<NameValuePair> params)
        {
            this.URL = url;
            this.parameters = params;
        }


        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpEntity httpEntity = null;
                HttpResponse httpResponse = null;

                HttpPost httpPost = new HttpPost(URL);

                if (parameters != null)
                {
                    httpPost.setEntity(new UrlEncodedFormEntity(parameters));
                }
                httpResponse = httpClient.execute(httpPost);

                httpEntity = httpResponse.getEntity();
                return EntityUtils.toString(httpEntity);

            }  catch (Exception e)
            {

            }
            return "";
        }
    

    }


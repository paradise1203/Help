package help.com.help.auth;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;

import help.com.help.R;

public class RegistrationActivity extends Activity {

    private final String auth_url = "http://10.17.0.151:8080/mobile/registration";

//    private final EditText email;
//    private final EditText pass;
//    private final EditText mobile;
//    private Button signUp;

    private class SendSignUpPostRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(auth_url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            InputStream in = null;
            try {
                jsonObject.accumulate("email", params[0]);
                jsonObject.accumulate("pass", params[1]);
                jsonObject.accumulate("mobile", params[2]);
                json = jsonObject.toString();
                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                HttpResponse httpResponse = httpClient.execute(httpPost);
                in = httpResponse.getEntity().getContent();
            } catch (Exception ignored) {
            }
            return "";
        }

        protected void onPostExecute(String result) {
            System.out.println(result);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText pass = (EditText) findViewById(R.id.pass);
        final EditText mobile = (EditText) findViewById(R.id.mobile);
        Button signUp = (Button) findViewById(R.id.sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] params = new String[] {email.getText().toString(),
                        pass.getText().toString(), mobile.getText().toString()};
                new SendSignUpPostRequest().execute(params);
            }
        });
    }

}

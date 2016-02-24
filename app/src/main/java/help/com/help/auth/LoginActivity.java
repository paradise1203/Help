package help.com.help.auth;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import help.com.help.R;
import help.com.help.location.LocationService;

public class LoginActivity extends Activity {

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private class SendSignUpPostRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            Firebase ref = new Firebase("https://blistering-inferno-7485.firebaseio.com/");
            ref.authWithPassword(params[0], params[1], new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                    UserUid.setUid(authData.getUid());
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    System.out.println(firebaseError.getMessage());
                }
            });
            return "success!";
        }

        protected void onPostExecute(String result) {
            LoginActivity.this.startActivity(new Intent(LoginActivity.this, LocationService.class));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.login_page);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText pass = (EditText) findViewById(R.id.pass);
        Button signUp = (Button) findViewById(R.id.sign_in);

        signUp.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v) {
                String[] params = new String[]{email.getText().toString(),
                        pass.getText().toString()};
                new SendSignUpPostRequest().execute(params);
            }
        });

    }

}

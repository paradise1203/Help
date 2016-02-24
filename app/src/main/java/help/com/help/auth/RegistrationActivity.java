package help.com.help.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import help.com.help.R;

public class RegistrationActivity extends Activity {

    private class SendSignUpPostRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(final String... params) {
            final Firebase ref = new Firebase("https://blistering-inferno-7485.firebaseio.com/");
            ref.createUser(params[0], params[1], new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {
                    String uid = result.get("uid").toString();
                    System.out.println("Successfully created user account with uid: " + uid);
                    Firebase mobile = ref.child(uid);
                    mobile.child("mobile").setValue(params[2]);
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    System.out.println(firebaseError.getMessage());
                }
            });
            return "success!";
        }

        protected void onPostExecute(String result) {
            RegistrationActivity.this.startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.registration_page);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText pass = (EditText) findViewById(R.id.pass);
        final EditText mobile = (EditText) findViewById(R.id.mobile);
        Button signUp = (Button) findViewById(R.id.sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] params = new String[]{email.getText().toString(),
                        pass.getText().toString(), mobile.getText().toString()};
                new SendSignUpPostRequest().execute(params);
            }
        });
    }

}

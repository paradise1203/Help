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
import android.widget.Switch;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import help.com.help.R;
import help.com.help.main.NeedyActivity;
import help.com.help.main.VolunteerActivity;

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
                    Switch role = (Switch) findViewById(R.id.role);
                    UserUid.setVolunteer(role.isChecked());
                    if (UserUid.isVolunteer()) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, VolunteerActivity.class));
                    } else {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, NeedyActivity.class));
                    }
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    System.out.println(firebaseError.getMessage());
                }
            });
            return "success!";
        }

        protected void onPostExecute(String result) {
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.login_page);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText pass = (EditText) findViewById(R.id.pass);
        Button signIn = (Button) findViewById(R.id.sign_in);
        Button signUp = (Button) findViewById(R.id.sign_up);

        signIn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v) {
                String[] params = new String[]{email.getText().toString(),
                        pass.getText().toString()};
                new SendSignUpPostRequest().execute(params);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

    }

}

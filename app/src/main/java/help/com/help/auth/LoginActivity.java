package help.com.help.auth;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends Activity {

    private class SendSignUpPostRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            Firebase ref = new Firebase("https://blistering-inferno-7485.firebaseio.com/");
            ref.authWithPassword(params[0], params[1], new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    System.out.println("Error!");
                }
            });
            return "success!";
        }

        protected void onPostExecute(String result) {
            System.out.println(result);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

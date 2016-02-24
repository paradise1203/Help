package help.com.help.action;

import android.app.Activity;
import android.os.Bundle;

import help.com.help.auth.UserUid;

public class PassActivity extends Activity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        System.out.println("User with uid : " + UserUid.getUid() + "  has been successfully authenticated!");
    }

}

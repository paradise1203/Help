package help.com.help;

import android.app.Activity;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by aleksandrpliskin on 25.02.16.
 */
public class ListenerActivity extends Activity {

    @Override
    protected String doInBackground(String... params) {
        Firebase ref = new Firebase("https://blistering-inferno-7485.firebaseio.com/");
        // Attach an listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        ref.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
//                BlogPost newPost = snapshot.getValue(BlogPost.class);
//                System.out.println("Author: " + newPost.getAuthor());
//                System.out.println("Title: " + newPost.getTitle());

            }
            //... ChildEventListener also defines onChildChanged, onChildRemoved,
            //    onChildMoved and onCanceled, covered in later sections.
        });
    }



}

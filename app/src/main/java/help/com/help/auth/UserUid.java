package help.com.help.auth;

public class UserUid {

    public static String uid;
    public static boolean volunteer;

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        UserUid.uid = uid;
    }

    public static boolean isVolunteer() {
        return volunteer;
    }

    public static void setVolunteer(boolean volunteer) {
        UserUid.volunteer = volunteer;
    }
}

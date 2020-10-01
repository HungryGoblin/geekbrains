import javax.jws.soap.SOAPBinding;

public class Main {
    public static void main (String[] args) {
        try {
            DbProcessor conn = new DbProcessor();
            conn.setQueryResult("SELECT * FROM _user");
            System.out.println(conn.numResults());
            UserTools.resetPassword("SpyderMan", "zzz");
            UserTools.resetPassword("SpyderMan", "zzz", "yyy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

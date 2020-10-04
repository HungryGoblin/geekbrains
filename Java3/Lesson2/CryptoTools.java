import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CryptoTools {

    private static final String CODEPAGE = "utf-8";
    private static final String ALGORITHM = "MD5";
    private static MessageDigest messageDigest;

    public static String getHash(String str) throws Exception {
        messageDigest.reset();
        messageDigest.update(str.getBytes(CODEPAGE));
        return DatatypeConverter.printHexBinary(messageDigest.digest());
    }

    public static boolean checkHash(String str, String hash) throws Exception {
        return (getHash(str).equals(hash));
    }

    static {
        try {
            messageDigest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("Error initialisation CryptoTools: %s", e.getMessage()));
        }
    }
}

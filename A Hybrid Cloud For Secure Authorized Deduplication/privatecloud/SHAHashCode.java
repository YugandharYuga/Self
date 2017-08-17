package server;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
public class SHAHashCode {
	private static String data;
private static String convertToHex(byte[] data) {
	StringBuffer buf = new StringBuffer();
    for(int i = 0; i < data.length; i++) { 
		int halfbyte = (data[i] >>> 4) & 0x0F;
        int two_halfs = 0;
        do {
			if ((0 <= halfbyte) && (halfbyte <= 9)) 
				buf.append((char) ('0' + halfbyte));
            else 
				buf.append((char) ('a' + (halfbyte - 10)));
			halfbyte = data[i] & 0x0F;
		} while(two_halfs++ < 1);
	}
	return buf.toString();
}
public static String encrypt(byte[] text) {
	MessageDigest md;
	byte[] md5hash = new byte[32];
    try{
		md = MessageDigest.getInstance("SHA1");
        md.update(text,0,text.length);
        md5hash = md.digest();
	}catch(Exception e){
		e.printStackTrace();
	}
    return convertToHex(md5hash);
} 

}
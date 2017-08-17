package util;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Security {
	public static byte[] getKeys(){
		byte[] keyBytes = "ThisIsASecretKet".getBytes();
		return keyBytes;
	}
	public static String encryption(String inputstring)throws Exception{
		byte input[] = inputstring.getBytes();
		byte keys[] = getKeys();
		SecretKeySpec key = new SecretKeySpec(keys,0,keys.length,"AES");
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
	    byte encbytes[] = cipher.doFinal(input);
		String enc = new String(Base64.encodeBase64(encbytes));
		return enc;
	}
	public static String decryption(String encdata)throws Exception {
		byte keys[] = getKeys();
		SecretKeySpec key = new SecretKeySpec(keys,0,keys.length,"AES");
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = Base64.decodeBase64(encdata.getBytes());
		byte decbytes[] = cipher.doFinal(plainText);
		return new String(decbytes);
	}
	public static void main(String[] args) throws Exception {
		String enc = encryption("Paramount Pictures Los Angeles Paramount Pictures Los Angeles");
		String dec = decryption(enc);
		System.out.println(enc+" "+dec);
	}
}         
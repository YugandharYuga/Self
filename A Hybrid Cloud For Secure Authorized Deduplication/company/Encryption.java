package hybridcloud;
import java.io.Serializable; 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.File;
public class Encryption  {
	
public static byte[] generateKey(String key)throws Exception{
	return key.getBytes();
}

public static byte[] encrypt(byte[] unencrypted,String key){
	byte[] ciphertext = null;
	try{
		DESKeySpec dks = new DESKeySpec(generateKey(key));
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, desKey);
		ciphertext = cipher.doFinal(unencrypted);
	}catch(Exception e){
		e.printStackTrace();
	}
	return ciphertext;
}
public static  byte[] decrypt(byte[] encrypted,String key){
	byte[] decrypt = null;
	try{
		DESKeySpec dks = new DESKeySpec(generateKey(key));
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, desKey);
		decrypt = cipher.doFinal(encrypted);
	}catch(Exception e){
		e.printStackTrace();  
	}
	return decrypt;  
}  
}       
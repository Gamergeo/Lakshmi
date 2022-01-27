package com.project.lakshmi.business.hash;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.project.lakshmi.technical.ApplicationException;

@Service("hashService")
public class HashServiceImpl implements HashService {
	
	@Override
	public String encode(String key, String data) {
		String result;
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
			sha256_HMAC.init(secret_key);
		
			result = new String(Base64.getEncoder().encode(sha256_HMAC.doFinal(data.getBytes("UTF-8"))));
		} catch (Exception exception) {
			throw new ApplicationException(exception.getMessage());
		}
		
		return result;
	}

	
//	/**
//	 * Hasx le message en SHA-256
//	 */
//	@Override
//	public String hash(String message) {
//		MessageDigest digest;
//		try {
//			digest = MessageDigest.getInstance("SHA-256");
//		} catch (NoSuchAlgorithmException e) {
//			throw new ApplicationException("Algorithme de hachage non trouvé");
//		}
//		
//		byte[] encodedhash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
//		
//		return bytesToHex(encodedhash);
//	}
//	
//	private static String bytesToHex(byte[] hash) {
//	    StringBuilder hexString = new StringBuilder(2 * hash.length);
//	    for (int i = 0; i < hash.length; i++) {
//	        String hex = Integer.toHexString(0xff & hash[i]);
//	        if(hex.length() == 1) {
//	            hexString.append('0');
//	        }
//	        hexString.append(hex);
//	    }
//	    return hexString.toString();
//	}

}
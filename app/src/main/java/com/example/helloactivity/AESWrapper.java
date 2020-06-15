package com.example.helloactivity;

import android.util.Base64;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESWrapper {
    private String TextToCode;
    private String password;

    private static final int PswdIterations = 65536;
    private static final int KeySize = 256;
    private static final String CipherInstance = "AES/CBC/PKCS7Padding";
    private static final String AESSalt = "Salt";
    private static final String SecretKeyInstance = "PBKDF2WithHmacSHA1";
    private static final String InitializationVector = "0123456789ABCDEF";

    /**
     * Constructor : initialize the AES instance
     * @param password user input used for deriving the real 128bit key
     * @param text input text to be encrypted or decrypted
     *
     */
    public AESWrapper(String password, String text){
        this.password = password;
        this.TextToCode = text;
    }

    /**
     * KeyDerivation : generate the (deterministic) secret key from the password
     * @param password user input
     * @return the raw key byte array
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */

    private static byte[] KeyDerivation(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SecretKeyInstance);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), AESSalt.getBytes(), PswdIterations, KeySize);
        SecretKey tmp = factory.generateSecret(spec);
        return tmp.getEncoded();
    }

    /**
     * GenerateCipher : initialize the block cipher instance
     * @param mode encrypt or decrypt under ECB or CBC mode with or without padding
     * @param password user input
     * @return initialized block cipher instance
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws NoSuchPaddingException
     * @throws InvalidKeySpecException
     */
    private static Cipher GenerateCipher(int mode, String password)
            throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance(CipherInstance);
        SecretKeySpec skeySpec = new SecretKeySpec(KeyDerivation(password), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(InitializationVector.getBytes());
        cipher.init(mode, skeySpec, ivParameterSpec);
        return cipher;
    }

    /**
     * SM4 encryption
     * @param password
     * @param textToEncrypt (plain text)
     * @return cipher text (base64 encoded)
     */
    public static String encrypt(String password, String textToEncrypt) throws Exception {

        Cipher cipher = GenerateCipher(Cipher.ENCRYPT_MODE, password);
        byte[] encrypted = cipher.doFinal(textToEncrypt.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    /**
     * SM4 decryption
     * @param password
     * @param textToDecrypt (base64 encoded cipher text)
     * @return plain text
     */
    public static String decrypt(String password, String textToDecrypt) throws Exception {

        byte[] encryted_bytes = Base64.decode(textToDecrypt, Base64.DEFAULT);
        Cipher cipher = GenerateCipher(Cipher.DECRYPT_MODE, password);
        byte[] decrypted = cipher.doFinal(encryted_bytes);
        return new String(decrypted, "UTF-8");
    }

    /**
     *
     * @param mode encryption or decryption
     * @param times run times
     * @return the corresponding result text
     */
    public String execute(String mode, int times) {
        String res = "";
        for(int i = 0; i < times; i++){
            try{
                if(mode.equals("enc")){
                    res = encrypt(password, TextToCode);
                }
                else if(mode.equals("dec")){
                    res = decrypt(password, TextToCode);
                }
            }
            catch (Exception e){
                System.out.println("Something wrong.");
                e.printStackTrace();
                return null;
            }
        }
        return res;
    }


}

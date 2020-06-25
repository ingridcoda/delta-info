package services;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CipherService {
    //DES
    //CBC
    //PKCS5

    public static byte[] decrypt(String seed, String knownText, byte[] cryptogram)
            throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException,
            IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException {

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
            SecureRandom sr = new SecureRandom();
            sr.setSeed(seed.getBytes());

            IvParameterSpec spec = new IvParameterSpec(generateIV());
            keyGenerator.init(56, sr);
            Key key = keyGenerator.generateKey();

            c.init(Cipher.DECRYPT_MODE, key, spec);

            byte[] result = c.doFinal(cryptogram);
            String resultString = new String(result, StandardCharsets.UTF_8);
            System.out.println("RANDOM BYTES - POSSÍVEL IV:" + Arrays.toString(spec.getIV()));
            System.out.println(resultString);

            if (resultString.contains(knownText)) {
                System.out.println("decodificado com sucesso!");
                System.out.println(resultString);
                for (byte b : result) {
                    System.out.println(b);
                }
                return result;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

//    private static byte[] genIV(){
////        List<Integer> solution = new ArrayList<>();
////        for (int i = 0; i <= 9; i++) {
////            solution.add(i);
////        }
////
////        byte[] resByte = new byte[8];
////        for(int i = 0; i < 8; i++){
////            for(int j = 0; j < 10; j++) {
////                resByte[i] = solution.get(j).byteValue();
////
////            }
////        }
////    }

    private static byte[] generateIV() {
        List<Integer> solution = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            solution.add(i);
        }
        Collections.shuffle(solution);

        String result = "";
        byte[] resByte = new byte[8];
        for (int i = 0; i < 8; i++) {
            result = result.concat(String.valueOf(solution.get(i)));
            resByte[i] = solution.get(i).byteValue();
        }

        //System.out.println("RANDOM BYTES - POSSÍVEL IV:" + result);
        //return result.getBytes();
        return resByte;
    }

}

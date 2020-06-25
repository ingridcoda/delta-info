package services;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

public class CipherService {
    //DES
    //CBC
    //PKCS5

    public static byte[] decrypt(String seed, String knownText, byte[] cryptogram)
            throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException,
            IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException {

        return genIV(seed, knownText, cryptogram);
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
//            Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
//            SecureRandom sr = new SecureRandom();
//            sr.setSeed(seed.getBytes());
//
//            IvParameterSpec spec = new IvParameterSpec(generateIV());
//            keyGenerator.init(56, sr);
//            Key key = keyGenerator.generateKey();
//
//            c.init(Cipher.DECRYPT_MODE, key, spec);
//
//            byte[] result = c.doFinal(cryptogram);
//            String resultString = new String(result, StandardCharsets.UTF_8);
//            System.out.println("RANDOM BYTES - POSSÍVEL IV:" + Arrays.toString(spec.getIV()));
//            System.out.println("RESULT: " + resultString);
//
//            if (resultString.contains(knownText)) {
//                System.out.println("decodificado com sucesso!");
//                System.out.println(resultString);
//                for (byte b : result) {
//                    System.out.println(b);
//                }
//                return result;
//            }
//            return null;
//        } catch (Exception e) {
//            if (!e.getLocalizedMessage().contains("padded")) System.out.println(e.getLocalizedMessage());
//            return null;
//        }
    }


    private static byte[] genIV(String seed, String knownText, byte[] cryptogram) {
        String current = "";
        for (int i = 0; i <= 9; i++) {
            current += i;
            for (int j = 0; j <= 9; j++) {
                current += j;
                for (int k = 0; k <= 9; k++) {
                    current += k;
                    for (int l = 0; l <= 9; l++) {
                        current += l;
                        for (int m = 0; m <= 9; m++) {
                            current += m;
                            for (int n = 0; n <= 9; n++) {
                                current += n;
                                for (int p = 0; p <= 9; p++) {
                                    current += p;
                                    for (int q = 0; q <= 9; q++) {
                                        current += q;
                                        System.out.println("current: " + current);
                                        try {
                                            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
                                            Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
                                            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
                                            sr.setSeed(seed.getBytes());

                                            IvParameterSpec spec = new IvParameterSpec(current.getBytes());
                                            keyGenerator.init(56, sr);
                                            Key key = keyGenerator.generateKey();

                                            c.init(Cipher.DECRYPT_MODE, key, spec);

                                            byte[] result = c.doFinal(cryptogram);
                                            System.out.println("RANDOM BYTES - POSSÍVEL IV:" + Arrays.toString(spec.getIV()));

                                            result = Base64.getDecoder().decode(result);
                                            String resultString = new String(result, StandardCharsets.UTF_8);
                                            System.out.println("RESULT: " + resultString);

                                            if (resultString.contains(knownText)) {
                                                System.out.println("decodificado com sucesso!");
                                                System.out.println(resultString);
                                                for (byte b : result) {
                                                    System.out.println(b);
                                                }
                                                return result;
                                            }
                                        } catch (Exception e) {
                                            if (!e.getLocalizedMessage().contains("padded"))
                                                System.out.println(e.getLocalizedMessage());
                                        }
                                        current = current.substring(0, 7);
                                    }
                                    current = current.substring(0, 6);
                                }
                                current = current.substring(0, 5);
                            }
                            current = current.substring(0, 4);
                        }
                        current = current.substring(0, 3);
                    }
                    current = current.substring(0, 2);
                }
                current = current.substring(0, 1);
            }
            current = "";
            if(i == 9) return null;
        }
        return null;
    }

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
        return result.getBytes();
//        return resByte;
    }
}

package services;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

public class CipherService {

    public static byte[] findIVAndDecrypt(String seed, String knownText, String cryptogram) {
        return decrypt(seed, knownText, convertToBytes(cryptogram));
    }

    private static byte[] decrypt(String seed, String knownText, byte[] cryptogram) {
        String current = "";
        //Cria IV's (do 00000000 ao 99999999) para tentar decriptar a mensagem com sucesso
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
                                        try {
                                            // Gera PRNG a partir da semente
                                            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
                                            sr.setSeed(seed.getBytes());

                                            // Cria chave DES com o PRNG gerado
                                            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
                                            keyGenerator.init(56, sr);
                                            Key key = keyGenerator.generateKey();

                                            //Obtem o IV corrente (tentativa da vez)
                                            byte[] iv = current.getBytes();
                                            IvParameterSpec spec = new IvParameterSpec(iv);

                                            //Decripta o criptograma com a chave e o IV fornecidos
                                            Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
                                            c.init(Cipher.DECRYPT_MODE, key, spec);
                                            byte[] decrypted = c.doFinal(cryptogram);

                                            //Verifica conteudo da mensagem (se é válida, retorna o IV atual)
                                            String decryptedString = new String(decrypted, StandardCharsets.UTF_8);
                                            if (decryptedString.contains(knownText)) {
                                                System.out.println("Decodificado com sucesso!");
                                                System.out.println("IV: " + current + " - Mensagem: " + decryptedString);
                                                return decrypted;
                                            }

                                        } catch (Exception e) {
                                            System.out.println("Erro inesperado. Tente novamente!");
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
            if (i == 9) return null;
        }
        return null;
    }

    private static byte[] convertToBytes(String hex) {
        byte[] hexBytes = new byte[hex.length() / 2];
        int i = 0;
        while (i < hex.length()) {
            int val = ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
            hexBytes[i / 2] = (byte) val;
            i += 2;
        }
        return hexBytes;
    }
}

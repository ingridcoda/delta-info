import static java.util.Objects.isNull;
import static services.CipherService.findIVAndDecrypt;

public class Main {

    public static void main(String[] args)
            throws Exception {
        if (isNull(args) || args.length < 3)
            throw new Exception("Erro ao obter parâmetros: " + (isNull(args) ? "args nulo" :
                    ("args tamanho " + args.length))
                    + ". Informe a semente, o trecho previamente conhecido e o criptograma, " +
                    "nessa ordem, via linha de comando.");
        else {
            System.out.println("Semente recebida: " + args[0]);
            System.out.println("Trecho previamente conhecido recebido: " + args[1]);
            System.out.println("Buscando IV adequado ao criptograma " + args[2]);
            byte[] result = findIVAndDecrypt(args[0], args[1], args[2]);
            System.out.println("Resultado:" + (isNull(result) ? "NÃO ENCONTRADO" : new String(result)));
        }
    }
}

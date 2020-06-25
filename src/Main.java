import static java.util.Objects.isNull;
import static services.CipherService.decrypt;

public class Main {

    public static void main(String[] args)
            throws Exception {
        if (isNull(args) || args.length < 3)
            throw new Exception("Erro ao obter parâmetros: " + (isNull(args) ? "args nulo" :
                    ("args tamanho " + args.length)));
        else {
            for (var arg : args) System.out.println(arg);
            byte[] result = decrypt(args[0], args[1], args[2].getBytes());
            System.out.println("RESULTADO:" + (isNull(result) ? "nulo" : new String(result)));
        }
    }
}

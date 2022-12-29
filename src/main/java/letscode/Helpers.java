package letscode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helpers {


    enum Webpage {
        Login,
        Employee,
        GovernmentClient,
        GovernmentGoods,
        InfoClient,
        Stocks,
        Registration
    }
    public static String getWebpage(Webpage webpage) {
        System.out.println("Getting webpage: " + webpage.name());
        InputStream inputStream;

        try {
            switch (webpage) {
                case Login:
                    inputStream = Files.newInputStream(Path.of("html/login/index.html"));
                    break;
                case Employee:
                    inputStream = Files.newInputStream(Path.of("html/employee/employee.html"));
                    break;
                case GovernmentClient:
                    inputStream = Files.newInputStream(Path.of("html/governmentClient/governmentClient.html"));
                    break;
                case GovernmentGoods:
                    inputStream = Files.newInputStream(Path.of("html/governmentGoods/governmentGoods.html"));
                    break;
                case InfoClient:
                    inputStream = Files.newInputStream(Path.of("html/infocient/indexclient.html"));
                    break;
                case Stocks:
                    inputStream = Files.newInputStream(Path.of("html/stocks/stocks.html"));
                    break;
                case Registration:
                    inputStream = Files.newInputStream(Path.of("html/registration/registration.html"));
                    break;
                default:
                    // Error
                    return null;
            }

            String data = readFromInputStream(inputStream);
            inputStream.close();
            return data;
        }
        catch (IOException exception) {
            return null;
        }
    }

    public static byte[] getResource(String path) {

        try (InputStream inputStream = Files.newInputStream(Path.of(path))) {
            return inputStream.readAllBytes();
        }
        catch (IOException exception) {
            return null;
        }
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static byte[] getPasswordHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

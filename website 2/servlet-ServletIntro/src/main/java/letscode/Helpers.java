package letscode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Helpers {


    enum Webpage {
        Login,
        Employee,
        GovernmentClient,
        GovernmentGoods,
        InfoClient,
        Stocks
    }
    public static String getWebpage(Webpage webpage) {
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
}

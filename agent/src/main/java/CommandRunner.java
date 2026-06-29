import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandRunner {

    private static final String UNAVAILABLE = "Unavailable";

    public static String runCommand(String... command) {
        StringBuilder output = new StringBuilder();

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream())
            );

            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            while (errorReader.readLine() != null) {

            }

            int exitCode = process.waitFor();

            String result = output.toString().trim();

            if (exitCode != 0 || result.isBlank()) {
                return UNAVAILABLE;
            }

            return result;

        } catch (Exception e) {
            return UNAVAILABLE;
        }
    }
}
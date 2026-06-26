import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandRunner {

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

            // Read standard output
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Read error output
            while ((line = errorReader.readLine()) != null) {
                output.append("ERROR: ").append(line).append("\n");
            }

            int exitCode = process.waitFor();

            // If command failed, return empty string so caller can handle it
            if (exitCode != 0) {
                return "";
            }

        } catch (Exception e) {
            // Return empty string on exception to allow program to continue safely
            return "";
        }

        return output.toString().trim();
    }
}
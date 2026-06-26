import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SystemInfo {

    public static void main(String[] args) {

        // Collect raw outputs
        String osInfo = CommandRunner.runCommand("uname", "-a");
        String diskInfo = CommandRunner.runCommand("df", "-h");
        String uptimeInfo = CommandRunner.runCommand("uptime");
        String cpuInfo = getCPUInfo();
        String memoryInfo = getMemoryInfo();
        String userInfo = CommandRunner.runCommand("who");

        // Extract relevant values
        String parsedUptime = extractUptimeLine(uptimeInfo);
        String diskLine = extractPrimaryDisk(diskInfo);
        String[] diskValues = parseDiskValues(diskLine);

        // Build structured data object
        SystemData data = new SystemData();
        data.os = osInfo;
        data.cpu = cpuInfo;
        data.memory = memoryInfo;
        data.uptime = parsedUptime;

        data.diskSize = diskValues[0];
        data.diskUsed = diskValues[1];
        data.diskAvailable = diskValues[2];
        data.diskUsagePercent = diskValues[3];

        // Convert to JSON
        String json = data.toJson();

        // Output JSON
        System.out.println("=== JSON OUTPUT ===");
        System.out.println(json);

        // Send JSON to API
        sendPost(json);
    }

    // Send JSON data to API endpoint
    public static void sendPost(String json) {

        try {
            // Update this URL when you have a real server
            URL url = new URL("http://192.168.1.87:8080/checkin");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Write JSON body
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            System.out.println("POST response code: " + responseCode);

        } catch (Exception e) {
            System.out.println("POST request failed");
        }
    }

    // Retrieve CPU information (macOS)
    public static String getCPUInfo() {
        return CommandRunner.runCommand("sysctl", "-n", "machdep.cpu.brand_string");
    }

    // Retrieve memory information (macOS)
    public static String getMemoryInfo() {
        return CommandRunner.runCommand("vm_stat");
    }

    // Extract uptime line containing load averages
    public static String extractUptimeLine(String uptimeOutput) {
        for (String line : uptimeOutput.split("\n")) {
            if (line.toLowerCase().contains("load average")) {
                return line;
            }
        }
        return "Uptime information not found";
    }

    // Extract primary disk entry (root mount)
    public static String extractPrimaryDisk(String diskOutput) {
        String[] lines = diskOutput.split("\n");

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];

            if (line.contains(" /")) {
                return line;
            }
        }

        return "";
    }

    // Parse disk values into fields
    public static String[] parseDiskValues(String diskLine) {

        if (diskLine == null || diskLine.isEmpty()) {
            return new String[]{"N/A", "N/A", "N/A", "N/A"};
        }

        String[] parts = diskLine.trim().split("\\s+");

        String size = parts.length > 1 ? parts[1] : "N/A";
        String used = parts.length > 2 ? parts[2] : "N/A";
        String available = parts.length > 3 ? parts[3] : "N/A";
        String usagePercent = parts.length > 4 ? parts[4] : "N/A";

        return new String[]{size, used, available, usagePercent};
    }
}

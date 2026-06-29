import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SystemInfo {

    public static void main(String[] args) {

        String osName = System.getProperty("os.name").toLowerCase();

        String osInfo;
        String diskInfo;
        String uptimeInfo;
        String cpuInfo;
        String memoryInfo;

        if (osName.contains("linux")) {
            osInfo = LinuxHardwareCommands.getOsVersion();
            diskInfo = LinuxHardwareCommands.getDiskInfo();
            uptimeInfo = LinuxHardwareCommands.getUptime();
            cpuInfo = LinuxHardwareCommands.getCpuInfo();
            memoryInfo = LinuxHardwareCommands.getMemoryInfo();
        } else {
            osInfo = MacHardwareCommands.getOsVersion();
            diskInfo = MacHardwareCommands.getDiskInfo();
            uptimeInfo = MacHardwareCommands.getUptime();
            cpuInfo = MacHardwareCommands.getCpuInfo();
            memoryInfo = MacHardwareCommands.getMemoryInfo();
        }

        String parsedUptime = extractUptimeLine(uptimeInfo);
        String diskLine = extractPrimaryDisk(diskInfo);
        String[] diskValues = parseDiskValues(diskLine);

        SystemData data = new SystemData();
        data.os = osInfo;
        data.cpu = cpuInfo;
        data.memory = memoryInfo;
        data.uptime = parsedUptime;

        data.diskSize = diskValues[0];
        data.diskUsed = diskValues[1];
        data.diskAvailable = diskValues[2];
        data.diskUsagePercent = diskValues[3];

        String json = data.toJson();

        System.out.println("=== JSON OUTPUT ===");
        System.out.println(json);

        sendPost(json);
    }

    public static void sendPost(String json) {

        try {
            URL url = new URL("http://192.168.1.87:8080/checkin");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

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

    public static String extractUptimeLine(String uptimeOutput) {
        for (String line : uptimeOutput.split("\n")) {
            if (line.toLowerCase().contains("load average")) {
                return line;
            }
        }
        return "Uptime information not found";
    }

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
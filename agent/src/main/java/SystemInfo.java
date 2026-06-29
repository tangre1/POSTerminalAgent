import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;

public class SystemInfo {

    public static void main(String[] args) {

        String osName = System.getProperty("os.name").toLowerCase();

        SystemData data = new SystemData();

        String diskInfo;

        if (osName.contains("linux")) {

            data.hostname = LinuxHardwareCommands.getHostname();
            data.ipAddress = LinuxHardwareCommands.getIpAddress();
            data.macAddress = LinuxHardwareCommands.getMacAddress();

            data.osName = LinuxHardwareCommands.getOsName();
            data.osVersion = LinuxHardwareCommands.getOsVersion();
            data.osBuild = LinuxHardwareCommands.getOsBuild();
            data.kernelVersion = LinuxHardwareCommands.getKernelVersion();

            data.cpuModel = LinuxHardwareCommands.getCpuModel();
            data.memoryTotal = LinuxHardwareCommands.getMemoryTotal();

            data.manufacturer = LinuxHardwareCommands.getManufacturer();
            data.modelNumber = LinuxHardwareCommands.getModelNumber();
            data.serialNumber = LinuxHardwareCommands.getSerialNumber();
            data.biosVersion = LinuxHardwareCommands.getBiosVersion();

            data.javaVersion = LinuxHardwareCommands.getJavaVersion();
            data.uptime = extractUptimeLine(LinuxHardwareCommands.getUptime());
            data.lastBoot = LinuxHardwareCommands.getLastBoot();

            diskInfo = LinuxHardwareCommands.getDiskInfo();

        } else {

            data.hostname = MacHardwareCommands.getHostname();
            data.ipAddress = MacHardwareCommands.getIpAddress();
            data.macAddress = MacHardwareCommands.getMacAddress();

            data.osName = MacHardwareCommands.getOsName();
            data.osVersion = MacHardwareCommands.getOsVersion();
            data.osBuild = MacHardwareCommands.getOsBuild();
            data.kernelVersion = MacHardwareCommands.getKernelVersion();

            data.cpuModel = MacHardwareCommands.getCpuModel();
            data.memoryTotal = MacHardwareCommands.getMemoryTotal();

            data.manufacturer = MacHardwareCommands.getManufacturer();
            data.modelNumber = MacHardwareCommands.getModelNumber();
            data.serialNumber = MacHardwareCommands.getSerialNumber();
            data.biosVersion = MacHardwareCommands.getBiosVersion();

            data.javaVersion = MacHardwareCommands.getJavaVersion();
            data.uptime = extractUptimeLine(MacHardwareCommands.getUptime());
            data.lastBoot = MacHardwareCommands.getLastBoot();

            diskInfo = MacHardwareCommands.getDiskInfo();
        }

        String diskLine = extractPrimaryDisk(diskInfo);
        String[] diskValues = parseDiskValues(diskLine);

        data.diskTotal = diskValues[0];
        data.diskUsed = diskValues[1];
        data.diskAvailable = diskValues[2];
        data.diskUsagePercent = diskValues[3];

        data.timestamp = Instant.now().toString();

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

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("✓ Successfully checked in with server (HTTP 200)");
            } else {
                System.out.println("✗ Server responded with HTTP " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("✗ Failed to send POST request.");
        }
    }

    public static String extractUptimeLine(String uptimeOutput) {

        if (uptimeOutput == null || uptimeOutput.equals("Unavailable")) {
            return "Unavailable";
        }

        for (String line : uptimeOutput.split("\n")) {
            if (line.toLowerCase().contains("load average")) {
                return line.trim();
            }
        }

        return uptimeOutput.trim();
    }

    public static String extractPrimaryDisk(String diskOutput) {

        if (diskOutput == null || diskOutput.equals("Unavailable")) {
            return "Unavailable";
        }

        String[] lines = diskOutput.split("\n");

        for (int i = 1; i < lines.length; i++) {

            if (lines[i].contains(" /")) {
                return lines[i];
            }
        }

        return "Unavailable";
    }

    public static String[] parseDiskValues(String diskLine) {

        if (diskLine == null || diskLine.equals("Unavailable")) {
            return new String[]{
                    "Unavailable",
                    "Unavailable",
                    "Unavailable",
                    "Unavailable"
            };
        }

        String[] parts = diskLine.trim().split("\\s+");

        String total = parts.length > 1 ? parts[1] : "Unavailable";
        String used = parts.length > 2 ? parts[2] : "Unavailable";
        String available = parts.length > 3 ? parts[3] : "Unavailable";
        String percent = parts.length > 4 ? parts[4] : "Unavailable";

        return new String[]{
                total,
                used,
                available,
                percent
        };
    }
}
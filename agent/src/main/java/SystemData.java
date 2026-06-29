public class SystemData {

    private static final String UNAVAILABLE = "Unavailable";

    public String storeNumber = UNAVAILABLE;
    public String terminalNumber = UNAVAILABLE;

    public String hostname = UNAVAILABLE;
    public String ipAddress = UNAVAILABLE;
    public String macAddress = UNAVAILABLE;

    public String osName = UNAVAILABLE;
    public String osVersion = UNAVAILABLE;
    public String osBuild = UNAVAILABLE;
    public String kernelVersion = UNAVAILABLE;

    public String cpuModel = UNAVAILABLE;
    public String memoryTotal = UNAVAILABLE;

    public String diskTotal = UNAVAILABLE;
    public String diskUsed = UNAVAILABLE;
    public String diskAvailable = UNAVAILABLE;
    public String diskUsagePercent = UNAVAILABLE;

    public String manufacturer = UNAVAILABLE;
    public String modelNumber = UNAVAILABLE;
    public String serialNumber = UNAVAILABLE;
    public String biosVersion = UNAVAILABLE;

    public String javaVersion = UNAVAILABLE;
    public String uptime = UNAVAILABLE;
    public String lastBoot = UNAVAILABLE;

    public String clientVersion = "1.0.0";
    public String timestamp = UNAVAILABLE;

    public String toJson() {
        return "{\n"
                + "  \"storeNumber\": \"" + escape(storeNumber) + "\",\n"
                + "  \"terminalNumber\": \"" + escape(terminalNumber) + "\",\n"
                + "  \"hostname\": \"" + escape(hostname) + "\",\n"
                + "  \"ipAddress\": \"" + escape(ipAddress) + "\",\n"
                + "  \"macAddress\": \"" + escape(macAddress) + "\",\n"
                + "  \"osName\": \"" + escape(osName) + "\",\n"
                + "  \"osVersion\": \"" + escape(osVersion) + "\",\n"
                + "  \"osBuild\": \"" + escape(osBuild) + "\",\n"
                + "  \"kernelVersion\": \"" + escape(kernelVersion) + "\",\n"
                + "  \"cpuModel\": \"" + escape(cpuModel) + "\",\n"
                + "  \"memoryTotal\": \"" + escape(memoryTotal) + "\",\n"
                + "  \"diskTotal\": \"" + escape(diskTotal) + "\",\n"
                + "  \"diskUsed\": \"" + escape(diskUsed) + "\",\n"
                + "  \"diskAvailable\": \"" + escape(diskAvailable) + "\",\n"
                + "  \"diskUsagePercent\": \"" + escape(diskUsagePercent) + "\",\n"
                + "  \"manufacturer\": \"" + escape(manufacturer) + "\",\n"
                + "  \"modelNumber\": \"" + escape(modelNumber) + "\",\n"
                + "  \"serialNumber\": \"" + escape(serialNumber) + "\",\n"
                + "  \"biosVersion\": \"" + escape(biosVersion) + "\",\n"
                + "  \"javaVersion\": \"" + escape(javaVersion) + "\",\n"
                + "  \"uptime\": \"" + escape(uptime) + "\",\n"
                + "  \"lastBoot\": \"" + escape(lastBoot) + "\",\n"
                + "  \"clientVersion\": \"" + escape(clientVersion) + "\",\n"
                + "  \"timestamp\": \"" + escape(timestamp) + "\"\n"
                + "}";
    }

    private String escape(String value) {
        if (value == null) {
            return UNAVAILABLE;
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
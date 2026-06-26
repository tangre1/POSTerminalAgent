public class SystemData {
    public String os;
    public String cpu;
    public String memory;
    public String diskSize;
    public String diskUsed;
    public String diskAvailable;
    public String diskUsagePercent;
    public String uptime;

    // Convert object to JSON string
    public String toJson() {
        return "{"
                + "\"os\": \"" + escape(os) + "\","
                + "\"cpu\": \"" + escape(cpu) + "\","
                + "\"memory\": \"" + escape(memory) + "\","
                + "\"diskSize\": \"" + escape(diskSize) + "\","
                + "\"diskUsed\": \"" + escape(diskUsed) + "\","
                + "\"diskAvailable\": \"" + escape(diskAvailable) + "\","
                + "\"diskUsagePercent\": \"" + escape(diskUsagePercent) + "\","
                + "\"uptime\": \"" + escape(uptime) + "\""
                + "}";
    }

    // Escape quotes for valid JSON
    private String escape(String value) {
        if (value == null) return "";
        return value.replace("\"", "\\\"");
    }
}
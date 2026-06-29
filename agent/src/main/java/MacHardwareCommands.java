public class MacHardwareCommands {

    public static String getOsVersion() {
        return CommandRunner.runCommand("sw_vers");
    }

    public static String getKernelVersion() {
        return CommandRunner.runCommand("uname", "-a");
    }

    public static String getHostname() {
        return CommandRunner.runCommand("hostname");
    }

    public static String getIpAddress() {
        return CommandRunner.runCommand("ipconfig", "getifaddr", "en0");
    }

    public static String getMacAddress() {
        return CommandRunner.runCommand("sh", "-c", "ifconfig en0 | awk '/ether/{print $2}'");
    }

    public static String getCpuInfo() {
        return CommandRunner.runCommand("sysctl", "-n", "machdep.cpu.brand_string");
    }

    public static String getMemoryInfo() {
        return CommandRunner.runCommand("sysctl", "-n", "hw.memsize");
    }

    public static String getDiskInfo() {
        return CommandRunner.runCommand("df", "-h", "/");
    }

    public static String getUptime() {
        return CommandRunner.runCommand("uptime");
    }

    public static String getLastBootTime() {
        return CommandRunner.runCommand("sysctl", "-n", "kern.boottime");
    }

    public static String getHardwareProfile() {
        return CommandRunner.runCommand("system_profiler", "SPHardwareDataType");
    }

    public static String getJavaVersion() {
        return CommandRunner.runCommand("java", "-version");
    }
}
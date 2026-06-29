public class LinuxHardwareCommands {

    public static String getOsVersion() {
        return CommandRunner.runCommand("cat", "/etc/os-release");
    }

    public static String getKernelVersion() {
        return CommandRunner.runCommand("uname", "-a");
    }

    public static String getHostname() {
        return CommandRunner.runCommand("hostname");
    }

    public static String getIpAddress() {
        return CommandRunner.runCommand("hostname", "-I");
    }

    public static String getMacAddress() {
        return CommandRunner.runCommand("sh", "-c", "ip link | awk '/ether/{print $2; exit}'");
    }

    public static String getCpuInfo() {
        return CommandRunner.runCommand("lscpu");
    }

    public static String getMemoryInfo() {
        return CommandRunner.runCommand("free", "-h");
    }

    public static String getDiskInfo() {
        return CommandRunner.runCommand("df", "-h", "/");
    }

    public static String getUptime() {
        return CommandRunner.runCommand("uptime");
    }

    public static String getLastBootTime() {
        return CommandRunner.runCommand("who", "-b");
    }

    public static String getModelNumber() {
        return CommandRunner.runCommand("cat", "/sys/devices/virtual/dmi/id/product_name");
    }

    public static String getSerialNumber() {
        return CommandRunner.runCommand("cat", "/sys/devices/virtual/dmi/id/product_serial");
    }

    public static String getBiosVersion() {
        return CommandRunner.runCommand("cat", "/sys/devices/virtual/dmi/id/bios_version");
    }

    public static String getBiosVendor() {
        return CommandRunner.runCommand("cat", "/sys/devices/virtual/dmi/id/bios_vendor");
    }

    public static String getJavaVersion() {
        return CommandRunner.runCommand("java", "-version");
    }
}
public class MacHardwareCommands {

    public static String getHostname() {
        return CommandRunner.runCommand("hostname");
    }

    public static String getIpAddress() {
        return CommandRunner.runCommand("ipconfig", "getifaddr", "en0");
    }

    public static String getMacAddress() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "ifconfig en0 | awk '/ether/{print $2}'"
        );
    }

    public static String getOsName() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "sw_vers -productName"
        );
    }

    public static String getOsVersion() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "sw_vers -productVersion"
        );
    }

    public static String getOsBuild() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "sw_vers -buildVersion"
        );
    }

    public static String getKernelVersion() {
        return CommandRunner.runCommand("uname", "-r");
    }

    public static String getCpuModel() {
        return CommandRunner.runCommand(
                "sysctl",
                "-n",
                "machdep.cpu.brand_string"
        );
    }

    public static String getMemoryTotal() {
        return CommandRunner.runCommand(
                "sysctl",
                "-n",
                "hw.memsize"
        );
    }

    public static String getDiskInfo() {
        return CommandRunner.runCommand(
                "df",
                "-h",
                "/"
        );
    }

    public static String getManufacturer() {
        return "Apple";
    }

    public static String getModelNumber() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "system_profiler SPHardwareDataType | awk -F': ' '/Model Name/{print $2}'"
        );
    }

    public static String getSerialNumber() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "system_profiler SPHardwareDataType | awk -F': ' '/Serial Number/{print $2}'"
        );
    }

    public static String getBiosVersion() {
        return "Unavailable";
    }

    public static String getJavaVersion() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "java -version 2>&1 | head -n 1"
        );
    }

    public static String getUptime() {
        return CommandRunner.runCommand("uptime");
    }

    public static String getLastBoot() {
        return CommandRunner.runCommand(
                "sysctl",
                "-n",
                "kern.boottime"
        );
    }
}
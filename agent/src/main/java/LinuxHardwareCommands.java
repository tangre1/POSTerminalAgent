public class LinuxHardwareCommands {

    public static String getHostname() {
        return CommandRunner.runCommand("hostname");
    }

    public static String getIpAddress() {
        return CommandRunner.runCommand("hostname", "-I");
    }

    public static String getMacAddress() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "ip link | awk '/ether/{print $2; exit}'"
        );
    }

    public static String getOsName() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "grep '^NAME=' /etc/os-release | cut -d= -f2 | tr -d '\"'"
        );
    }

    public static String getOsVersion() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "grep '^VERSION_ID=' /etc/os-release | cut -d= -f2 | tr -d '\"'"
        );
    }

    public static String getOsBuild() {
        return CommandRunner.runCommand(
                "uname",
                "-v"
        );
    }

    public static String getKernelVersion() {
        return CommandRunner.runCommand(
                "uname",
                "-r"
        );
    }

    public static String getCpuModel() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "lscpu | awk -F: '/Model name/{print $2}'"
        );
    }

    public static String getMemoryTotal() {
        return CommandRunner.runCommand(
                "sh",
                "-c",
                "free -h | awk '/Mem:/{print $2}'"
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
        return CommandRunner.runCommand(
                "cat",
                "/sys/devices/virtual/dmi/id/sys_vendor"
        );
    }

    public static String getModelNumber() {
        return CommandRunner.runCommand(
                "cat",
                "/sys/devices/virtual/dmi/id/product_name"
        );
    }

    public static String getSerialNumber() {
        return CommandRunner.runCommand(
                "cat",
                "/sys/devices/virtual/dmi/id/product_serial"
        );
    }

    public static String getBiosVersion() {
        return CommandRunner.runCommand(
                "cat",
                "/sys/devices/virtual/dmi/id/bios_version"
        );
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
                "who",
                "-b"
        );
    }
}
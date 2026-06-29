public class CheckInThread extends Thread {

    private static final long CHECKIN_INTERVAL_MS = 1 * 10 * 1000;

    @Override
    public void run() {

        while (!isInterrupted()) {

            try {

                // Perform a single check-in
                SystemInfo.runCheckIn();

                // Wait until the next check-in
                Thread.sleep(CHECKIN_INTERVAL_MS);

            } catch (InterruptedException e) {

                interrupt();
                break;

            } catch (Exception e) {

                System.out.println("Check-in thread encountered an error: " + e.getMessage());

            }
        }

        System.out.println("Check-in thread stopped.");
    }
}
public class MissedLectures {
    public static int missedLectures(int numChapters, int firstDay, int lastDay) {
        int totalDays = lastDay - firstDay + 1; // Total number of days the student will be out
        int fullCycles = totalDays / numChapters; // Number of full cycles of lectures
        int remainingDays = totalDays % numChapters; // Remaining days after full cycles

        // Calculate the total number of chapters the student will miss
        int totalMissedChapters = fullCycles * numChapters + Math.min(remainingDays, numChapters);

        return totalMissedChapters;
    }

    public static void main(String[] args) {
        // Example usage:
        int numChapters = 4;
        int firstDay = 3;
        int lastDay = 5;
        int result = missedLectures(numChapters, firstDay, lastDay);
        System.out.println(result);
    }
}

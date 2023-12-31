// Java Program for the above approach

import java.util.*;

public class SortDates {
	// Map to store the numeric value of each month depending on
	// its occurrence i.e. Jan = 1, Feb = 2 and so on.
	static Map<String, Integer> monthsMap = new HashMap<>();

	// Function which initializes the monthsMap
	static void sortMonths() {
		monthsMap.put("Jan", 1);
		monthsMap.put("Feb", 2);
		monthsMap.put("Mar", 3);
		monthsMap.put("Apr", 4);
		monthsMap.put("May", 5);
		monthsMap.put("Jun", 6);
		monthsMap.put("Jul", 7);
		monthsMap.put("Aug", 8);
		monthsMap.put("Sep", 9);
		monthsMap.put("Oct", 10);
		monthsMap.put("Nov", 11);
		monthsMap.put("Dec", 12);
	}

	static int cmp(String date) {
		String[] dateParts = date.split(" ");
		int day = Integer.parseInt(dateParts[0]);
		int month = monthsMap.get(dateParts[1]);
		int year = Integer.parseInt(dateParts[2]);

		return year * 10000 + month * 100 + day;
	}

	// Utility function to print the contents
	// of the array
	static void printDates(String[] dates, int n) {
		for (int i = 0; i < n; i++) {
			System.out.println(dates[i]);
		}
	}

	public static void main(String[] args) {
		String[] dates = { "24 Jul 2017", "25 Jul 2017", "11 Jun 1996",
						"01 Jan 2019", "12 Aug 2005", "01 Jan 1997" };
		int n = dates.length;

		// Order the months
		sortMonths();

		// Sort the dates
		Arrays.sort(dates, (a, b) -> cmp(a) - cmp(b));

		// Print the sorted dates
		printDates(dates, n);
	}
}

// This code is contributed by Prince Kumar

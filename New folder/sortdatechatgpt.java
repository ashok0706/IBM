import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class SortDates {
    public static void main(String[] args) {
        // Example usage:
        String[] dates = {"01 Mar 2017", "03 Feb 2017", "15 Jan 1998"};
        sortDates(dates);
        System.out.println(Arrays.toString(dates));
    }

    public static void sortDates(String[] dates) {
        Arrays.sort(dates, new Comparator<String>() {
            @Override
            public int compare(String date1, String date2) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                try {
                    Date d1 = sdf.parse(date1);
                    Date d2 = sdf.parse(date2);
                    return d1.compareTo(d2);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
    }
}

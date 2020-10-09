package dev.juho.inventory.utils;

import java.util.Date;

public class TimeUtils {

    // This is copied from the web app and that code is copied from stackoverflow so the number are
    // more or less magic to me =)
    public static String timeSince(long date) {
        long seconds = (long) Math.floor((new Date().getTime() - date) / 1000);
        long interval = seconds / 31536000;

        if (interval > 1) {
            return (int) interval + " years";
        }
        interval = seconds / 2592000;

        if (interval > 1) {
            return (int) interval + " months";
        }
        interval = seconds / 86400;

        if (interval > 1) {
            return (int) interval + " days";
        }
        interval = seconds / 3600;

        if (interval > 1) {
            return (int) interval + " hours";
        }
        interval = seconds / 60;

        if (interval > 1) {
            return (int) interval + " minutes";
        }

        return (int) seconds + " seconds";
    }

}

package utils;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Part;

public final class Utils {

    public static boolean isInt(String s) {
        return s.matches("^\\d+$");

    }

    public static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String getYear() {
        return new DateUtils().getYear();
    }

    public static String renderColName(String colname) {
        String str = colname.replace('_', ' ');
        str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
        return str;
    }

    public static String firstUpper(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    public static String escapeUnicode(String str) {

        StringBuilder retStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int cp = Character.codePointAt(str, i);
            int charCount = Character.charCount(cp);
            if (charCount > 1) {
                i += charCount - 1; // 2.
                if (i >= str.length()) {
                    throw new IllegalArgumentException("escapeUnicode: se ha "
                            + "sobrepazado el indice maximo");
                }
            }

            if (cp < 128) {
                retStr.appendCodePoint(cp);
            } else {
                retStr.append(String.format("\\u%x", cp));
            }
        }
        return retStr.toString();
    }
//
//    public static String getNombreArchivo(final Part part, Logger LOGGER) {
//        final String partHeader = part.getHeader("content-disposition");
//        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
//        for (String content : part.getHeader("content-disposition").split(";")) {
//            if (content.trim().startsWith("filename")) {
//                return content.substring(
//                        content.indexOf('=') + 1).trim().replace("\"", "");
//            }
//        }
//        return null;
//    }
}

final class DateUtils {

    public final DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    public final Calendar c = Calendar.getInstance();
    public final Date date = new Date();

    public String getYear() {
        String year = new String();
        year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        return year;
    }
}
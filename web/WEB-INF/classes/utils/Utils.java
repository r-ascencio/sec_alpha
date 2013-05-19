package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public final class Utils {
    private static Object message;

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
    
    

    public static String encodeToSHA512(String value) {
        String out = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(value.getBytes());
            byte[] mb = md.digest();
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                out += s;
            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return out;

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
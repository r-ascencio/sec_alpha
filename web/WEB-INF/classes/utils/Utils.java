package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String buildForm(final models.Tabla entidad, String action) {
        StringBuilder dom = new StringBuilder();
        String datatype = new String();
        Pattern fk = Pattern.compile("([_A-Za-z0-9]+)(_fk)$");
        Matcher fk_matcher;
        Pattern auto = Pattern.compile("([_A-Za-z0-9]+)(_auto)$");
        Matcher auto_matcher;

        dom.append("<form method=\"POST\" name=\"frm" + entidad.getTableName() + "\" "
                + "action=\"" + action + "\">");
        dom.append("<fieldset>");
        dom.append("<legend id=\"lengend\"> " + entidad.getTableName() + "</legend>");
        dom.append("<br/>");

        for (Map.Entry<String, String> field : entidad.getCols().entrySet()) {
            String key = field.getKey();
            String type = "text";
            auto_matcher = auto.matcher(key);
            fk_matcher = fk.matcher(key);


            if (field.getValue() == "Integer") {
                datatype = "integer";
            } else if (field.getValue() == "String") {
                datatype = "string";
            } else if (field.getValue() == "Float") {
                datatype = "float";
            }

            if (fk_matcher.matches()) {
                key = fk_matcher.group(1);
            } else if (auto_matcher.matches()) {
                key = auto_matcher.group(1);
                type = "password";
            }

            if (key == "codigo" && field.getValue() == "Integer") {
                dom.append("<input type=\"hidden\" data-type=\"auto_increment\" "
                        + "name=\"" + key + "\" value = \" " + entidad.getCols().size() + 1 + " \" />");
            } else {

                dom.append("<div class=\"elevencol centered\">");
                dom.append("<div class=\"twocol last \">");
                dom.append("<span class=\"prefix\"> " + Utils.renderColName(key) + "</span>");
                dom.append("</div>");
                dom.append("<div class=\"tencol\">");
                dom.append("<input type=\"" + type + "\" data-type=" + datatype + " name=\"" + key + "\" />");
                dom.append("</div>");
                dom.append("</div>");

            }
        }
        dom.append("<input type=\"hidden\" name=\"" + entidad.getTableName() + "\" />");
        dom.append("<input type=\"submit\" value=\"Guardar\" class=\"twocol rgt btn success\"/>");
        dom.append("</fieldset>");
        dom.append("</form>");
        dom.append("</div>");
        dom.append("<br/>");
        return dom.toString();
    }
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
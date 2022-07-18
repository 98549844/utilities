package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailUtil {
    static private final Log log = LogFactory.getLog(EmailUtil.class);

    public static boolean emailAdderValidator(String email) {
        if (NullUtil.isNull(email)) {
            log.info("Email address not exist");
            return false;
        }
        String checkPattern = "^([a-zA-Z0-9_])+\\@(([a-zA-Z0-9])+\\.)+([a-zA-Z0-9]{2,4})+$";
        Pattern regex = Pattern.compile(checkPattern);
        Matcher matcher = regex.matcher(email);
        String validStatus = matcher.matches() ? "valid" : "invalid";
        log.info(email + " is " + validStatus + " Email address");
        return matcher.matches();
    }

    public static void main(String[] args) {
        boolean isEmail = EmailUtil.emailAdderValidator("sxgkwei@16375.org");

        System.out.println(isEmail);
    }


}

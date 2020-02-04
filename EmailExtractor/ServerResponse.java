package EmailExtractor;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ServerResponse {
    private Set<String> emails = new HashSet<>();
    private Pattern p = Pattern.compile("[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}");

    public String processInput(String theInput) throws Exception {
        System.out.println(theInput);
        Matcher https = this.p.matcher(theInput);
        if (https.matches()) {
            return sendPingRequest(theInput);
        }
        if (theInput.equals("3"))
            return "3";
        try {
            Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
                    "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-" +
                    "\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\." +
                    ")+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9]" +
                    "[0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
                    "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\" +
                    "x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
            URL url = new URL(theInput);
            BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder data = new StringBuilder();
            String input = "";
            while ((input = read.readLine()) != null) {
                data.append(input);
            }
            Matcher matches = p.matcher(data);
            while (matches.find()) {
                emails.add(matches.group());
            }
            if (emails.size() > 0) {
                String email_addresses = "0 ";
                for (String e : emails) {
                    email_addresses += e + ",";
                }
                emails.clear();
                return email_addresses;
            } else {
                System.out.println("NO EMAIL BUT VALID ADDRESS");
                return "1 ";
            }
        } catch (PatternSyntaxException e) {
            return "4";
        } catch (Exception e) {
            System.out.println("Exception MAIN ServerResponse");
            return "4 ";
        }
    }

    private String sendPingRequest(String address) throws Exception  {
        Matcher m = this.p.matcher(address);
        boolean b = m.matches();
        try {
            if (b) {
                boolean connected = InetAddress.getByName(address).isReachable(100);
                if (connected) {
                    System.out.println("200 ok");
                    System.out.println(InetAddress.getByName(address).getHostName().toUpperCase());
                    System.out.println(InetAddress.getByName(address).getCanonicalHostName().toUpperCase());
                    return "5";
                } else {
                    System.out.println("No 200");
                    return "6";
                }
            }  
        } catch (Exception e) {
            System.out.println("No matches EXCEPTION SendingPingRequest");
            return "4";
        }
        System.out.println("No matches outside try in SendingPingRequest");
        return "4";
    }
}

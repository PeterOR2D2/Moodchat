package com.example.mentalhealthlogin;

import java.util.List;
import java.util.Map;

public class UserInfo {
    private String username;
    private String email;
    private String password;
    private List<Map<String, Map<String, String>>> activitylog;
    private List<Map<String,Map<String, String>>> sleeplog;
    private List<Map<String,String>> foodlog;
    private List<Map<String,String>> moodlog;

    public UserInfo()
    {
        ;
    }

    public UserInfo(String uname, String e_mail, String pword,
                    List<Map<String, Map<String, String>>> alog,
                    List<Map<String,Map<String, String>>> slog,
                    List<Map<String,String>> flog,
                    List<Map<String,String>> mlog)
    {
        this.username = uname;
        this.email = e_mail;
        this.password = pword;
        this.activitylog = alog;
        this.sleeplog = slog;
        this.foodlog = flog;
        this.moodlog = mlog;
    }

    public List<Map<String, Map<String, String>>> getActivityLog()
    {
        return this.activitylog;
    }

}

package com.jauharteam.ojek.ojek;

/**
 * Created by dery on 11/4/17.
 */

public class Config {
    private String identityServicePath;
    public String getIdentityServicePath() {
        return identityServicePath;
    }

    public void setIdentityServicePath(String identityServicePath) {
        this.identityServicePath = identityServicePath;
    }
    public class JdbcConfig {
        private String url;
        private String username;
        private String password;

        public JdbcConfig() {
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "JdbcConfig{" +
                    "url='" + url + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    private JdbcConfig jdbc;

    public Config() {
    }

    public JdbcConfig getJdbc() {
        return jdbc;
    }

    public void setJdbc(JdbcConfig jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public String toString() {
        return "Config{" +
                "identityServicePath='" + identityServicePath + '\'' +
                ", jdbc=" + jdbc +
                '}';
    }

    private String defaultProfPicUrl;

    public String getDefaultProfPicUrl() {
        return defaultProfPicUrl;
    }

    public void setDefaultProfPicUrl(String defaultProfPicUrl) {
        this.defaultProfPicUrl = defaultProfPicUrl;
    }
}

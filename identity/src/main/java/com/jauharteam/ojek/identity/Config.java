package com.jauharteam.ojek.identity;

public class Config {

    public static class JdbcConfig {
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
    private Integer tokenAge;

    public Config() {
    }

    public JdbcConfig getJdbc() {
        return jdbc;
    }

    public void setJdbc(JdbcConfig jdbc) {
        this.jdbc = jdbc;
    }

    public Integer getTokenAge() {
        return tokenAge;
    }

    public void setTokenAge(Integer tokenAge) {
        this.tokenAge = tokenAge;
    }

    @Override
    public String toString() {
        return "{\"Config\":{"
            + "\"jdbc\":" + jdbc
            + ", \"tokenAge\":\"" + tokenAge + "\""
            + "}}";
    }
}

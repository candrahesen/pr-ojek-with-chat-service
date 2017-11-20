package com.ojek.common;

/**
 * @since 10/30/2017.
 */
public class User {

  private Integer id;
  private String username;
  private String password;
  private String email;
  private String name;
  private String phone;
  private String profpicUrl;
  private Boolean driver;
  private Integer rating;
  private Integer votes;

  public User() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getProfpicUrl() {
    return profpicUrl;
  }

  public void setProfpicUrl(String profpicUrl) {
    this.profpicUrl = profpicUrl;
  }

  public Boolean getDriver() {
    return driver;
  }

  public void setDriver(Boolean driver) {
    this.driver = driver;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public Integer getVotes() {
    return votes;
  }

  public void setVotes(Integer votes) {
    this.votes = votes;
  }

  @Override
  public String toString() {
    return "{\"User\":{"
        + "\"id\":\"" + id + "\""
        + ", \"username\":\"" + username + "\""
        + ", \"password\":\"" + password + "\""
        + ", \"email\":\"" + email + "\""
        + ", \"name\":\"" + name + "\""
        + ", \"phone\":\"" + phone + "\""
        + ", \"profpicUrl\":\"" + profpicUrl + "\""
        + ", \"driver\":\"" + driver + "\""
        + ", \"rating\":\"" + rating + "\""
        + ", \"votes\":\"" + votes + "\""
        + "}}";
  }
}

<%@ page import="com.ojek.common.User" %>
<%@ page import="com.jauharteam.ojek.webapp.Config" %>
<%@ page import="com.jauharteam.ojek.ojek.UserService" %>
<%@ page import="com.jauharteam.ojek.ojek.LocationService" %>
<%@ page import="com.ojek.common.Location" %>
<%@ page import="java.util.Date" %>
<%
    String accessToken = (String) request.getAttribute("accessToken");
    UserService userService = (UserService) request.getAttribute("userService");
    LocationService locationService = (LocationService) request.getAttribute("locationService");
    Config conf = (Config) request.getAttribute("config");

    User user = userService.getUser(accessToken);
    Location[] locations = locationService.getAllLocation(accessToken);
%>
	<script>
        var globalConfig = {
            "identityRestPath": "<%= conf.getIdentityRestPath() %>",
            "chatRestPath": "<%= conf.getChatRestPath() %>",
            "identityServicePath": "<%= conf.getIdentityServicePath() %>",
            "userServicePath": "<%= conf.getUserServicePath() %>",
            "orderServicePath": "<%= conf.getOrderServicePath() %>",
            "locationServicePath": "<%= conf.getLocationServicePath() %>",
            "baseUrl": "<%= conf.getBaseUrl() %>"
        };
    </script>
            <div class="row margin-top">
                <h2>Edit Preferred Locations</h2>
                <div class="row">
                    <% int nb = 0; %>
                    <table class="location_table" id='location_table'>
                        <tr>
                            <th style="width: 25px">No</th>
                            <th>Location</th>
                            <th style="width: 90px">Actions</th>
                        </tr>
                        <%
                            for (Location location : locations) {
                                nb+=1;
                        %>
                        <% int prefNum = location.getPrefNum(); %>
                        <tr id='row-<%= prefNum %>'>
                        <form action="/soapservlet" method="post" accept-charset="utf-8">
                            <input type="text" name="name" value="edit-location" hidden>
                            <td class='number'>
                                <input type='text' id="pref-row-<%= prefNum %>" class='input-standard input-standard-v2' name="pref-<%= prefNum %>" value="<%= prefNum %>" style="display:none;">
                                <span class='location-pref'><%= nb %></span>
                            </td>
                            <td class='location'>
                                <input type="text" name="loc-last-<%= prefNum %>" value="<%= location.getLocation() %>" hidden>
                                <input type="text" name="location-pref" value="<%= prefNum %>" hidden>
                                <input type='text' id="loc-row-<%= prefNum %>" class='input-standard input-standard-v2' name="loc-<%= prefNum %>" value="<%= location.getLocation() %>" style="display:none;">
                                <span class='location-name'><%= location.getLocation() %></span>
                            </td>
                            <td class='option'>
                                <div class="row">
                                    <div class="col-6" id="edit-<%= prefNum %>">
                                        <a href="#" class="edit-icon" id="edit-link-<%= prefNum %>" onclick="editLocation(<%= user.getId() %> , <%= prefNum %>)"><i class="material-icons md-36">edit</i></a>
                                        <button class="save-" id='submit-edited-location-<%= prefNum %>' value='submit-edited-location-<%= prefNum %>' style="display:none;"><i class='material-icons md-36'>save</i></button>
                                    </div>
                                    <div class="col-6" id="delete-<%= prefNum %>">
                                        <a href="#" class="delete-icon" id="delete-link-<%= prefNum %>" onclick="
                                            deleteLocation(<%= user.getId() %>, <%= prefNum %>, <%= location.getId() %>)
                                            "><i class="material-icons md-36">clear</i></a>
                                    </div>
                                </div>
                            </td>
                        </form>
                        </tr>
                        <%
                            }
                        %>
                        <tr id='no-data-row' style="display: <% if(nb < 1){%> table-row <%}else{%> none <%}%>">
                            <td colspan='3' style='text-align: center;'> No data to display </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="row margin-top">
                <h3>Add New Location : </h3>
                <form action="/soapservlet" method="POST" accept-charset="utf-8">
                <input name="name" type="hidden" value="add-location">
                <label class="col-10">
                    <input class="input-standard input-standard-v2" autocorrect="off" autocomplete="off" type="text" id="location" name="location" list="suggestLoc">
                    <datalist id="suggestLoc"></datalist>
                </label>
                <label class="col-2 ">
                <input class="button button-success button-add" id='submit-add-location' name="submit" type="submit" value="Add"></button>
                </label>
                </form>
            </div>
            <?php if(is_string($msg)) { ?>
            <div class="row warning-box" id="warning-msg">
                <span class="closebtn" onclick="">&times;</span>
                <?php echo $msg;?>
            </div>
            <?php } ?>
            <div class="row margin-top">
                <a href="<%= conf.getBaseUrl() %>profile" class="button button-fail">Back</a>
            </div>

            <script>
                var id = <%= user.getId() %>;
                var nbPref = <%= nb %>;
            </script>

            <% long ts = (new Date()).getTime(); //Used to prevent JS/CSS caching %>
            <script type="text/javascript" src="./resources/js/ajax.js?<%= ts %>"></script>
            <script type="text/javascript" src="./resources/js/edit_location.js?<%= ts %>"></script>

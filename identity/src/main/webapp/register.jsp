<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h1>Register</h1>
    <p>Register service, use POST method in this url with 7 parameters: name, username, email, password, confirm_password, phone, and driver. Service will return json that contain access token if success</p>
    <ul>
        <li>
                <pre>curl localhost:8081/identity/register -X POST \
--data "username=jauhar" \
--data "name=jauhar" \
--data "password=jauhar" \
--data "confirm_password=jauhar" \
--data "email=jauhar@jauhar.com" \
--data "phone=08211111111" \
--data "driver=1"
                </pre>
        </li>
    </ul>
</body>
</html>
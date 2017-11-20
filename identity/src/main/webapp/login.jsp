<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <p>Login service, use POST method in this url with two parameter: username and password. Service will return
        json that contain access token if success</p>
        <ul>
            <li>
                <pre>curl localhost:8081/identity/login -X POST \
--data "username=jauhar" \
--data "password=jauhar"
                </pre>
            </li>
        </ul>
    </body>
</html>
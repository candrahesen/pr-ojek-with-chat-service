import com.jauharteam.ojek.ojek.UserService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * Created by dery on 11/4/17.
 */
public class TestUserService {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8081/user?wsdl");

        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://service.ojek.ojek.jauharteam.com/", "UserServiceImplService");
        Service service = Service.create(url, qname);
        UserService userService = service.getPort(UserService.class);
        String token = "jdwTQE1ehTKExOqDxS23FnbeShJ0hof4";
        System.out.println(userService.getUser(token));
    }
}

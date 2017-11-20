import com.jauharteam.ojek.identity.IdentityService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class TestIdentityService {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8080/identity/service?wsdl");

        //1st argument service URI, refer to wsdl document above
        //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://identity.ojek.jauharteam.com/", "IdentityServiceImplService");
        Service service = Service.create(url, qname);
        IdentityService hello = service.getPort(IdentityService.class);
        String token = "jdwTQE1ehTKExOqDxS23FnbeShJ0hof4";
        System.out.println(hello.isTokenValid(token));
        if(hello.getUserByToken(token).getId() != null){
            System.out.println(hello.getUserByToken(token).getId());
            System.out.println(hello.getUserByToken(token).getUsername());
            System.out.println(hello.getUserByToken(token).getPassword());
            System.out.println(hello.getUserByToken(token).getEmail());
        }else{
            System.out.println("getUserByToken() == null");
        }
    }
}

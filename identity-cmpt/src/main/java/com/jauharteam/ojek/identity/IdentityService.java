package com.jauharteam.ojek.identity;

import com.ojek.common.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IdentityService {

    @WebMethod public User getUserByToken(String token);

    @WebMethod public Boolean isTokenValid(String token);

}

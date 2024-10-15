package ra.pj05.service;


import ra.pj05.model.dto.request.FormLogin;
import ra.pj05.model.dto.request.FormRegister;
import ra.pj05.model.dto.response.JwtResponse;

public interface IAuthService {

    JwtResponse handleLogin(FormLogin formLogin);
    void handleRegister(FormRegister formRegister);
}

package br.com.treinaweb.twprojetos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.treinaweb.twprojetos.entities.Funcionario;
import br.com.treinaweb.twprojetos.entities.UserDetailImpl;
import br.com.treinaweb.twprojetos.repositories.FuncionarioRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        return new UserDetailImpl(funcionario);
    }
    
}

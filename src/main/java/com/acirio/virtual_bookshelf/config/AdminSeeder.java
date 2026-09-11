package com.acirio.virtual_bookshelf.config;

import com.acirio.virtual_bookshelf.model.UserModel;
import com.acirio.virtual_bookshelf.model.enums.UserRoleEnum;
import com.acirio.virtual_bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o banco de dados está vazio (sem usuários)
        if (userRepository.count() == 0) {
            System.out.println("Nenhum usuário encontrado. Criando usuário Administrador padrão...");

            UserModel admin = new UserModel();
            admin.setName("Administrador Padrão");
            admin.setNickname("admin");
            admin.setEmail("admin@admin.com");
            // A senha é criptografada utilizando o mesmo encoder seguro (BCrypt) do sistema
            admin.setPassword(passwordEncoder.encode("admin123")); 
            admin.setRole(UserRoleEnum.ROLE_ADMIN);

            userRepository.save(admin);
            
            System.out.println("Usuário Administrador criado com sucesso!");
            System.out.println("E-mail: admin@admin.com | Senha: admin123");
        }
    }
}

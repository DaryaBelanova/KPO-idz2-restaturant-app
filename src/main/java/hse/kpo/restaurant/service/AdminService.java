package hse.kpo.restaurant.service;

import hse.kpo.restaurant.model.Admin;
import hse.kpo.restaurant.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private AdminRepository adminRepository;
    public boolean ifAdminExistOrNot(String login) {
        Admin existingAdmin = adminRepository.findFirstByLogin(login);
        return existingAdmin != null;
    }
}

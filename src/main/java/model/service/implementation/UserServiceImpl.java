package model.service.implementation;

import model.entity.User;
import model.repository.UserRepository;
import model.repository.implementation.UserRepositoryImpl;
import model.service.UserService;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl() {
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public void create(User entity) throws SQLException {
        userRepository.create(entity);
    }

    @Override
    public User findById(Long id) throws SQLException {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() throws SQLException {
        return userRepository.findAll();
    }

    @Override
    public void update(User entity) throws SQLException {
        userRepository.update(entity);
    }

    @Override
    public void delete(Long id) throws SQLException {
        userRepository.delete(id);
    }
}

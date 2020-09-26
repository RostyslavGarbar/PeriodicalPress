package model.service.implementation;

import model.entity.Edition;
import model.repository.EditionRepository;
import model.repository.implementation.EditionRepositoryImpl;
import model.service.EditionService;

import java.sql.SQLException;
import java.util.List;

public class EditionServiceImpl implements EditionService {
    private final EditionRepository editionRepository;

    public EditionServiceImpl() {
        editionRepository = new EditionRepositoryImpl();
    }

    @Override
    public void create(Edition entity) throws SQLException {
        editionRepository.create(entity);
    }

    @Override
    public Edition findById(Long id) throws SQLException {
        return editionRepository.findById(id);
    }

    @Override
    public List<Edition> findAll() throws SQLException {
        return editionRepository.findAll();
    }

    @Override
    public void update(Edition entity) throws SQLException {
        editionRepository.update(entity);
    }

    @Override
    public void delete(Long id) throws SQLException {
        editionRepository.delete(id);
    }
}

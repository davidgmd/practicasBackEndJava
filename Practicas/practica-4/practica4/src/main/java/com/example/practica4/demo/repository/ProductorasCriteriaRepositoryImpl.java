package com.example.practica4.demo.repository;

import com.example.practica4.demo.entity.Productoras;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProductorasCriteriaRepositoryImpl implements ProductorasCriteriaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Productoras> findAllCriteria() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Productoras> query = cb.createQuery(Productoras.class);
        Root<Productoras> root = query.from(Productoras.class);

        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Productoras> findByIdCriteria(String idProductora) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Productoras> query = cb.createQuery(Productoras.class);
        Root<Productoras> root = query.from(Productoras.class);

        query.select(root)
                .where(cb.equal(root.get("idProductora"), idProductora));

        List<Productoras> resultado = entityManager.createQuery(query).getResultList();

        return resultado.stream().findFirst();
    }

    @Override
    public Productoras saveCriteria(Productoras productora) {
        Productoras existente = entityManager.find(Productoras.class, productora.getIdProductora());

        if (existente == null) {
            entityManager.persist(productora);
            return productora;
        } else {
            return entityManager.merge(productora);
        }
    }

    @Override
    public void deleteByIdCriteria(String idProductora) {
        Productoras productora = entityManager.find(Productoras.class, idProductora);
        if (productora != null) {
            entityManager.remove(productora);
        }
    }
}

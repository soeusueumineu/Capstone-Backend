package com.capstone.repository.queryrepository;

import com.capstone.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberCartReviewQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public Member findByIdWithReviewsAndCart(Long id) {
        String jpql = "SELECT m FROM Member m " +
                "LEFT JOIN FETCH m.reviews " +
                "LEFT JOIN FETCH m.cart " +
                "WHERE m.id = :id";

        return entityManager.createQuery(jpql, Member.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}

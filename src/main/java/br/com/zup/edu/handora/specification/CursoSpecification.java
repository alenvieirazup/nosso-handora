package br.com.zup.edu.handora.specification;

import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.zup.edu.handora.model.Curso;

public class CursoSpecification {

    public static Specification<Curso> nomes(List<String> nomes) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (nomes == null || nomes.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Predicate[] predicates = new Predicate[nomes.size()];
            for (int i = 0; i < nomes.size(); i++) {
                Predicate p = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("nome")), "%" + nomes.get(i).toUpperCase() + "%"
                );

                predicates[i] = p;
            }

            return criteriaBuilder.or(predicates);
        };
    }

    public static Specification<Curso> descricao(String descricao) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (descricao == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                criteriaBuilder.upper(root.get("descricao")), "%" + descricao.toUpperCase() + "%"
            );
        };
    }

}

package br.com.zup.edu.handora.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.jpa.domain.Specification;

import br.com.zup.edu.handora.model.Curso;

public class CursoSpecification {

    public static Specification<Curso> nomes(List<String> nomes) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (nomes == null || nomes.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            In<String> inClause = criteriaBuilder.in(criteriaBuilder.upper(root.get("nome")));

            for (String nome : nomes) {
                inClause.value(nome.toUpperCase());
            }

            return inClause;
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

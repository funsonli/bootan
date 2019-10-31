package com.funsonli.bootan.base;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * JPA访问DAO
 *
 * @author Funsonli
 * Date 2019-08-16
 */
@NoRepositoryBean
public interface BaseDao<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

    /**
     *
     * @author Funsonli
     * @date 2019/10/31
     */
    List<E> findByNameOrNameLikeOrderBySortOrderAsc(String keyword, String keyword1);

    List<E> findAllByOrderByCreatedAtAsc();

    List<E> findAllByOrderByCreatedAtDesc();

    List<E> findAllByOrderBySortOrderAscCreatedAtDesc();
}

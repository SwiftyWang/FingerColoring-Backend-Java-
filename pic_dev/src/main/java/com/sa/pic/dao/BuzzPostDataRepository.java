package com.sa.pic.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sa.pic.dao.domain.BuzzPostData;

@Repository
public interface BuzzPostDataRepository extends JpaRepository<BuzzPostData, String> {

	List<BuzzPostData> findByPageIdInOrderByPublishDateDesc(List<String> pageIds, Pageable top);

	List<BuzzPostData> findByPageIdInOrderByEngagementNumberDesc(List<String> pageIds, Pageable top);

    
}

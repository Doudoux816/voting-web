package com.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vote.model.VotingItem;



public interface VotingItemRepository extends JpaRepository<VotingItem, Integer> {
	
}

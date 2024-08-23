package com.example.meven.arpentage_api.repository;

import com.example.meven.arpentage_api.model.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface MemberRepository extends CrudRepository<Member, Long> {
}

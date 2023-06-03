package com.tutorial.trip.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.trip.model.MeetingRequest;

public interface MeetingRequestRepo extends JpaRepository<MeetingRequest, Long>{

	
	@Query(
			
			value = "select * from tbl_meeting where fk_user_id=:userId",
			nativeQuery = true
			)
	
	MeetingRequest findByuserID(Long userId);
	
	
	@Query(
			
			value="select fk_user_id from tbl_meeting where friend_email=:userEmail",
			
			nativeQuery = true
			
			
			)
	

	List<Long> findAllFriendsIdByEmail(String userEmail);

	
	@Modifying
	@Transactional
	@Query(
			value="delete from tbl_meeting  where friend_email=:myEmail And fk_user_id!=:userId",
			nativeQuery = true
			
			)
	
	
	

	void DeleteUnwantedRequest(String myEmail, Long userId);


	List<MeetingRequest> findByFriendEmail(String friendEmail);

	
	@Query(
			value = "select * from tbl_meeting  where friend_email=:friendEmail And fk_user_id=:userId",
			nativeQuery = true
			)

	MeetingRequest findByEmailAndUserId(String friendEmail, Long userId);
	
	

}

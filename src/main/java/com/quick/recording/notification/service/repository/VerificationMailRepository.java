package com.quick.recording.notification.service.repository;

import com.quick.recording.notification.service.entity.VerificationMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationMailRepository extends JpaRepository<VerificationMail, String> {
}

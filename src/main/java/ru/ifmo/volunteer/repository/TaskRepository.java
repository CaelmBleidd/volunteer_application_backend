package ru.ifmo.volunteer.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.ifmo.volunteer.model.Task;

import javax.transaction.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT task_id FROM task_to_event WHERE event_id = :id", nativeQuery = true)
    List<Long> findAllTasksByEventId(@Param("id") Long id);

    @Query(value = "SELECT status FROM task_to_status WHERE task_id=:id", nativeQuery = true)
    String getStatus(@Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE task_to_status SET status=?2 WHERE task_id = ?1", nativeQuery = true)
    void updateStatus(long id, String status);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO task_to_event (task_id, event_id) VALUES(?1, ?2)", nativeQuery = true)
    void saveTaskToEvent(long taskId, long eventId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO task_to_status (task_id, status) VALUES(?1, ?2)", nativeQuery = true)
    void addStatus(long id, String status);
}
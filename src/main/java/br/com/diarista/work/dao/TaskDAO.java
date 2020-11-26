package br.com.diarista.work.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.work.entity.Task;

public interface TaskDAO extends JpaRepository<Task, Long>
{
	public List<Task> findBytypeAndStatus(Short type, Boolean status);
}

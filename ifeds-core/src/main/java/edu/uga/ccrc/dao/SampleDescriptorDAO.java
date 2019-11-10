package edu.uga.ccrc.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uga.ccrc.entity.SampleDescriptor;

@Repository
public interface SampleDescriptorDAO extends CrudRepository<SampleDescriptor, Long> {

	public List<SampleDescriptor> findAll();
}

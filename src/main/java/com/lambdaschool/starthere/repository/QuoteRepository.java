package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Story;
import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository extends CrudRepository<Story, Long>
{

}

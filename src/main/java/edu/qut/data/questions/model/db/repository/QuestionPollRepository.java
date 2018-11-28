package edu.qut.data.questions.model.db.repository;

import edu.qut.data.questions.model.db.QuestionPoll;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface QuestionPollRepository extends CrudRepository<QuestionPoll, String> {
}

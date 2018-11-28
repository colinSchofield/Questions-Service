package edu.qut.data.questions.model.json.validation;

import edu.qut.data.questions.model.json.QuestionRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/** perform validation on the QuestionRequest JSON object */
@Component
public class QuestionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) { return QuestionRequest.class.isAssignableFrom(clazz); }

    @Override
    public void validate(Object target, Errors errors) {

        QuestionRequest request = (QuestionRequest) target;

        if (StringUtils.isEmpty(request.getQuestion())) {
            errors.reject("Input.InvalidJson", "Request does not contain a question");
        }

        if (request.getChoices() == null || request.getChoices().size() == 0) {
            errors.reject("Input.InvalidJson", "Request does not contain any choices");
        }
    }
}

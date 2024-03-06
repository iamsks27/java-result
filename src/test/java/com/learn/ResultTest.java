package com.learn;

import com.learn.bean.Student;
import com.learn.exception.ResultException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author sksingh created on 06/03/24
 */
public class ResultTest {


    @Test
    void itShouldValidateOkResult() {
        Result<?, ?> result = Result.ok(new Student(1L, "John"));

        assertThat(result.isOk()).isTrue();
        assertThat(result.isError()).isFalse();
    }

    @Test
    void itShouldValidateErrorResult() {
        Result<?, ?> result = Result.error(new RuntimeException("Invalid result"));

        assertThat(result.isError()).isTrue();
        assertThat(result.isOk()).isFalse();
    }

    @Test
    void itShouldValidateUnwrapResultValue() throws ResultException {
        Result<String, ?> ok = Result.ok("5");

        assertThat(ok.unwrap()).isEqualTo("5");
    }

    @Test
    void itShouldValidateUnwrapResultThrowException() {
        Result<?, RuntimeException> result = Result.error(new RuntimeException("Exception"));

        assertThatThrownBy(result::unwrap).isInstanceOf(ResultException.class);
    }

    @Test
    void itShouldValidateAndThenOnOkResult() throws ResultException {
        Result<Integer, ?> result = Result.ok("5")
                .andThen(v -> Result.ok(Integer.parseInt(v)));

        assertThat(result.unwrap()).isEqualTo(5);
    }

    @Test
    void itShouldValidateAndThenOnErrorResult() {
        Result<?, String> result = Result.error("bar");

        Result<Object, String> res = result.andThen(e -> Result.error("foo"));

        assertThat(res).isEqualTo(result);
    }

    @Test
    void itShouldValidateMapOnOkResult() throws ResultException {
        Result<String, Object> ok = Result.ok("5");

        Result<Integer, Object> res = ok.map(Integer::parseInt);

        assertThat(res.unwrap()).isEqualTo(5);
    }

    @Test
    void itShouldValidateMapErrOnErrorResult() {
        Result<Object, RuntimeException> exception = Result.error(new RuntimeException("Exception Occurred!!!"));

        Result<Object, String> result = exception.mapError(Throwable::getMessage);

        if (result.getError().isPresent()) {
            assertThat(result.getError().get()).isExactlyInstanceOf(String.class);
            assertThat(result.getError().get()).isEqualTo("Exception Occurred!!!");
        }
    }

    @Test
    void itShouldValidateMapErrWithOkResult() {
        Result<String, Throwable> ok = Result.ok("foo");

        // Lambda shouldn't be invoked for ok result
        ok.mapError(e -> {
            throw new RuntimeException(e);
        });


        Result<String, RuntimeException> result = ok.mapError(RuntimeException::new);

        assertThat(result).isSameAs(ok);
    }
}

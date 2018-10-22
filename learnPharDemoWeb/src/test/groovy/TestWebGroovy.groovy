import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class TestWebGroovy extends Specification {

    @Autowired
    ApplicationContext context

    def "it should boot Spring application successfully"() {
        expect: "application context exists"
        context == null
    }

}

import com.pharm.demo.model.Medicine
import com.pharm.demo.services.MedicineService
import spock.lang.Specification

class TestDataGroovy extends Specification {
     MedicineService medicineService = Mock()
     Medicine medicine = new Medicine();
    def "check medicine service"(){
        given:
        medicineService.findById(20) >> medicine

        when:
        def result = medicineService.findById(20)

        then:
        result == medicine
    }

    def "Should verify notify was called"() {
        given:
        def medicineService = Mock(MedicineService)

        when:
        medicineService.findById(0)

        then:
        1 * medicineService.findById(0)

    }

    def "two plus two should equal four"() {
        given:
        int left = 2
        int right = 2

        when:
        int result = left + right

        then:
        result == 4
    }

    def "numbers to the power of two"(int a, int b, int c) {
        expect:
        Math.pow(a, b) == c

        where:
        a | b | c
        1 | 2 | 1
        2 | 2 | 4
        3 | 2 | 9
    }

}

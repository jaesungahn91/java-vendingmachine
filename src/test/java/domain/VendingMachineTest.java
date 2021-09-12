package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
import service.VendingMachineService;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("자판기 테스트")
class VendingMachineTest {

    private final VendingMachineService vendingMachineService = new VendingMachineService();

    @Test
    void 자판기_생성(){
        VendingMachine vendingMachine = new VendingMachine(450, vendingMachineService.getRandomCoin(450));
        assertThat(vendingMachine).isNotNull();
    }

    @Test
    @CsvSource({"콜라,20,1500", "사이다,10,1000"})
    void 드링크_채우기(String name, int count, int amount){
        Drink drink = new Drink(name, count, amount);
    }

    @Test
    void 드링크_뽑기(){

    }

    @Test
    void 보유금_차감(){

    }
    @Test
    void 보유금_증가(){

    }
}
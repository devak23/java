package yaml;

import lombok.extern.slf4j.Slf4j;
import statemachine.model.CustomerRegisteredBanks;
import statemachine.util.YamlUtil;

@Slf4j
public class YamlMain {
    public static void main(String[] args) throws Exception {
        CustomerRegisteredBanks customerRegisteredBanks =
                YamlUtil.loadYaml("customer-registered-banks.yaml", CustomerRegisteredBanks.class);

        log.info("CustomerRegisteredBanks: {}", customerRegisteredBanks);
    }
}

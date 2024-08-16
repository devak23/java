package statemachine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerRegisteredBanks {
    private Map<String, String> bankBranch;
}

package com.rnd.artemis.framework.model;

import lombok.*;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ConditionalConfig extends BaseConfig {
    private String criteria;
    private String configurationFile;

    public boolean evaluateCondition(DataRow dataRow) {
        if (!StringUtils.hasText(criteria)) {
            throw new IllegalArgumentException("Conditional configuration is opted in but criteria is not provided");
        }

        Expression expression = new SpelExpressionParser().parseExpression(criteria);
        Object value = expression.getValue(dataRow);

        return switch (value) {
            case Boolean b -> b;
            case Number n -> n.intValue() > 0;
            case String s -> StringUtils.hasText(s);
            case null, default -> false;
        };
    }
}

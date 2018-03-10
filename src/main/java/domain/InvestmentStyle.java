package domain;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Investment style defines what percentage of funds should be allocated to given InvestmentFundType
 *
 * These percentages are stored in a map of 'InvestmentFundType -> Double'
 * For example, investment style with map that contains:
 * InvestmentFundType.POLISH -> 0.4
 * means that 40% of all funds should be allocated to POLISH investment funds for this particular investment style
 */
public enum InvestmentStyle {

    SAFE(ImmutableMap.of(
            InvestmentFundType.POLISH, 0.2,
            InvestmentFundType.FOREIGN, 0.75,
            InvestmentFundType.MONETARY, 0.05
    )),
    BALANCED(ImmutableMap.of(
            InvestmentFundType.POLISH, 0.3,
            InvestmentFundType.FOREIGN, 0.6,
            InvestmentFundType.MONETARY, 0.1
    )),
    AGGRESSIVE(ImmutableMap.of(
            InvestmentFundType.POLISH, 0.4,
            InvestmentFundType.FOREIGN, 0.2,
            InvestmentFundType.MONETARY, 0.4
    ));

    private Map<InvestmentFundType, Double> fundsDistributionPercentages;

    InvestmentStyle(Map<InvestmentFundType, Double> fundsDistributionPercentages) {
        this.fundsDistributionPercentages = fundsDistributionPercentages;
    }

    public Map<InvestmentFundType, Double> getFundsDistributionPercentages() {
        return fundsDistributionPercentages;
    }
}

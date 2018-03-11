package service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import domain.InvestmentFund;
import domain.InvestmentFundAllocation;
import domain.InvestmentFundType;
import domain.InvestmentStyle;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class InvestmentFundAllocator {

    /**
     * Calculates money allocations per given investment funds. Percentage of money allocated to fund of some type is defined in
     * InvestmentStyle.
     * Returns a list of InvestmentFundAllocation objects, which contain info about:
     * - investmentFund
     * - allocated amount of money for this fund
     * - percentage of total amount allocated.
     * Some part of monetaryAmount can be unallocated eg. when there are no given investmentFunds of some type.
     * Unallocated amount is returned in 'InvestmentFundAllocation' object with null investmentFund.
     *
     * @param monetaryAmount amount of money to allocate. Must be greater or equal 0
     * @param investmentFunds given list of investment funds. Cannot be null
     * @param investmentStyle given investment style. Cannot be null
     * @return calculated allocations
     */
    public List<InvestmentFundAllocation> calculateFundAllocations(int monetaryAmount,
                                                    List<InvestmentFund> investmentFunds,
                                                    InvestmentStyle investmentStyle) {
        List<InvestmentFundAllocation> allocations = Lists.newArrayList();
        validateInputParams(monetaryAmount, investmentFunds, investmentStyle);
        if (monetaryAmount == 0) {
            return allocations;
        }

        Map<InvestmentFundType, Double> fundsDistributionPercentages = investmentStyle.getFundsDistributionPercentages();
        int allocatedAmount = 0;

        for (InvestmentFundType investmentFundType : InvestmentFundType.values()) {
            double fundDistributionPercentage = fundsDistributionPercentages.getOrDefault(investmentFundType, 0.0);
            List<InvestmentFund> investmentFundsWithType = filterInvestmentFundsWithType(investmentFunds, investmentFundType);
            if (CollectionUtils.isEmpty(investmentFundsWithType)) {
                continue;
            }

            int amountForGivenFundType = (int) (monetaryAmount * fundDistributionPercentage);
            int amountForSingleFund = amountForGivenFundType / investmentFundsWithType.size();
            int remainingAmountForGivenType = amountForGivenFundType - (amountForSingleFund * investmentFundsWithType.size());

            for (InvestmentFund investmentFund : investmentFundsWithType) {
                int amountToAllocate = amountForSingleFund + remainingAmountForGivenType;
                remainingAmountForGivenType = 0;
                double percentageOfTotal = (double) amountToAllocate / (double) monetaryAmount;

                InvestmentFundAllocation allocation = new InvestmentFundAllocation(investmentFund, amountToAllocate, percentageOfTotal);
                allocatedAmount += amountToAllocate;
                allocations.add(allocation);
            }
        }

        handleNotAllocatedAmount(monetaryAmount, allocations, allocatedAmount);

        return allocations;
    }

    private void handleNotAllocatedAmount(int monetaryAmount, List<InvestmentFundAllocation> allocations, int totalAllocatedAmount) {
        int notAllocatedAmount = monetaryAmount - totalAllocatedAmount;
        if (notAllocatedAmount > 0) {
            double percentageOfTotal = (double) notAllocatedAmount / (double) monetaryAmount;
            InvestmentFundAllocation allocation = new InvestmentFundAllocation(null, notAllocatedAmount, percentageOfTotal);
            allocations.add(allocation);
        }
    }

    private void validateInputParams(int monetaryAmount, List<InvestmentFund> investmentFunds, InvestmentStyle investmentStyle) {
        Preconditions.checkArgument(monetaryAmount >= 0, "Monetary amount must be grater or equal to 0");
        Preconditions.checkNotNull(investmentFunds);
        Preconditions.checkNotNull(investmentStyle);
    }

    private List<InvestmentFund> filterInvestmentFundsWithType(List<InvestmentFund> investmentFunds, InvestmentFundType investmentFundType) {
        return investmentFunds.stream().filter(f -> investmentFundType == f.getInvestmentFundType()).collect(Collectors.toList());
    }
}

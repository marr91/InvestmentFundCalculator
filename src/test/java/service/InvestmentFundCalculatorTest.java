package service;

import com.google.common.collect.ImmutableList;
import domain.InvestmentFund;
import domain.InvestmentFundAllocation;
import domain.InvestmentFundType;
import domain.InvestmentStyle;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


public class InvestmentFundCalculatorTest {

    private static final InvestmentFund POLISH_FUND_1 = new InvestmentFund(1, "Fundusz Polski 1", InvestmentFundType.POLISH);
    private static final InvestmentFund POLISH_FUND_2 = new InvestmentFund(2, "Fundusz Polski 2", InvestmentFundType.POLISH);
    private static final InvestmentFund POLISH_FUND_3 = new InvestmentFund(3, "Fundusz Polski 3", InvestmentFundType.POLISH);
    private static final InvestmentFund FOREIGN_FUND_1 = new InvestmentFund(4, "Fundusz Zagraniczny 1", InvestmentFundType.FOREIGN);
    private static final InvestmentFund FOREIGN_FUND_2 = new InvestmentFund(5, "Fundusz Zagraniczny 2", InvestmentFundType.FOREIGN);
    private static final InvestmentFund FOREIGN_FUND_3 = new InvestmentFund(6, "Fundusz Zagraniczny 3", InvestmentFundType.FOREIGN);
    private static final InvestmentFund MONETARY_FUND_1 = new InvestmentFund(7, "Fundusz Pieniezny 1", InvestmentFundType.MONETARY);

    private InvestmentFundCalculator investmentFundCalculator;

    @Before
    public void setup() {
        investmentFundCalculator = new InvestmentFundCalculator();
    }

    @Test
    public void shouldAllocateAllFundsForSafeStyle() {
        // given
        int totalAmountToAllocate = 10000;
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;
        List<InvestmentFund> investmentFunds = ImmutableList.of(POLISH_FUND_1, POLISH_FUND_2, FOREIGN_FUND_1, FOREIGN_FUND_2,
                FOREIGN_FUND_3, MONETARY_FUND_1);

        // when
        List<InvestmentFundAllocation> allocations = investmentFundCalculator
                .calculateFundAllocations(totalAmountToAllocate, investmentFunds, investmentStyle);

        // then
        assertThat(allocations).hasSize(6);
        assertFundHasExpectedAllocation(allocations, POLISH_FUND_1, 1000);
        assertFundHasExpectedAllocation(allocations, POLISH_FUND_2, 1000);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_1, 2500);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_2, 2500);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_3, 2500);
        assertFundHasExpectedAllocation(allocations, MONETARY_FUND_1, 500);
        assertAllocatedAmount(allocations, totalAmountToAllocate);
    }

    @Test
    public void shouldAllocateAllFundsForBalancedStyle() {
        // given
        int totalAmountToAllocate = 10000;
        InvestmentStyle investmentStyle = InvestmentStyle.BALANCED;
        List<InvestmentFund> investmentFunds = ImmutableList.of(POLISH_FUND_1, POLISH_FUND_2, FOREIGN_FUND_1, FOREIGN_FUND_2,
                FOREIGN_FUND_3, MONETARY_FUND_1);

        // when
        List<InvestmentFundAllocation> allocations = investmentFundCalculator
                .calculateFundAllocations(totalAmountToAllocate, investmentFunds, investmentStyle);

        // then
        assertThat(allocations).hasSize(6);
        assertFundHasExpectedAllocation(allocations, POLISH_FUND_1, 1500);
        assertFundHasExpectedAllocation(allocations, POLISH_FUND_2, 1500);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_1, 2000);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_2, 2000);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_3, 2000);
        assertFundHasExpectedAllocation(allocations, MONETARY_FUND_1, 1000);
        assertAllocatedAmount(allocations, totalAmountToAllocate);
    }

    @Test
    public void shouldAllocateAllFundsForAggresiveStyle() {
        // given
        int totalAmountToAllocate = 10000;
        InvestmentStyle investmentStyle = InvestmentStyle.AGGRESSIVE;
        List<InvestmentFund> investmentFunds = ImmutableList.of(POLISH_FUND_1, POLISH_FUND_2, FOREIGN_FUND_1, FOREIGN_FUND_2,
                FOREIGN_FUND_3, MONETARY_FUND_1);

        // when
        List<InvestmentFundAllocation> allocations = investmentFundCalculator
                .calculateFundAllocations(totalAmountToAllocate, investmentFunds, investmentStyle);

        // then
        assertThat(allocations).hasSize(6);
        assertFundHasExpectedAllocation(allocations, POLISH_FUND_1, 2000);
        assertFundHasExpectedAllocation(allocations, POLISH_FUND_2, 2000);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_1, 668);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_2, 666);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_3, 666);
        assertFundHasExpectedAllocation(allocations, MONETARY_FUND_1, 4000);
        assertAllocatedAmount(allocations, totalAmountToAllocate);
    }

    @Test
    public void shouldContainUnallocatedAmountWhenNoMonetaryFunds() {
        // given
        int totalAmountToAllocate = 10000;
        InvestmentStyle investmentStyle = InvestmentStyle.AGGRESSIVE;
        List<InvestmentFund> investmentFunds = ImmutableList.of(POLISH_FUND_1, POLISH_FUND_2, FOREIGN_FUND_1, FOREIGN_FUND_2,
                FOREIGN_FUND_3);

        // when
        List<InvestmentFundAllocation> allocations = investmentFundCalculator
                .calculateFundAllocations(totalAmountToAllocate, investmentFunds, investmentStyle);

        // then
        assertThat(allocations).hasSize(6);
        assertFundHasExpectedAllocation(allocations, POLISH_FUND_1, 2000);
        assertFundHasExpectedAllocation(allocations, POLISH_FUND_2, 2000);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_1, 668);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_2, 666);
        assertFundHasExpectedAllocation(allocations, FOREIGN_FUND_3, 666);
        assertFundHasExpectedAllocation(allocations, null, 4000);
        assertAllocatedAmount(allocations, totalAmountToAllocate);
    }

    @Test
    public void shouldReturnEmptyAllocationsWhenAmountToAllocateIsZero() {
        // given
        int totalAmountToAllocate = 0;
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;
        List<InvestmentFund> investmentFunds = ImmutableList.of(POLISH_FUND_1, FOREIGN_FUND_1, MONETARY_FUND_1);

        // when
        List<InvestmentFundAllocation> allocations = investmentFundCalculator
                .calculateFundAllocations(totalAmountToAllocate, investmentFunds, investmentStyle);

        // then
        assertThat(allocations).isEmpty();
    }

    @Test
    public void shouldReturnOnlyUnassignedAllocationWhenNoInvestmentFunds() {
        // given
        int totalAmountToAllocate = 10000;
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;
        List<InvestmentFund> investmentFunds = ImmutableList.of();

        // when
        List<InvestmentFundAllocation> allocations = investmentFundCalculator
                .calculateFundAllocations(totalAmountToAllocate, investmentFunds, investmentStyle);

        // then
        assertThat(allocations).hasSize(1);
        InvestmentFundAllocation unallocated = allocations.get(0);
        assertThat(unallocated.getAllocatedAmount()).isEqualTo(totalAmountToAllocate);
        assertThat(unallocated.getPercentageOfTotal()).isEqualTo(1.0);
        assertThat(unallocated.getInvestmentFund()).isNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenNegativeAmountToAllocate() {
        // given
        int totalAmountToAllocate = -5000;
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;
        List<InvestmentFund> investmentFunds = ImmutableList.of();

        // when + then
        investmentFundCalculator.calculateFundAllocations(totalAmountToAllocate, investmentFunds, investmentStyle);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenInvestmentStyleNull() {
        // given
        int totalAmountToAllocate = 15000;
        InvestmentStyle investmentStyle = null;
        List<InvestmentFund> investmentFunds = ImmutableList.of(POLISH_FUND_1);

        // when + then
        investmentFundCalculator.calculateFundAllocations(totalAmountToAllocate, investmentFunds, investmentStyle);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionWhenInvestmentFundsNull() {
        // given
        int totalAmountToAllocate = 15000;
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;
        List<InvestmentFund> investmentFunds = null;

        // when + then
        investmentFundCalculator.calculateFundAllocations(totalAmountToAllocate, investmentFunds, investmentStyle);
    }

    private static void assertAllocatedAmount(List<InvestmentFundAllocation> allocations, int totalAmountToAllocate) {

        assertThat(allocations.stream().mapToInt(InvestmentFundAllocation::getAllocatedAmount).sum()).isEqualTo(totalAmountToAllocate);
    }

    private static void assertFundHasExpectedAllocation(List<InvestmentFundAllocation> allocations, InvestmentFund fund, int expectedAllocation) {
        assertThat(allocations).anyMatch(f -> f != null &&
                Objects.equals(f.getInvestmentFund(), fund) &&
                f.getAllocatedAmount() == expectedAllocation);
    }
}

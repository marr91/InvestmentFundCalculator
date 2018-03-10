package domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class InvestmentFundAllocation {

    private InvestmentFund investmentFund;

    private int allocatedAmount;

    private double percentageOfTotal;

    public InvestmentFundAllocation(InvestmentFund investmentFund, int allocatedAmount, double percentageOfTotal) {
        this.investmentFund = investmentFund;
        this.allocatedAmount = allocatedAmount;
        this.percentageOfTotal = percentageOfTotal;
    }

    public InvestmentFund getInvestmentFund() {
        return investmentFund;
    }

    public void setInvestmentFund(InvestmentFund investmentFund) {
        this.investmentFund = investmentFund;
    }

    public int getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(int allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public double getPercentageOfTotal() {
        return percentageOfTotal;
    }

    public void setPercentageOfTotal(double percentageOfTotal) {
        this.percentageOfTotal = percentageOfTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvestmentFundAllocation that = (InvestmentFundAllocation) o;
        return allocatedAmount == that.allocatedAmount &&
                Double.compare(that.percentageOfTotal, percentageOfTotal) == 0 &&
                Objects.equal(investmentFund, that.investmentFund);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(investmentFund, allocatedAmount, percentageOfTotal);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("investmentFund", investmentFund)
                .add("allocatedAmount", allocatedAmount)
                .add("percentageOfTotal", percentageOfTotal)
                .toString();
    }
}

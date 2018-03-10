package domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;


public class InvestmentFund {
    private int id;

    private String name;

    private InvestmentFundType investmentFundType;

    public InvestmentFund(int id, String name, InvestmentFundType investmentFundType) {
        this.id = id;
        this.name = name;
        this.investmentFundType = investmentFundType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InvestmentFundType getInvestmentFundType() {
        return investmentFundType;
    }

    public void setInvestmentFundType(InvestmentFundType investmentFundType) {
        this.investmentFundType = investmentFundType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvestmentFund that = (InvestmentFund) o;
        return id == that.id &&
                Objects.equal(name, that.name) &&
                investmentFundType == that.investmentFundType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, investmentFundType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("investmentFundType", investmentFundType)
                .toString();
    }
}

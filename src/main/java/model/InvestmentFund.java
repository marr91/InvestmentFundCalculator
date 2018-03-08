package model;

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


}

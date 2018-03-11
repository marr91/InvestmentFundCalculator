# InvestmentFundCalculator

Project contains service for allocating money to investment funds.  
Amount of allocated money is based on fund type and investment style.  

Investment style defines what percentage of funds should be allocated to funds with given InvestmentFundType.  
For example, for 'safe' style:  
20% of funds are allocated to polish investmentFunds  
75% of funds are allocated to foreign investmentFunds  
5% of funds are allocated to monetary investmentFunds  
  
To calculate allocations for investment funds, we need:  
- amount of money to allocate  
- list of InvestmentFund  
- InvestmentStyle (safe/balanced/aggressive)  

Example:  
amount of money to allocate: 10000 PLN  
investment style: safe  
investment funds:  

| id | type     | name                  |
|----|----------|-----------------------|
| 1  | polish   | Fundusz Polski 1      |
| 2  | polish   | Fundusz Polski 2      |
| 3  | foreign  | Fundusz Zagraniczny 1 |
| 4  | foreign  | Fundusz Zagraniczny 2 |
| 5  | foreign  | Fundusz Zagraniczny 3 |
| 6  | monetary | Fundusz Pieniężny 1   |



Calculated allocations:  
id  type        name                    allocated_amount    percentage_of_total  
1   polish      Fundusz Polski 1        1000 PLN            10%  
2   polish      Fundusz Polski 2        1000 PLN            10%  
3   foreign     Fundusz Zagraniczny 1   2500 PLN            25%  
4   foreign     Fundusz Zagraniczny 2   2500 PLN            25%  
5   foreign     Fundusz Zagraniczny 3   2500 PLN            25%  
6   monetary    Fundusz Pieniężny 1     500 PLN              5%  


# BondYieldCalculator

This is a console app that can calculate the bond price and yield from the user inputs. 

The background of the explanations of the terms can be found ![here](https://www.investopedia.com/terms/b/bond-yield.asp).

Given a coupon payment C, a time of N years, a face value F, the price P of the bond for a given rate r is:

P = C / (1 + r) ^ 1 + C / (1 + r) ^ 2 + ... + C / (1 + r) ^ N + F / (1 + r) ^ N

If the price is known and the rate r can also be calculated through this equation.

If we are calculating r, the polynomial equation can be difficult to solve. We can use ![Newton's Method](https://en.wikipedia.org/wiki/Newton%27s_method)
to approximate the value into our desired range.

Follow the instructions provided to input your variable values, the result will come out in a precision of 10^-7.



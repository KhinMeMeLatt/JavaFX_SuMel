## SuMel
Money saving for the purpose of different categories. Withdrawing as much as user's need and breaking the moneybox. Expenses can be managed. User can track all of
his/her activities with high visualized formative charts. It also supports foreign currencies converting.

## Functionalities
- Save money in two options: Saving systematically (provided the saving plan) for the desire purpose, Saving as much as you want for general purpose at any time
- Can withdraw or break the money box whenever you need
- Flexibility in changing the circumstances such as saving plan, so on
- Allow to set Expense Limitation and easily input your frequent expenses all at once
- Can quickly check the expected spending and actual expenses by each category or by each budget plan
- Easy-to-understand visualization graphs of your budgeting and money savingby expense category or saving goals
- Gain the clean tracking history of all the action you did by time
- Secure your account with password encryption
- Automatically calculate the saving amount according to the options you choose
- Adding custom or default categories of expense
- Multiple currency exchange

## Tutorial
Tutorial can be watched [here](https://github.com/KhinMeMeLatt/SuMel/tree/main/src/assets/About.mp4).

## Installation

### Prerequisites
- To run this project, you must have Java 15 and JavaFX15

### Step 1
Begin by cloning this repository to your machine
```bash
git clone https://github.com/KhinMeMeLatt/JavaFX_SuMel.git
```

### Step 2
- Create sumeldb database
- Import sql files to your database
- Need to change the username and password in DBConnection.java

### Exchange Rate API
- This project use Exchange Rate API. You can get it from [ExchangeRate API website](https://www.exchangerate-api.com/docs/supported-currencies)

Please replace your api key in the following statement.

```bash
String url_str = "https://v6.exchangerate-api.com/v6/588e05b36428d517c77d7639/latest/"+baseCountryCode;
```

## Future Works
- Performance Enhancement
- Multi-language
- Notify the system user to save money according to saving plan
- Backup and export bill transferring, and income

## Build With
- [Java](https://docs.oracle.com/en/java/)
- [JavaFX](https://openjfx.io/)

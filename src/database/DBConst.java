package database;

/**
 * 
 * @author UIT NTT-AT Scholarship Students
 *
 */
public class DBConst {

	//Database Tables' Names
	public static final String USER_TABLE = "user";
	public static final String SAVE_TABLE = "save";
	public static final String WITHDRAW_TABLE = "withdraw";
	public static final String GOAL_TABLE = "goal";
	public static final String EXPENSE_TABLE = "expense";
	
	//Columns' Names of User Table
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String TARGET_EXPENSE = "targetExpense"; 
	
	//Columns' Name of Save Table
	public static final String SAVE_ID = "saveId";
	public static final String SAVE_AMOUNT = "saveAmount";
	public static final String SAVED_AT = "saveAt";
	public static final String SAVE_GOAL_ID = "goalId";
	
	//Columns' Names of Withdraw Table
	public static final String WITHDRAW_ID = "withdrawId";
	public static final String WITHDRAW_AMOUNT = "withdrawAmount";
	public static final String WITHDRAW_AT = "withdrawAt";
	public static final String WITHDRAW_GOAL_ID = "goalId";
	
	//Columns' Names of Goal Table
	public static final String GOAL_ID = "goalId";
	public static final String GOAL_NAME = "goalName";
	public static final String GOAL_IMAGE_NAME = "goalImgName";
	public static final String GOAL_AMOUNT = "goalAmount";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String SAVE_TYPE = "saveType";
	public static final String AMOUNT_TO_SAVE = "amountToSave";
	public static final String IS_BREAK = "isBreak";
	public static final String GOAL_USER_ID = "userId";
	
	//Columns' Names of Expense Table
	public static final String EXPENSE_ID = "expenseId";
	public static final String EXPENSE_NAME = "expenseName";
	public static final String EXPENSE_CATEGORY = "expenseCategory";
	public static final String EXPENSE_AMOUNT = "expenseAmount";
	public static final String SPEND_AT = "spendAt";
	public static final String EXPENSE_USER_ID = "userId";
}

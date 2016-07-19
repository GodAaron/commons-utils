package com.wf2311.commons.persist.lang;

public interface Condition {
	String BETWEEN = "BETWEEN";
	
	String GREATNESS = "GRENTNESS";
	
	String SMALLNESS = "SMALLNESS";
	
	String GREATNESS_AND_EQUALS = "GREATNESS_AND_EQUALS";
	
	String SMALLNESS_AND_EQUALS = "SMALLNESS_AND_EQUALS";
	
	String LIKE = "LIKE";
	
	String START = "START";
	
	String END = "END";
	
	String EQUALS = "EQUALS";

	String NOT_NULL = "NOT_NULL";
	
	String NULL = "IS_NULL";
	
	String NOT_IN = "NOT_IN";
	
	String OR = "OR";
	
	String IN = "IN";
	
	String NOT = "NOT";
	
	String LEFT = "LEFT";
	
	String LEFT_EQ = "LEFT_EQ";
	
	String RIGHT_EQ = "RIGHT_EQ";
	
	String RIGHT = "RIGHT";
	
	String BETWEEN_SPLIT = "-BTW-";
	
	String IS_NOT_NULL = "NOT_NULL";
	
	
	String IS_NULL = "IS_NULL";
	
	String NOT_EQUALS = "NOT_EQUALS";
	
	String getProperty();
	
	String getOperator();
	
	Object getValue();
}

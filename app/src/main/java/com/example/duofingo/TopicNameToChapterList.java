package com.example.duofingo;

import java.util.HashMap;
import java.util.Map;

public class TopicNameToChapterList {
    Map<String, String[]> map = new HashMap<>();

    String[] budgetingArray = new String[]{"Budgeting1", "Budgeting2", "Budgeting3"};
    String[] investingArray = new String[]{"Investing1", "Investing2", "Investing3"};
    String[] taxArray = new String[]{"Tax1", "Tax2", "Tax3"};
    String[] debtArray = new String[]{"Debt1", "Debt2", "Debt3"};
    String[] homeOwnershipArray = new String[]{"Home Ownership1", "Home Ownership2", "Home Ownership3"};
    String[] savingsArray = new String[]{"Savings1", "Savings2", "Savings3"};
    String[] netWorthArray = new String[]{"Net Worth1", "Net Worth2", "Net Worth3"};
    String[] creditArray = new String[]{"Credit1", "Credit2", "Credit3"};

    public TopicNameToChapterList() {
        map.put("Budgeting", budgetingArray);
        map.put("Investing", investingArray);
        map.put("Taxes", taxArray);
        map.put("Debt", debtArray);
        map.put("Home Ownership", homeOwnershipArray);
        map.put("Savings", savingsArray);
        map.put("Net Worth", netWorthArray);
        map.put("Credit", creditArray);
    }
}

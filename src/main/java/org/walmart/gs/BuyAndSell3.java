package org.walmart.gs;

public class BuyAndSell3 {
    public int maxProfit(int[] prices) {
        int firstBuy = Integer.MIN_VALUE, firstSell = 0;
        int secondBuy = Integer.MIN_VALUE, secondSell = 0;
        for(int price : prices){
            firstBuy = Math.max(firstBuy, -price);
            firstSell = Math.max(firstSell, firstBuy + price);
            secondBuy = Math.max(secondBuy, firstSell - price);
            secondSell = Math.max(secondSell, secondBuy + price);
        }
        return secondSell;
    }

    public int maxProfit(int k, int[] prices) {
        if(prices == null || prices.length < 2) return 0;
        if(k < 1) return 0;

        int buy[] = new int[k];
        int sell[] = new int[k];

        for(int i = 0; i < k; i++){
            buy[i] = Integer.MIN_VALUE;
            sell[i] = 0;
        }

        for(int i = 0; i < prices.length; i++){
            buy[0] = Math.max(buy[0], -prices[i]);
            sell[0] = Math.max(sell[0], buy[0] + prices[i]);
            for(int j = 1; j < k; j++){
                buy[j] = Math.max(buy[j], sell[j-1] - prices[i]);
                sell[j] = Math.max(sell[j], buy[j] + prices[i]);
            }
        }
        return sell[k-1];
    }

    public static void main(String[] args) {
        BuyAndSell3 buyAndSell3 = new BuyAndSell3();
        //System.out.println(buyAndSell3.maxProfit(new int[] {3,3,5,0,0,3,1,4}));
        System.out.println(buyAndSell3.maxProfit(2, new int[] {2, 4, 1}));
    }
}

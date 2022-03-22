package org.walmart.gs;

public class TrappingRainWater {
    public int trap(int[] height) {
        if(height == null || height.length == 0) return 0;
        int left = 0;
        int right = height.length - 1;
        int leftMax = Integer.MIN_VALUE;
        int rightMax = Integer.MIN_VALUE;
        int totalValue = 0;
        while(left < right){
            if(height[left] < height[right]) {
                if(height[left] >= leftMax)
                    leftMax = height[left];
                else {
                    totalValue += leftMax - height[left];
                }
                left++;
            }
            else {
                if(height[right] >= rightMax)
                    rightMax = height[right];
                else {
                    totalValue += rightMax - height[right];
                }
                right--;
            }
        }
        return totalValue;
    }

    public static void main(String[] args) {
        TrappingRainWater trappingRainWater = new TrappingRainWater();
        trappingRainWater.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1});
    }
}

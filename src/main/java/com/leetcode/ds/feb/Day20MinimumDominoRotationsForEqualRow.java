package com.leetcode.ds.feb;

/**
 * In a row of dominoes, tops[i] and bottoms[i] represent the top and bottom halves of the ith domino. (A domino is a tile with two numbers from 1 to 6 - one on each half of the tile.)
 *
 * We may rotate the ith domino, so that tops[i] and bottoms[i] swap values.
 *
 * Return the minimum number of rotations so that all the values in tops are the same, or all the values in bottoms are the same.
 *
 * If it cannot be done, return -1.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: tops = [2,1,2,4,2,2], bottoms = [5,2,6,2,3,2]
 * Output: 2
 * Explanation:
 * The first figure represents the dominoes as given by tops and bottoms: before we do any rotations.
 * If we rotate the second and fourth dominoes, we can make every value in the top row equal to 2, as indicated by the second figure.
 * Example 2:
 *
 * Input: tops = [3,5,1,2,3], bottoms = [3,6,3,3,4]
 * Output: -1
 * Explanation:
 * In this case, it is not possible to rotate the dominoes to make one row of values equal.
 *
 *
 * Constraints:
 *
 * 2 <= tops.length <= 2 * 104
 * bottoms.length == tops.length
 * 1 <= tops[i], bottoms[i] <= 6
 *
 */
public class Day20MinimumDominoRotationsForEqualRow {
    public static int minDominoRotations(int[] tops, int[] bottoms) {
        int minStep = Integer.MAX_VALUE;
        int topCount =0;
        int bottomCount=0;
        boolean possible = true;
        for (int i=1; i<=6; i++){
            topCount =0;
            bottomCount=0;
            possible = true;

            for(int j=0;j< tops.length;j++){
                if(tops[j]!=i && bottoms[j]!=i){
                    possible = false;
                    break;
                }else if(tops[j]!=i && bottoms[j]==i){
                    topCount++;
                }else if(tops[j]==i && bottoms[j]!=i){
                    bottomCount++;
                }else{
                    //top and bottom both are equal, no rotation needed
                }
            }

            if(possible){
                minStep = Math.min(minStep, Math.min(topCount, bottomCount));
            }
        }


        return minStep==Integer.MAX_VALUE ? -1:minStep;
    }

    public static void main(String[] args) {
        //int[] tops = {2,1,2,4,2,2};
        //int[] bottoms = {5,2,6,2,3,2};

        //int[] tops = {3,5,1,2,3};
        //int[] bottoms = {3,6,3,3,4};

        int[] tops =    {6,1,6,4,6,6};
        int[] bottoms = {5,6,2,6,3,6};

        System.out.println(minDominoRotations(tops, bottoms));
    }
}

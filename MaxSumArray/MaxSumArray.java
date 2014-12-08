/*
 * Given an array of positive/negative integers, return the max sum possible
 * by adding up any contiguous portion of the array. The empty array is a
 * valid portion (i.e. answer cannot be < 0)
 **/
public class MaxSumArray
{
    public static long maxSumSubArray(int[] a)
    {
        long maxSum = 0;
        long runningSum = 0;

        for(int i = 0; i < a.length; i++)
        {
            runningSum += a[i];
            if (runningSum > maxSum)
            {
                maxSum = runningSum;
            }
            else if (runningSum < 0)
            {
                runningSum = 0;
            }
        }
        return maxSum;
    }

    public static void main (String [] args)
    {
        long result = maxSumSubArray(new int[]{1, 3, -5, 4, 8, -100, 100});
        System.out.println("t1:" + (result == 100));
        long result2 = maxSumSubArray(new int[]{1, 3, -5, 4, 8, -100, 10});
        System.out.println("t2:" + (result2 == 12));
        long result3 = maxSumSubArray(new int[]{1, 3, -2, 4, 8, -4, 10});
        System.out.println("t3:" + (result3 == 20));
        long result4 = maxSumSubArray(new int[]{1, 7, 0, -3, 1, 2, -6, 5});
        System.out.println("t4:" + (result4 == 8));
        long result5 = maxSumSubArray(new int[]{-3, -5, -8, -4});
        System.out.println("t5:" + (result5 == 0));
    }
}

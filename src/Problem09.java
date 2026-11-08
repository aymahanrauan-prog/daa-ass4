public class Problem09 {

    public int maxSubArray(int[] nums) {
        int current = nums[0];
        int best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            current = Math.max(nums[i], current + nums[i]);
            best = Math.max(best, current);
        }
        return best;
    }

    public int maxSubArrayDivideAndConquer(int[] nums) {
        return solve(nums, 0, nums.length - 1);
    }

    private int solve(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }
        int mid = (left + right) / 2;
        int leftBest = solve(nums, left, mid);
        int rightBest = solve(nums, mid + 1, right);

        int sum = 0;
        int leftCross = Integer.MIN_VALUE;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftCross = Math.max(leftCross, sum);
        }
        sum = 0;
        int rightCross = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightCross = Math.max(rightCross, sum);
        }
        return Math.max(Math.max(leftBest, rightBest), leftCross + rightCross);
    }
}

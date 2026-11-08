# Assignment 4 - Dynamic Programming Report

All solutions are in `src/ProblemXX.java`. Every solution was compiled and tested on all examples from the task. Problems 04 and 09 were also cross-checked on 2000 random arrays (two different algorithms must give the same answer).

## Summary

| Problem | Task | DP state | Time | Space |
|---|---|---|---|---|
| 01 | Counting bits | `ans[i]` = number of 1 bits in `i` | O(n) | O(n) |
| 02 | Jump game II | Greedy: farthest reachable position (not a table DP) | O(n) | O(1) |
| 03 | Min cost climbing stairs | `dp[i]` = min cost to reach step `i` | O(n) | O(n) |
| 04 | Longest increasing subsequence | `dp[i]` = LIS length ending at `i` | O(n^2) (follow-up: O(n log n)) | O(n) |
| 05 | Longest common subsequence | `dp[i][j]` = LCS of first `i` and first `j` characters | O(n*m) | O(n*m) |
| 06 | Edit distance | `dp[i][j]` = min operations for first `i` and first `j` characters | O(n*m) | O(n*m) |
| 07 | Longest palindromic subsequence | `dp[i][j]` = LPS length inside `s[i..j]` | O(n^2) | O(n^2) |
| 08 | Palindromic substrings | `dp[i][j]` = is `s[i..j]` a palindrome | O(n^2) | O(n^2) |
| 09 | Maximum subarray | `current` = best sum ending at `i` (Kadane) | O(n) | O(1) |
| 10 | House robber | `dp[i]` = max money from first `i+1` houses | O(n) | O(n) |

## Problem 01 - Counting Bits

**Idea.** `ans[i]` is the number of 1 bits in the binary form of `i`. The number `i >> 1` is `i` without its last bit, so it is smaller and its answer is already computed. The removed last bit is `i & 1`.

**Recurrence.** `ans[i] = ans[i >> 1] + (i & 1)`, with `ans[0] = 0`.

**Example.** `ans[5]`: `5 = 101`, `5 >> 1 = 2 = 10` has one 1 bit, last bit is 1, so `ans[5] = 1 + 1 = 2`.

**Complexity.** One loop: O(n) time, O(n) memory (the result array).

## Problem 02 - Jump Game II

**Idea.** The solution is greedy, but it uses the same "best answer so far" thinking as DP. We go through the array and keep two values: `curEnd` is the farthest index we can reach with the current number of jumps, and `farthest` is the farthest index we can reach with one more jump. When we reach `curEnd`, we must make a jump, so `jumps++` and `curEnd = farthest`.

**Update rule.** `farthest = max(farthest, i + nums[i])`. If `i == curEnd`: `jumps++`, `curEnd = farthest`. The loop stops before the last index, because we do not need to jump from it.

**Example.** `nums = [2,3,1,1,4]`: the first jump reaches index 2, the second jump reaches the end, so the answer is 2.

**Complexity.** One pass: O(n) time, O(1) memory. (A table DP `dp[i] = min(dp[j] + 1)` would be O(n^2).)

## Problem 03 - Min Cost Climbing Stairs

**Idea.** The top is position `n`. We can arrive there from step `n-1` (pay `cost[n-1]`) or from step `n-2` (pay `cost[n-2]`). We can start on step 0 or 1 for free, so `dp[0] = dp[1] = 0`.

**Recurrence.** `dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])`. Answer: `dp[n]`.

**Complexity.** One loop: O(n) time, O(n) memory (can be O(1) with two variables).

## Problem 04 - Longest Increasing Subsequence

**Idea.** `dp[i]` is the length of the longest strictly increasing subsequence that ends exactly at `nums[i]`. Every element alone gives length 1.

**Recurrence.** `dp[i] = max(dp[j] + 1)` for all `j < i` with `nums[j] < nums[i]`. Answer: the maximum value in `dp`.

**Complexity.** Two nested loops: O(n^2) time, O(n) memory. This is fine for n <= 2500.

**Follow-up (O(n log n)).** Method `lengthOfLISFast` keeps an array `tails`, where `tails[k]` is the smallest possible last number of an increasing subsequence of length `k+1`. For every number we find its place with binary search and replace that value (or add a new one at the end). The size of `tails` is the answer. Time O(n log n), space O(n).

## Problem 05 - Longest Common Subsequence

**Idea.** `dp[i][j]` is the LCS length of `text1[0..i-1]` and `text2[0..j-1]`. Row 0 and column 0 are 0 (empty string).

**Recurrence.**
- If the last characters are equal: `dp[i][j] = dp[i-1][j-1] + 1`.
- Otherwise: `dp[i][j] = max(dp[i-1][j], dp[i][j-1])`.

**Complexity.** O(n*m) time and memory.

## Problem 06 - Edit Distance

**Idea.** `dp[i][j]` is the minimum number of operations to turn the first `i` characters of `word1` into the first `j` characters of `word2`. Base cases: `dp[i][0] = i` (delete all) and `dp[0][j] = j` (insert all).

**Recurrence.**
- If characters are equal: `dp[i][j] = dp[i-1][j-1]`.
- Otherwise: `dp[i][j] = 1 + min(dp[i-1][j-1] (replace), dp[i-1][j] (delete), dp[i][j-1] (insert))`.

**Complexity.** O(n*m) time and memory.

## Problem 07 - Longest Palindromic Subsequence

**Idea.** `dp[i][j]` is the longest palindromic subsequence inside `s[i..j]`. One character is a palindrome of length 1, so `dp[i][i] = 1`. We fill the table with `i` from the end to the start, so `dp[i+1][j-1]` is already known.

**Recurrence.**
- If `s[i] == s[j]`: `dp[i][j] = dp[i+1][j-1] + 2`.
- Otherwise: `dp[i][j] = max(dp[i+1][j], dp[i][j-1])`.

Answer: `dp[0][n-1]`.

**Complexity.** O(n^2) time and memory.

## Problem 08 - Palindromic Substrings

**Idea.** `dp[i][j]` is `true` if substring `s[i..j]` is a palindrome. Every `true` cell is one palindromic substring, so we count them.

**Recurrence.** `dp[i][j]` is true when `s[i] == s[j]` and (the length is 1 or 2, or `dp[i+1][j-1]` is true).

**Complexity.** O(n^2) time and memory.

## Problem 09 - Maximum Subarray

**Idea (Kadane).** `current` is the best sum of a subarray that ends at the current index. At each element we choose: start a new subarray here, or extend the previous one.

**Recurrence.** `current = max(nums[i], current + nums[i])`, `best = max(best, current)`.

**Complexity.** O(n) time, O(1) memory.

**Follow-up (divide and conquer).** Method `maxSubArrayDivideAndConquer` splits the array in the middle. The best subarray is in the left half, in the right half, or crosses the middle. The crossing sum is the best suffix of the left half plus the best prefix of the right half. Time O(n log n), memory O(log n) for recursion.

## Problem 10 - House Robber

**Idea.** `dp[i]` is the maximum money from houses `0..i`. For house `i` there are two choices: skip it, or rob it and skip house `i-1`.

**Recurrence.** `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`, with `dp[0] = nums[0]` and `dp[1] = max(nums[0], nums[1])`. A single house is a special case.

**Complexity.** O(n) time, O(n) memory (can be O(1)).

## Testing

Problem 01 and Problem 02 were tested too: `countBits(5)` gives `[0,1,1,2,1,2]`, `jump([2,3,1,1,4])` gives 2, `jump([2,3,0,1,4])` gives 2, `jump([1])` gives 0. The greedy `jump` matched a slow O(n^2) DP on 3000 random arrays.

All examples from the task give the expected output (for example: Problem 03 -> 15 and 6, Problem 06 -> 3 and 5, Problem 08 -> 3 and 6, Problem 10 -> 4 and 12). Extra edge cases: empty `word1` in Problem 06, one-character strings in Problem 07, one house in Problem 10. In Problem 04 and Problem 09 the fast / divide-and-conquer versions match the main DP version on 2000 random arrays.

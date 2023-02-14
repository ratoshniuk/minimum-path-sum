I split that program into small subtasks.

I almost at start observed issues during reading from command line input.
I decided to be focused on main task - writing efficient algorithm and I would be grateful for understanding

First subtask was - efficient way of scanning input.

I created default way of reading file since error with command line i described above

But I wanted to scan in linearly using iterator. Since we know that input is triangle, means that each next line will be 1 item more
I could iterate through each ```level + i``` token. Each iteration of this traversing could help me to work with raw stream.

Current function ```prepare2dVector``` reads file and converts that into 2 dimensional vector.
First dimension defines level of deepness of our "binary tree". second dimension is its position on level.
Left leaf will always have same 2nd coordinate as it's parent, right will be patent's Ith coordinate + 1.
Since our data structure is "triangle" it means that we will always have i and i+1 on next level.
So we don't need to check for out of bound

I choose vector as main data structure since it allows to get access by index though O(1)

Function ```traverseNodes``` describes exactly main algorithm and I want to focus on that.

First implementation had the following benchmarks:
- time complexity - log(n)
- space complexity - O(1) since we only used three variables and changed their values, so assume it is constant

The basic idea is to look at the input as a binary tree which always have two leafs.
we can skip checking for cases if leaf is nullable.

I decided to use recursion here for going deep until leaf will not have a child and after that we can simply return its value
Otherwise, algorithm applies calculation of two sums - with right and left leaf respectively.
Once we compare them we can choose minimum sum on this level and push that upper until we got first call in our stack of calls

On level 1 we have 1 call if f(n), which causes 2 calls of f(n)
On level 2 we have 2 calls of f(n) for each leaf, and each leaf call own leafs and same way until we don't have leafs on latest level

After running that algorithm on a small data structure I verified that it works correct and used larger data structure.

Obviously I saw almost infinite execution time for 500 lines of code and knew the reason.
We are making using recursion almost always calculation of same information we did previously.

So to reduce execution time I decided to increase space complexity by creating dictionary using mutable.HashMap.
According to scala official documentation it's quite efficient and does put and get operation on constant time O(1).
So our time complexity will not be affected

Space complexity in worst case will be O(k^2), where k is number of levels of our 2d vector.

The major change after adding memoization is that before execution of algorithms
it checks whether for current node which locates on k-level and on i-th position exists already calculated result.
Otherwise, it makes recursion calls and performs calculation. After that it puts in dictionary result.

As key of dictionaty I am using Tuple2[Int, Int], where first is K-level, second - ith coordinate on current level.
It's quite efficient data structure in Scala does not take too much space in memory.

> Thoughts about what could be done better

1. I really wanted to discover better way of parsing input,
   but decided to focus mostly on algorithm because input can be different rather can cmd.
   I am sorry that I could figure out quickly the reason why I am getting empty args

2.I was trying to apply here tail recursion,
but I decide to leave as is because I didn't want to over complex this solution.
I am not sure that it will significantly improve performance.

3. I don't like using mutual data structures and almost never used in Scala.
   But for personal reason in case of writing algorithms from scratch I prefer use them rather put extra arguments in functions.
   It helps me to be focused mostly on target algorithm. And later of course improve solution to be more functional.
   It breaks referential transparency but i like Scala that it can be used either purely functional when I should write ReaderMonad, StateT, Kleisli
   or just use mutable structures and play around algorithmic problems such this 




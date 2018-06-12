package cn.jing.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

@Slf4j
public class ForkJoinTaskExample extends RecursiveTask<Integer> {

	public static final int threshold = 2;
	private int start; // 1
	private int end; // 100

	public ForkJoinTaskExample(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		int sum = 0;

		// 如果任务足够小就直接计算任务
		boolean canCompute = (end - start) <= threshold;
		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum += i;
			}
		} else {
			// 如果任务大于阈值，就分裂成两个子任务计算
			int middle = (start + end) / 2;
			// 双向递归拆分成各个子任务
			ForkJoinTaskExample leftTask = new ForkJoinTaskExample(start, middle);
			ForkJoinTaskExample rightTask = new ForkJoinTaskExample(middle + 1, end);

			// 执行子任务
			leftTask.fork();
			rightTask.fork();

			// 等待各个子任务执行结束后合并其结果
			int leftResult = leftTask.join();
			int rightResult = rightTask.join();

			// 合并子任务
			sum = leftResult + rightResult;
		}
		return sum;
	}

	public static void main(String[] args) {
		// 负责实现，工作窃取算法，管理工作线程，管理任务的状态信息
		ForkJoinPool forkjoinPool = new ForkJoinPool();

		// 生成一个计算任务，计算1+2+3+4(此处表示从1加到100)
		ForkJoinTaskExample task = new ForkJoinTaskExample(1, 100);

		// 执行一个任务
		Future<Integer> result = forkjoinPool.submit(task);

		try {
			log.info("result:{}", result.get());
		} catch (Exception e) {
			log.error("exception", e);
		}
	}
}

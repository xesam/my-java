## synchronized 缺陷

线程数目 = 核心数目 /(1 - 任务的阻塞系数)

synchronized 关键字的粒度太大，没有提供当线程没有获取到锁的情况下的超时逻辑，而且也不允许对互斥区域的并发读。
另外，如果我们使用了 synchronized， 那么关于线程安全方面的单元测试也很难进行。


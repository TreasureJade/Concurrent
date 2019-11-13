## 线程安全-并发容器 J.U.C
1. 并发容器    
   - ArrayList -> CopyOnWriteArrayList
        - 当有新元素添加到CopyOnWriteArrayList，先从原有的数组里边拷贝一份出来，然后在新的数组上进行写操作，写完之后将原来的数组指向新的数组
        - 所有的add操作都是在锁的保护下进行的
        - 缺点：
        - 会另外开辟空间
        - 不能进行实时读的需求
    - 多用于多读少写的操作    
    
   - HashSet -> CopyOnWriteArraySet(底层通过CopyOnWriteArrayList实现)
     - 迭代器不支持remove操作
   - TreeSet -> ConcurrentSkipListSet
     - 支持自然排序
     - 基于map集合
     - add remove...都是线程安全的 
     - 批量操作（removeAll...）不能保证原子性，需要加锁以保证同一时间内只有一个线程进行批量操作
     - 不能操作null值
  
   - HashMap -> ConcurrentHashMap
     - 对读操作做了优化，具有特别高的并发性
   - TreeMap -> ConcurrentSkipListMap
     - 内部通过skipList跳表的形式实现
     - key是有序的
     - 存取时间与线程数无关
2. AQS
   
   1. df：AQS是AbstractQueuedSynchronizer的简称，AQS提供了一种实现阻塞锁和一系列依赖FIFO等待队列的同步器的框架 
        ![在这里插入图片描述](https://imgconvert.csdnimg.cn/aHR0cHM6Ly91cGxvYWQtaW1hZ2VzLmppYW5zaHUuaW8vdXBsb2FkX2ltYWdlcy81MzcyNy1hZTM2ZGI1ODI0MWMyNTZiLnBuZw?x-oss-process=image/format,png)
    
   2. 使用Node实现FIFO队列，可以用于构建锁或者其他同步装置的基础框架
    
   3. 利用了一个int类型表示状态
    
   4. 使用方法是继承（子类通过继承并通过实现它的方法管理其状态{acquire和release}）
    
   5. 可以同时实现排它锁和共享锁模式（独占、共享）
   
3. AQS同步组件
   - CountDownLatch 通过一个B树来控制线程是否需要一直阻塞
     ![](../pic/CountDownLatch.png)
     
     [CountDownLatch使用场景及分析](https://blog.csdn.net/liyuguanguan/article/details/85621359)
     
   - Semaphore（信号量） 能控制同意时间线程的并发数目
     
     - 常用于有限资源的访问
   - CyclicBarrier: 跟CountDownLatch相似（通过计数器实现）
     ![](../pic/CyckucBarrier.png)
     - 允许一组线程相互等待，直到达到某个通过的屏障点，只有所有进程都到达后，才能进行下面的操作 
     - 使用场景：可以用于多线程计算数据，最后合并计算结果
   - ReentrantLock与锁 
     - ReentrantLock(可重入锁)和synchronized区别
       
        - 可重入性
            
            ReenTrantLock的字面意思就是再进入的锁，其实synchronized关键字所使用的锁也是可重入的，两者关于这个的区别不大。两者都是同一个线程没进入一次，锁的计数器都自增1，所以要等到锁的计数器下降为0时才能释放锁。
        - 锁的实现：
            
            Synchronized是依赖于JVM实现的，而ReenTrantLock是JDK实现的。
        - 性能的区别
        
            Synchronized优化之后跟ReenTrantLock差不多，官方推荐使用 Synchronized（写法容易）
        - 功能区别
            
           - 便利性：Synchronized的使用比较方便简洁，并且由编译器去保证锁的加锁和释放，而ReenTrantLock需要手工声明来加锁和释放锁，为了避免忘记手工释放锁造成死锁，所以最好在finally中声明释放锁。
           
           - 锁的细粒度和灵活度：很明显ReenTrantLock优于Synchronized 
        - ReentrantLock独有的功能
           
           - 可指定是公平锁还是非公平锁
           
           - 提供了一个Condition类。可以分组唤醒需要唤醒的线程
           - 提供能够中断等待锁的线程的机制，lock.lockInterruptibly(){使线程避免进入内核态};     
   - Condition
   - FutureTask
     [FutureTask、Runnable、CallAble、Future](https://blog.csdn.net/sinat_39634657/article/details/81456810)
       - df：一个可取消的异步计算FutureTask,提供了对Future的基本实现，可以调用方法去开始和取消一个计算，可以查询计算是否完成并且获取计算结果。只有当计算完成时才能获取到计算结果，一旦计算完成，计算将不能被重启或者被取消，除非调用runAndReset方法。
       - **Runnable**: 只有一个run()函数，用于将耗时操作写在其中，该函数**没有返回值**。然后某个线程去执行该runnable即可实现多线程，Thread类在调用start()函数后执行的就是Runnable的run()函数
       ```java
        public interface Runnable{
         /**
          *  java.lang.Thread run() 
          */
         public abstract void run();
        }
       ```
       - **CallAble:** Callable中有一个call()函数，但是call()函数有返回值，而Runnable的run()函数不能将结果返回给客户程序.
      
       ```java
        // 泛型接口
        public interface Callable<V> {
             /**
              * 返回一个结果。或者在未执行时抛出一个异常
              *
              * @return 返回客户程序传递进来的V类型
              * @throws Exception if unable to compute a result
              */
              V call() throws Exception;
        }
        ``` 
       
      - **Future:** Future设计模式的核心思想是异步调用。其理念应该跟ajax差不多，就是希望在等一个耗时比较长的操作的期间，做其他操作，然后其他操作做完了，回来再处理这个耗时长的操作的结果。这样业务完成的效率高一些。
                    
          ![Future流程图](https://img-blog.csdn.net/20180913105940244?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L291eXVud2Vu/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
          
          main方法一开始就请求了数据，但是这时FutureClient返回的是一个FutureData对象。简单解释下：FutureData跟RealData都是Data接口的实现类，因此都有相同的方法，只是FutureData里面不包含真正的数据，等RealData来填充。main方法深知现在拿到的Data对象其实是不包含具体的数据的，而且这个具体的数据要等一段时间才有。因此main方法继续去做其他事情。而与此同时，futureClient默默地开了一个线程，去获取真正的Data对象，并且填充到FutureData对象里。 
          main方法完成其他事情了，回头过来再取上面的FutureData数据时，其实这个时候FutureData已经填充好了真正的Data数据了，FutureData就直接调RealData的相应的方法，把结果直接转发回给main方法。 
          这样，通过这个设计，main方法在等待数据的同时，也把其他事情先做完了。这样，main方法完成任务的时间将会缩短一些。
          
          ```java
            public interface Future<V> {
             
                /**
                 * 取消此任务的执行。如果任务已经完成，已经取消，或者由于其他原因无法取消，则此尝试将失败。
                 * 如果成功，并且在调用cancel时此任务尚未启动，则此任务不应运行。
                 * 如果任务已经启动，那么mayInterruptIfRunning参数确定执行此任务的线程是否应该被中断，以尝试停止该任务。
                 */
                boolean cancel(boolean mayInterruptIfRunning);
             
                /**
                 * 如果此任务在正常完成之前被取消，则返回true。
                 */
                boolean isCancelled();
             
                /**
                 * 如果任务完成，返回true。
                 */
                boolean isDone();
             
                /**
                 * 必要时等待计算完成，然后检索其结果。
                 *
                 * @return the computed result
                 */
                V get() throws InterruptedException, ExecutionException;
             
                /**
                 * 如果需要，将等待最多给定的时间以完成计算，然后检索其结果(如果可用)。
                 *
                 * @param timeout the maximum time to wait
                 * @param unit the time unit of the timeout argument
                 * @return the computed result
                 */
                V get(long timeout, TimeUnit unit)
                    throws InterruptedException, ExecutionException, TimeoutException;
            }
            
          ```
          FutureTask实现Runnable，所以能通过Thread包装执行，
          
          FutureTask实现Runnable，所以能通过提交给ExcecuteService来执行
          
          注：ExecuteService：创建线程池实例对象，其中有submit（Runnable）、submit（Callable）方法
          
         [ExecuteService介绍](https://blog.csdn.net/suifeng3051/article/details/49443835)
          
          还可以直接通过get()函数获取执行结果，该函数会阻塞，直到结果返回。
          
          因此FutureTask是Future也是Runnable，又是包装了的Callable( 如果是Runnable最终也会被转换为Callable )。
      
      - **Callable 和 Future接口的区别**    
           1. Callable规定的方法是call()，而Runnable规定的方法是run(). 
            
           2. Callable的任务执行后可返回值，而Runnable的任务是不能返回值的。  
           3. call()方法可抛出异常，而run()方法是不能抛出异常的。 
           4. 运行Callable任务可拿到一个Future对象， Future表示异步计算的结果。 
           5. 它提供了检查计算是否完成的方法，以等待计算的完成，并检索计算的结果。 
           6. 通过Future对象可了解任务执行情况，可取消任务的执行，还可获取任务执行的结果。 
           7. Callable是类似于Runnable的接口，实现Callable接口的类和实现Runnable的类都是可被其它线程执行的任务。
           
   - Fork/Join 框架
     
      [Fork/Join 框架介绍](https://www.cnblogs.com/senlinyang/p/7885964.html)
      
   - BlockingQueue
     
      [BLockingQueue 介绍](https://www.jianshu.com/p/7b2f1fa616c6)
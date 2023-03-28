# 三种语言的简单生产者消费者模型的实现
在研究`Kettle`时, `Kettle`的实现原理让我眼前一亮:
- 在ETL流程图中, 所有`Step`同时开始（而不是我认为的一个接一个）
- 每个`Step`执行一样的操作：先读取上游`Hop`是否有RowSet, 如果有，则读取并转换，转换的结果写到下游的`Hop`
- `Hop`有容量概念，比如一个`Hop`如果容量是1000，那么当容量为0时，下游`Step`取数时就会发生阻塞。同理，当容量为1000时，上游`Hop`取数时会发生阻塞

那么，问题是：`Hop`是怎么做到阻塞的呢？\
答案就是`Hop`中维护了一个`BlockingQueue`, 恰好实现了上面的需求。\

于是看了java的`BlockingQueue`, 顺带比较了一把3种语言的`BlockingQueue`实现 \

## java版
`Kettle`是用的 java版实现，采用LinkedBlockingQueue\
基于线程进行阻塞，明显效率低\
run: `java BlockingQueueExample.java`

## rust版
`rust`是基于通道(channel)实现，虽然示例中采用了thread,但明显没有必要，因为关键数据结构`sync_channel`并不基于thread\
`rust`可以提供更好的性能\
run: `rustc blocking_queue_example.rs`

## go版
`go`是基于go channel, 与rust在同一水准。\
不过直观感觉, go在写这种程序时，明显更易用

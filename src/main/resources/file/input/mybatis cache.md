https://blog.csdn.net/u012373815/article/details/47069223


mybatis提供查询缓存，用于减轻数据压力，提高数据库性能。
mybaits提供一级缓存，和二级缓存。

一级缓存是SqlSession级别的缓存。在操作数据库时需要构造 sqlSession对象，
在对象中有一个(内存区域)数据结构（HashMap）用于存储缓存数据。
不同的sqlSession之间的缓存数据区域（HashMap）是互相不影响的。

一级缓存的作用域是同一个SqlSession，在同一个sqlSession中两次执行相同的sql语句，
第一次执行完毕会将数据库中查询的数据写到缓存（内存），第二次会从缓存中获取数据将不再从数据库查询，
从而提高查询效率。当一个sqlSession结束后该sqlSession中的一级缓存也就不存在了。Mybatis默认开启一级缓存。

一级缓存只是相对于同一个SqlSession而言。所以在参数和SQL完全一样的情况下，
我们使用同一个SqlSession对象调用一个Mapper方法，往往只执行一次SQL，
因为使用SelSession第一次查询后，MyBatis会将其放在缓存中，以后再查询的时候，
如果没有声明需要刷新，并且缓存没有超时的情况下，SqlSession都会取出当前缓存的数据，而不会再次发送SQL到数据库。


1、一级缓存的生命周期有多长？
　　a、MyBatis在开启一个数据库会话时，会 创建一个新的SqlSession对象，SqlSession对象中会有一个新的Executor对象。
       Executor对象中持有一个新的PerpetualCache对象；当会话结束时，SqlSession对象及其内部的Executor对象还有PerpetualCache对象也一并释放掉。
　　b、如果SqlSession调用了close()方法，会释放掉一级缓存PerpetualCache对象，一级缓存将不可用。
　　c、如果SqlSession调用了clearCache()，会清空PerpetualCache对象中的数据，但是该对象仍可使用。
　　d、SqlSession中执行了任何一个update操作(update()、delete()、insert()) ，都会清空PerpetualCache对象的数据，但是该对象可以继续使用

 2、怎么判断某两次查询是完全相同的查询？
　　mybatis认为，对于两次查询，如果以下条件都完全一样，那么就认为它们是完全相同的两次查询。

　　2.1 传入的statementId
　　2.2 查询时要求的结果集中的结果范围
　　2.3. 这次查询所产生的最终要传递给JDBC java.sql.Preparedstatement的Sql语句字符串（boundSql.getSql() ）
　　2.4 传递给java.sql.Statement要设置的参数值



二级缓存
MyBatis的二级缓存是Application级别的缓存，它可以提高对数据库查询的效率，以提高应用的性能。 
MyBatis的缓存机制整体设计以及二级缓存的工作模式
![](img/001.png)

二级缓存是mapper级别的缓存，多个SqlSession去操作同一个Mapper的sql语句，
多个SqlSession去操作数据库得到数据会存在二级缓存区域，
多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession的。

二级缓存是多个SqlSession共享的，其作用域是mapper的同一个namespace，
不同的sqlSession两次执行相同namespace下的sql语句且向sql中传递参数也相同即最终执行相同的sql语句，
第一次执行完毕会将数据库中查询的数据写到缓存（内存），第二次会从缓存中获取数据将不再从数据库查询，
从而提高查询效率。Mybatis默认没有开启二级缓存需要在setting全局参数中配置开启二级缓存。

sqlSessionFactory层面上的二级缓存默认是不开启的，二级缓存的开启需要进行配置，
实现二级缓存的时候，MyBatis要求返回的POJO必须是可序列化的。 
也就是要求实现Serializable接口，配置方法很简单，只需要在映射XML文件配置就可以开启缓存了<cache/>，如果我们配置了二级缓存就意味着：

映射语句文件中的所有select语句将会被缓存。
映射语句文件中的所欲insert、update和delete语句会刷新缓存。
缓存会使用默认的Least Recently Used（LRU，最近最少使用的）算法来收回。
根据时间表，比如No Flush Interval,（CNFI没有刷新间隔），缓存不会以任何时间顺序来刷新。
缓存会存储列表集合或对象(无论查询方法返回什么)的1024个引用
缓存会被视为是read/write(可读/可写)的缓存，意味着对象检索不是共享的，而且可以安全的被调用者修改，不干扰其他调用者或线程所做的潜在修改。
如果缓存中有数据就不用从数据库中获取，大大提高系统性能。
package com.aaa.sun.redis;

import com.aaa.sun.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static com.aaa.sun.staticproperties.RedisProperties.*;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/10 15:33
 * @Description
 *          jedisCluster.set("","");当中的类型是String
 *          Object--->String  转换
 */
@Service
public class RedisService<T> {

    private RedisSerializer keySerializer = null;


    /**
     * @Author sun
     * @Description
     *      初始化redis的序列化器
     * @Date 19:27 2020/7/10
     * @Param []
     * @return void
     */
    @PostConstruct
    public void initRedisSerializer(){
        if (this.keySerializer == null){
            this.keySerializer = new JdkSerializationRedisSerializer(this.getClass().getClassLoader());
        }
    }
    @Autowired
    private JedisCluster jedisCluster;
    
    /**
     * @Author sun
     * @Description
     *      向redis中存入数据
     *      nxxx:
     *          固定值
     *          "nx":如果redis中的key不存在，则就可以存储，如果redis中已经有这个key，则不存储数据
     *
     *          "xx":如果redis中的key存在，则直接覆盖，如果key不存在则不存
     *
     *          eg:
     *          研发组有两个人，一个是张三，一个是李四
     *                张三负责商品管理的代码编写
     *                李四负责订单管理
     *                张三和李四因为数据量过大都会使用到redis
     *                张三---->redis.set("goods", 商品信息);
     *                李四---->redis.set("goods", 订单信息);
     *                张三---->redis.get("goods")--->订单信息---->转换异常
     *
     *                张三负责商品管理的代码编写
     *                    张三---->redis.set("goods", 商品信息);
     *                    张三---->redis.set("goods", 商品信息);
     *
     *                 expx:
     *
     *                 "ex": 失效时间，单位：秒
     *                 "px": 失效时间，单位：毫秒
     *
     *                 seconds： 失效时间
     *
     * @Date 15:38 2020/7/10
     * @Param [key, value, nxxx, expx, seconds]
     * @return java.lang.String
     */
    public String set(String key , T value,String nxxx, String expx, Integer seconds){
        if (null != seconds && 0 < seconds &&
                (EX.equals(expx) || PX.equals(expx)) &&
                (NX.equals(nxxx) || XX.equals(nxxx))) {
            // 说明在存入数据的时候必须要上失效时间
            return jedisCluster.set(key, JSONUtils.toJsonString(value),nxxx, expx, seconds);
        }else {
            // 说明不需要设置失效时间
            // 但是仍然需要进一步去判断用户所传递的是nx还是xx
            if (NX.equals(nxxx)) {
                return String.valueOf(jedisCluster.setnx(key, JSONUtils.toJsonString(value)));
            }else if (XX.equals(nxxx)) {
                return jedisCluster.set(key, JSONUtils.toJsonString(value));
            }
        }
        return NO;
    }

    /**
     * @Author sun
     * @Description
     *         从redis中查询数据（当个数据）
     * @Date 16:45 2020/7/10
     * @Param [key]
     * @return T
     */
    public T getOne(String key){
        return (T) JSONUtils.toObject(jedisCluster.get(key), Object.class);
    }


    /**
     * @Author sun
     * @Description
     *          从redis中查询数据（value值是字符串）
     * @Date 16:47 2020/7/10
     * @Param [key]
     * @return java.lang.String
     */
    public String getString(String key){
        return jedisCluster.get(key);
    }


    /**
     * @Author sun
     * @Description
     *      从redis中查询数据（集合数据）
     * @Date 16:48 2020/7/10
     * @Param [key]
     * @return java.util.List<T>
     */
    public List<T> getList(String key){
        return (List<T>) JSONUtils.toList(jedisCluster.get(key),Object.class);
    }


    public Long delOne(Object key){
        /**
         * 思路：
         *      目前架构遇到问题
         *          封装redis的时候发现无法实现通用，因为JedisCluster只能接受String类型key值
         *          并不符合架构的标准，最终可以把object对象转换为字节数组来进行处理这个问题
         */

        return jedisCluster.del(rawKey(key));
    }

    public Long delMany(Collection<T> keys){
        /**
         *
         */
//       Long result = 0L;
//        for (int i = 0; i < keys.size(); i++){
//            T t = keys.iterator().next();
//            result = jedisCluster.del(rawKey(t));
//        }
        if (CollectionUtils.isEmpty(keys)){
            return 0L;
        }else {
            byte[][] bytes = this.rawkeys(keys);
            return jedisCluster.del(bytes);
        }
    }

    /**
     * @Author sun
     * @Description
     *      将Object对象转换位字节数组
     * @Date 19:34 2020/7/10
     * @Param [key]
     * @return byte[]
     */
    public byte[] rawKey(Object key){
//        // 1.判断
//        if (key !=null){
//            // 转转
//        }else {
//            return null;
//        }

        /**
         *   断言：
         *      如果key有值则会执行下面的代码
         *      如果key没有，则直接return
         */
        org.springframework.util.Assert.notNull(key,"non null key required");
//        if ( keySerializer == null && key instanceof byte[]){
//            // 直接转换
//           return  (byte[]) key;
//        }else {
//            // 说明条件不满足，需要进行装换
//            return keySerializer.serialize(key);
//        }

        /**
         * 三目表达式
         */
        return this.keySerializer == null && key instanceof byte[] ?
                (byte[]) key : this.keySerializer.serialize(key);
    }


    /**
     * @Author sun
     * @Description
     *      将参数写入一个二维数组
     * @Date 19:51 2020/7/10
     * @Param [keys]
     * @return byte[][]
     */
    private byte[][] rawkeys(Collection<T> keys){
        byte[][] rks = new byte[keys.size()][];
        int i = 0;
        Object key;
        for (Iterator i3 = keys.iterator(); i3.hasNext();rks[i++] = this.rawKey(key)){
            key = i3.next();
        }
        return rks;
    }
}

package com.llb.cxy.mybatis.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: GenericManager
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author LiLuBing
 * @date 2015年4月7日 下午3:26:51
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericManager<T, PK extends Serializable> {

    /**
     * @Author LiLuBing
     * @Description
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     * //TODO
     * @Date 2021-01-12 17:59
     * @Param  * @param id the identifier (primary key) of the object to get
     * @return {@link T} a populated object
     **/
    T get(PK id);

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @Author LiLuBing
     * @Date 2021-01-17 19:56
     * @Param  * @param
     * @return {@link List<T>}
     **/
    List<T> getAll();

    /**
     * @Author LiLuBing
     * @Description Generic method to save an object - handles both update and insert.//TODO
     * @Date 2021-01-12 18:00
     * @Param  * @param object the object to save
     * @return {@link Integer} success - {@code 1} fail - {@code 0}
     * 推荐】防止 NPE，是程序员的基本修养，注意 NPE 产生的场景:
     *      1) 返回类型为基本数据类型，return 包装数据类型的对象时，自动拆箱有可能产生 NPE。
     *      反例:public int f() { return Integer 对象}， 如果为 null，自动解箱抛 NPE。 2) 数据库的查询结果可能为 null。
     *      3) 集合里的元素即使 isNotEmpty，取出的数据元素也可能为 null。
     *      4) 远程调用返回对象时，一律要求进行空指针判断，防止 NPE。
     *      5) 对于 Session 中获取的数据，建议进行 NPE 检查，避免空指针。
     *      6) 级联调用 obj.getA().getB().getC();一连串调用，易产生 NPE。 正例:使用 JDK8 的 Optional 类来防止 NPE 问题。
     **/
    Integer save(T object);

    /**
     * @Author LiLuBing
     * @Description Generic method to update an object - handles both update and insert. //TODO
     * @Date 2021-01-12 18:00
     * @Param  * @param object the String to object
     * @return {@link Integer} success - {@code 1} fail - {@code 0}
     * 推荐】防止 NPE，是程序员的基本修养，注意 NPE 产生的场景:
     *      1) 返回类型为基本数据类型，return 包装数据类型的对象时，自动拆箱有可能产生 NPE。
     *      反例:public int f() { return Integer 对象}， 如果为 null，自动解箱抛 NPE。 2) 数据库的查询结果可能为 null。
     *      3) 集合里的元素即使 isNotEmpty，取出的数据元素也可能为 null。
     *      4) 远程调用返回对象时，一律要求进行空指针判断，防止 NPE。
     *      5) 对于 Session 中获取的数据，建议进行 NPE 检查，避免空指针。
     *      6) 级联调用 obj.getA().getB().getC();一连串调用，易产生 NPE。 正例:使用 JDK8 的 Optional 类来防止 NPE 问题。
     **/
    Integer update(T object);

    /**
     * @Author LiLuBing
     * @Description Generic method to save an object - handles both update and insert.//TODO
     * @Date 2021-01-12 18:01
     * @Param  * @param jsonRow the String to saveByJson
     * @return {@link Integer} success - {@code 1} fail - {@code 0}
     * 推荐】防止 NPE，是程序员的基本修养，注意 NPE 产生的场景:
     *      1) 返回类型为基本数据类型，return 包装数据类型的对象时，自动拆箱有可能产生 NPE。
     *      反例:public int f() { return Integer 对象}， 如果为 null，自动解箱抛 NPE。 2) 数据库的查询结果可能为 null。
     *      3) 集合里的元素即使 isNotEmpty，取出的数据元素也可能为 null。
     *      4) 远程调用返回对象时，一律要求进行空指针判断，防止 NPE。
     *      5) 对于 Session 中获取的数据，建议进行 NPE 检查，避免空指针。
     *      6) 级联调用 obj.getA().getB().getC();一连串调用，易产生 NPE。 正例:使用 JDK8 的 Optional 类来防止 NPE 问题。
     **/
    default Integer saveByJson(String jsonRow) {
        return 0;
    };

    /**
     * @Author LiLuBing
     * @Description Generic method to delete an object based on class and id//TODO
     * @Date 2021-01-12 18:01
     * @Param  * @param id the identifier (primary key) of the object to remove
     * @return {@link Integer} success - {@code 1} fail - {@code 0}
     **/
    Integer delete(PK id);

    /**
     * @Author LiLuBing
     * @Description Generic method to deletes an object based on class and id//TODO
     * @Date 2021-01-12 18:02
     * @Param  * @param ids the identifier (String ids) of the object to remove
     * @return {@link Integer} success - {@code 1} fail - {@code 0}
     * 推荐】防止 NPE，是程序员的基本修养，注意 NPE 产生的场景:
     *      1) 返回类型为基本数据类型，return 包装数据类型的对象时，自动拆箱有可能产生 NPE。
     *      反例:public int f() { return Integer 对象}， 如果为 null，自动解箱抛 NPE。 2) 数据库的查询结果可能为 null。
     *      3) 集合里的元素即使 isNotEmpty，取出的数据元素也可能为 null。
     *      4) 远程调用返回对象时，一律要求进行空指针判断，防止 NPE。
     *      5) 对于 Session 中获取的数据，建议进行 NPE 检查，避免空指针。
     *      6) 级联调用 obj.getA().getB().getC();一连串调用，易产生 NPE。 正例:使用 JDK8 的 Optional 类来防止 NPE 问题。
     **/
    Integer deletes(String ids);

    /**
     * @Author LiLuBing
     * @Description Generic method to searchPaginated According to the conditions of the query//TODO
     * @Date 2021-01-12 18:02
     * @Param  * @param conditions
     * @return {@link PageInfo<T>}
     **/
    PageInfo<T> searchPaginated(String conditions);

    /**
     * 分页查询
     * @Author LiLuBing
     * @Date 2021-01-13 14:26
     * @Param  * @param mapPageInfo 分页信息
     * @param mapConditions
     * @return {@link List<T>}
     **/
    default List<T> findByConditions(@Param("page") Map<String, Object> mapPageInfo,
                                     @Param("params") Map<String, Object> mapConditions) {
        return null;
    }

    /**
     * 分页根据条件查询总数
     * @Author LiLuBing
     * @Date 2021-01-13 14:29
     * @Param  * @param mapConditions
     * @return {@link Integer}
     * 推荐】防止 NPE，是程序员的基本修养，注意 NPE 产生的场景:
     *      1) 返回类型为基本数据类型，return 包装数据类型的对象时，自动拆箱有可能产生 NPE。
     *      反例:public int f() { return Integer 对象}， 如果为 null，自动解箱抛 NPE。 2) 数据库的查询结果可能为 null。
     *      3) 集合里的元素即使 isNotEmpty，取出的数据元素也可能为 null。
     *      4) 远程调用返回对象时，一律要求进行空指针判断，防止 NPE。
     *      5) 对于 Session 中获取的数据，建议进行 NPE 检查，避免空指针。
     *      6) 级联调用 obj.getA().getB().getC();一连串调用，易产生 NPE。 正例:使用 JDK8 的 Optional 类来防止 NPE 问题。
     **/
    default Integer findTotalByConditions(@Param("params") Map<String, Object> mapConditions) {
        return null;
    }
}
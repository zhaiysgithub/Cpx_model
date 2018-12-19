package com.base.shareData

import android.util.SparseArray
import com.alibaba.fastjson.JSONObject
import com.base.shareData.message.ShareData
import com.base.shareData.message.ShareDataDb
import com.base.shareData.user.User

/**
 *
 *Describe:少量得共享数据
 *
 *Created by zhigang wei
 *on 2018/5/23
 *
 *Company :cpx
 */
object ShareSparse {
    /**
     * USER类型
     */
    const val USER_CLS: String = "1"


    /**
     * 存放数据的对象
     */
    private val MUSLIM_DATA: SparseArray<Any> = SparseArray()

    /**
     * 根据上面定义的常量获取对象
     * @param key 关键key
     */
    fun getValueBy(key: String): Any {
        if (null == MUSLIM_DATA.get(key.toInt())) {
            var value = getDbValueBy(key)
            if(value != null){
                putValue(key, value)
            }else{
                return createEmptyValue(key)
            }
        }
        return MUSLIM_DATA.get(key.toInt())
    }

    /**
     * 创建key 创建空的value
     */
    private fun createEmptyValue(key:String):Any{
        return when(key){
            USER_CLS ->{
                User()
            }
            else->{
                Any()
            }
        }

    }


    /**
     * 根据上面定义的常量从数据库获取
     * @param key 关键key
     */
    fun getDbValueBy(key: String): Any? {
        return ShareDataDb.getInstance().queryBy(key)
    }


    /**
     * set全局db变量
     * @param key 关键key
     * @param any 数据源
     */
    fun putDbValue(key: String, any: Any) {
        ShareDataDb.getInstance().savrOrUpdate(ShareData(key, JSONObject.toJSONString(any)))
    }


    /**
     * 是否需要保持数据到本地
     *
     * @param key key
     * @param any 数据源
     * @param saveLocal 是否需要缓冲处理
     */
    fun putValue(key: String, any: Any, saveLocal: Boolean=false) {
        if (saveLocal) {
            putDbValue(key, any)
        }
        MUSLIM_DATA.put(key.toInt(), any)
    }


}
package com.backstage.unils;

import java.util.HashMap;

/**
 * @description: Ajax 返回数据格式
 * @author: wanli3815@163.com
 * @create: 2020-01-13 17:36
 **/
public class AjaxResult extends HashMap<String, Object>
{

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String COUNT_TAG = "count";

    public static final String DATA_TAG = "data";


    /** 状态码 */
    private int code;

    /** 返回内容 */
    private String msg;

    private  Long count;

    /** 数据对象 */
    private Object data;

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态类型
     * @param msg 返回内容
     */
    public AjaxResult(Integer code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态类型
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AjaxResult(Integer code, String msg, Long count, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(COUNT_TAG, count);
        if (data!=null)
        {
            super.put(DATA_TAG, data);
        }
    }
    public AjaxResult(Integer code, String msg,  Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);

        if (data!=null)
        {
            super.put(DATA_TAG, data);
        }
    }

    public static AjaxResult SuccessList(long count,Object data){
        return new AjaxResult(ResultEnum.SUCCESS.getCode(),"操作成功",count,data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success()
    {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg)
    {
        return new AjaxResult(ResultEnum.SUCCESS.getCode(), msg);
    }



    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(ResultEnum.SUCCESS.getCode(), msg, data);
    }


    /**
     * 返回错误消息
     *
     * @return
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(ResultEnum.ERROR.getCode(), msg, data);
    }



    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

}


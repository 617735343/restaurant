package com.imooc.imooc_restaurant.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils {
    private static SPUtils instance;
    private Context mCtx;

    /**
     * 保存在手机里面的文件名
     */
    public String mFileName = "share_data";
    private SPUtils(Context context,String fileName){
        mCtx = context;
        this.mFileName = fileName;
    }

    public static SPUtils init(Context context,String filename){
        if (instance == null){
            synchronized (SPUtils.class){
                if (instance == null){
                    instance = new SPUtils(context,filename);
                }
            }
        }
        return instance;
    }

    public static SPUtils getInstance(){
        if(instance == null){
            throw new IllegalStateException("you should can getInstance(Context context,String filename)");
        }
        return instance;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据不同的类型调用不同的保存方法
     */
    public void put(String key,Object object){
        SharedPreferences sp = mCtx.getSharedPreferences(mFileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if(object instanceof String){
            editor.putString(key,(String) object);
        }else if(object instanceof Integer){
            editor.putInt(key,(Integer) object);
        }else if(object instanceof Boolean){
            editor.putBoolean(key,(Boolean) object);
        }else if(object instanceof Float){
            editor.putFloat(key,(Float) object);
        }else if(object instanceof Long){
            editor.putLong(key,(Long) object);
        }else {
            editor.putString(key,object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public Object get(String key,Object defaultObject){
        SharedPreferences sp = mCtx.getSharedPreferences(mFileName,Context.MODE_PRIVATE);
        if (defaultObject instanceof String){
            return sp.getString(key,(String) defaultObject);
        }else if(defaultObject instanceof Integer){
            return sp.getInt(key,(Integer) defaultObject);
        }else if(defaultObject instanceof Boolean){
            return sp.getBoolean(key,(Boolean) defaultObject);
        }else if(defaultObject instanceof Float){
            return sp.getFloat(key,(Float) defaultObject);
        }else if (defaultObject instanceof Long){
            return sp.getLong(key,(Long) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key){
        SharedPreferences sp = mCtx.getSharedPreferences(mFileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public void clear(){
        SharedPreferences sp = mCtx.getSharedPreferences(mFileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public boolean contains(String key){
        SharedPreferences sp = mCtx.getSharedPreferences(mFileName,Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String,?> getAll(){
        SharedPreferences sp = mCtx.getSharedPreferences(mFileName,Context.MODE_PRIVATE);
        return sp.getAll();
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat{
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         * @return
         */
        private static Method findApplyMethod() {
            Class clz = SharedPreferences.Editor.class;
            try {
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor){
            if (sApplyMethod != null){
                try {
                    sApplyMethod.invoke(editor);
                    return;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                editor.commit();
            }
        }
    }
}

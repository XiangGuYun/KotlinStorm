package com.kotlindemo.base.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream






interface BmpUtils {

    /**
     * Bitmap转二进制
     */
    fun Bitmap.toBytes(isPng:Boolean=true,quality:Int=100):ByteArray{
        val o = ByteArrayOutputStream()
        if(isPng)
            this.compress(Bitmap.CompressFormat.PNG, quality, o)
        else
            this.compress(Bitmap.CompressFormat.JPEG, quality, o)
        return o.toByteArray()
    }

    /**
     * 二进制转Bitmap
     */
    fun ByteArray.toBmp():Bitmap{
        return BitmapFactory.decodeByteArray(this, 0, this.size)
    }

    /**
     * Drawable转Bitmap
     */
    fun Drawable.toBmp():Bitmap{
        return (this as BitmapDrawable).bitmap
    }

    /**
     * Bitmap转Drawable
     */
    fun Bitmap.toDrawable():Drawable{
        return BitmapDrawable(this)
    }

    /**
     * 保存Bitmap到本地
     */
    fun Bitmap.save(path:String=
                            Environment.getExternalStorageDirectory().toString(),
                    name:String="default.jpg",
                    isJpeg:Boolean=true,
                    quality:Int=100){
        var file = File("$path/$name")
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }
        val fos = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        fos.close()
    }

    /**
     * 从资源文件中解析Bitmap
     */
    fun Context.bmpFromRes(id:Int, options:BitmapFactory.Options=BitmapFactory.Options()):Bitmap{
        return BitmapFactory.decodeResource(this.resources, id,options)
    }


    fun Context.bmpFromRes(path:String, options:BitmapFactory.Options=BitmapFactory.Options()):Bitmap{
        return BitmapFactory.decodeFile(path,options)
    }

}
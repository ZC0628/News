package com.zc.news.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

/**
 * 图片的缓存
 * @author Administrator
 *
 */
public class ImageLoaderUtil {
	// 将图片的路径作为键
	private LruCache<String, Bitmap> cache;// 访问--最快速有效,不浪费性能的访问方式
	private ImageLoadListener mImageLoadListener;
	private Context context;
	private Bitmap bitmap;
	private String name;
	
	// 图片的临时存储区域
		/*
		 * Bitmap(大胖子--OOM--32M),Drawable 强引用/弱引用/虚引用 强引用-垃圾回收器不会回收
		 * 弱引用-当手机内存不够用了,会进行回收SoftReference<Bitmap>
		 * Lrucache缓存--容器,分配大小8M,强引用,会将不常用的图片删除
		 */
	
	public ImageLoaderUtil(ImageLoadListener mImageLoadListener, Context context){
		this.context = context;
		this.mImageLoadListener = mImageLoadListener;
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		//创建出缓存
		cache = new LruCache<String, Bitmap>(maxMemory/4){
			//计算图片的大小
			@Override
			protected int sizeOf(String key, Bitmap value) {
				//用图片每行的字节数*图片的高度
				return value.getHeight() * value.getRowBytes();
			}
		};
	}
	
	public interface ImageLoadListener{
		void imageLoadOK(Bitmap bitmap, String url);
	}
	
	//从缓存下载图片
	public Bitmap getBitmap(String icon){
		
		bitmap = cache.get(icon);
		//先从Lrucache中查找
		if(bitmap != null){
			//如果找到图片则返回这张图片
			return bitmap;
		}
		//如果没有找到继续查找二级缓存,
		//截取图片的名称
		File file = context.getCacheDir();
		if(file != null){
			File[] files = file.listFiles();
			//剪切路径中的文件名
			name = icon.substring(icon.lastIndexOf("/")+1);
			for(int x = 0; x < files.length; x++){
				//判断文件名称和传过来的文件名是否一样
				if(files[x].getName().equals(name)){
					return BitmapFactory.decodeFile(files[x].getPath());
				}
			}
		}
		
		//下载图片
		LogUtil.d("icon", icon);
		MyTask task = new MyTask();
		task.execute(icon);
		return null;
		
	}
	
	/*
	 * 异步任务
	 */
	class MyTask extends AsyncTask<String, Void, Bitmap>{
        private String url;
        	
		@Override
		protected Bitmap doInBackground(String... params){
			url = params[0];
			try {
				Bitmap result = HttpUtil.httpGetBitmap(url);
				
				
				if(result != null){
					
					LogUtil.d("onPostExecute", result+"");
					
					//将图片保存到缓存中
					//先保存到Lrucache中
					cache.put(url, result);
					//再保存到文件中
					name = url.substring(url.lastIndexOf("/")+1);
					File file = new File(context.getCacheDir().getPath()+"/"+name);
					OutputStream os = null;
					try {
						os = new FileOutputStream(file);
						result.compress(CompressFormat.JPEG, 70, os);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						if(os != null){
							try {
								os.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				return result;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		//在执行任务前要做的事
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		//当任务结束后在UI线程中执行
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			//通知主界面下载图片结束并把结果传递过去
			mImageLoadListener.imageLoadOK(result, url);
		}
	}
}

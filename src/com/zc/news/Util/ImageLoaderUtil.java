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
 * ͼƬ�Ļ���
 * @author Administrator
 *
 */
public class ImageLoaderUtil {
	// ��ͼƬ��·����Ϊ��
	private LruCache<String, Bitmap> cache;// ����--�������Ч,���˷����ܵķ��ʷ�ʽ
	private ImageLoadListener mImageLoadListener;
	private Context context;
	private Bitmap bitmap;
	private String name;
	
	// ͼƬ����ʱ�洢����
		/*
		 * Bitmap(������--OOM--32M),Drawable ǿ����/������/������ ǿ����-�����������������
		 * ������-���ֻ��ڴ治������,����л���SoftReference<Bitmap>
		 * Lrucache����--����,�����С8M,ǿ����,�Ὣ�����õ�ͼƬɾ��
		 */
	
	public ImageLoaderUtil(ImageLoadListener mImageLoadListener, Context context){
		this.context = context;
		this.mImageLoadListener = mImageLoadListener;
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		//����������
		cache = new LruCache<String, Bitmap>(maxMemory/4){
			//����ͼƬ�Ĵ�С
			@Override
			protected int sizeOf(String key, Bitmap value) {
				//��ͼƬÿ�е��ֽ���*ͼƬ�ĸ߶�
				return value.getHeight() * value.getRowBytes();
			}
		};
	}
	
	public interface ImageLoadListener{
		void imageLoadOK(Bitmap bitmap, String url);
	}
	
	//�ӻ�������ͼƬ
	public Bitmap getBitmap(String icon){
		
		bitmap = cache.get(icon);
		//�ȴ�Lrucache�в���
		if(bitmap != null){
			//����ҵ�ͼƬ�򷵻�����ͼƬ
			return bitmap;
		}
		//���û���ҵ��������Ҷ�������,
		//��ȡͼƬ������
		File file = context.getCacheDir();
		if(file != null){
			File[] files = file.listFiles();
			//����·���е��ļ���
			name = icon.substring(icon.lastIndexOf("/")+1);
			for(int x = 0; x < files.length; x++){
				//�ж��ļ����ƺʹ��������ļ����Ƿ�һ��
				if(files[x].getName().equals(name)){
					return BitmapFactory.decodeFile(files[x].getPath());
				}
			}
		}
		
		//����ͼƬ
		LogUtil.d("icon", icon);
		MyTask task = new MyTask();
		task.execute(icon);
		return null;
		
	}
	
	/*
	 * �첽����
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
					
					//��ͼƬ���浽������
					//�ȱ��浽Lrucache��
					cache.put(url, result);
					//�ٱ��浽�ļ���
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
		//��ִ������ǰҪ������
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		//�������������UI�߳���ִ��
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			//֪ͨ����������ͼƬ�������ѽ�����ݹ�ȥ
			mImageLoadListener.imageLoadOK(result, url);
		}
	}
}

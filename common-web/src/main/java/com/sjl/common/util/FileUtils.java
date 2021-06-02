package com.sjl.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

/**
 * 文件操作工具类
 * 
 * @author song
 *
 */
public class FileUtils {

	/**
	 * 
	 * 根据指定文件类型获取文件 path不以’/'开头时，默认是从此类所在的包下取资源；path 以’/'开头时，则是从ClassPath根下获取；
	 * 
	 * @param request
	 * @param path
	 * @param fileType
	 * @return
	 */
	public static File getFile(HttpServletRequest request, String path, String fileType) {
		String filePath = request.getSession().getServletContext().getRealPath(path);
		File dir = new File(filePath);
		File[] list = dir.listFiles();
		File apkFile = null;
		for (File file : list) {
			if (file.exists() && file.isFile() && file.getName().endsWith(fileType)) {
				apkFile = file;
				break;
			}
		}
		return apkFile;
	}

	/**
	 * 删除指定路径下的文件目录及文件
	 * 
	 * @param path 文件路径
	 * @param selfFlag true删除本身目录及下面所有目录及文件，false本身目录及下面所有目录的文件
	 */
	public static void deleteFiles(String path, boolean selfFlag) {
		File file = new File(path);
		// 1级文件刪除
		if (!file.isDirectory()) {
			file.delete();
		} else if (file.isDirectory()) {
			// 2级文件列表
			String[] filelist = file.list();
			for (int j = 0; j < filelist.length; j++) {
				File filessFile = new File(path + "\\" + filelist[j]);
				if (!filessFile.isDirectory()) {
					filessFile.delete();
				} else if (filessFile.isDirectory()) {
					// 递归删除文件
					deleteFiles(path + File.separator + filelist[j], selfFlag);
				}
			}
			if (selfFlag) {
				file.delete();
			}

		}
	}

	/**
	 * 使用NIO进行快速的文件拷贝
	 * 
	 * @param source 源文件
	 * @param target 目标文件
	 * @throws IOException
	 */
	public static void fileCopy(File source, File target) throws IOException {
		FileChannel inChannel = new FileInputStream(source).getChannel();
		FileChannel outChannel = new FileOutputStream(target).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
			// original -- apparently has trouble copying large files on Windows
			// magic number for Windows, 64Mb - 32Kb)
			int maxCount = (64 * 1024 * 1024) - (32 * 1024);
			long size = inChannel.size();
			long position = 0;
			while (position < size) {
				position += inChannel.transferTo(position, maxCount, outChannel);
			}
			System.out.println("file copy success!");
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
	}
	/**
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static int fileCopy(InputStream input, OutputStream output) throws IOException {
		return IOUtils.copy(input, output);
	}
}

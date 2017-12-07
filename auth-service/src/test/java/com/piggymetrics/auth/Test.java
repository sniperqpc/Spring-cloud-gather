package com.piggymetrics.auth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Test {
	//解压gzip文件
	public static boolean extractZip(File file, File parent) {
		ZipFile zf = null;
		try {
			zf = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zf.entries();
			if (entries == null)
				return false;

			final byte[] buf = new byte[256];

			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				if (entry == null)
					continue;

				if (entry.isDirectory()) {
					File dir = new File(parent, entry.getName());
					dir.mkdirs();
					continue;
				}

				File dstFile = new File(parent, entry.getName());
				if (!dstFile.exists()) {
					dstFile.getParentFile().mkdirs();
				}

				InputStream fis = zf.getInputStream(entry);
				BufferedInputStream bis = new BufferedInputStream(fis);
				FileOutputStream fos = new FileOutputStream(dstFile);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				int read = 0;
				while ((read = bis.read(buf)) > 0) {
					bos.write(buf, 0, read);
				}
				fis.close();
				bis.close();
				bos.close();
				fos.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				zf.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对将单个文件进行压缩
	 * 
	 * @param source
	 *            源文件
	 * @param target
	 *            目标文件
	 * @throws IOException
	 */
	public static void zipFile(String source, String target) throws IOException {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		GZIPOutputStream gzout = null;
		try {
			fin = new FileInputStream(source);
			fout = new FileOutputStream(target);
			gzout = new GZIPOutputStream(fout);
			byte[] buf = new byte[1024];
			int num;
			while ((num = fin.read(buf)) != -1) {
				gzout.write(buf, 0, num);
			}
		} finally {
			if (gzout != null)
				gzout.close();
			if (fout != null)
				fout.close();
			if (fin != null)
				fin.close();
		}
	}

	//多个文件压缩成gzip文件
	public static void mutileFileToGzip(ArrayList<String> filePaths, String targetFileName) {
		try {
			File file = new File(targetFileName);
			FileOutputStream fout = new FileOutputStream(file);
			BufferedInputStream bin = null;
			ZipOutputStream zout = new ZipOutputStream(fout);
			for (String fileSource : filePaths) {
				String[] fileNames = fileSource.split("/");
				zout.putNextEntry(new ZipEntry(fileNames[fileNames.length - 1]));
				int c;
				bin = new BufferedInputStream(new FileInputStream(fileSource));
				while ((c = bin.read()) != -1) {
					zout.write(c);
				}
				bin.close();
			}
			zout.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("压缩成功！");

	}
}

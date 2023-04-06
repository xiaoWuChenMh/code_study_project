package com.future.small_toolbox.damo.cankao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import study.damo.Features.*;
import study.damo.exception.DMException;
import study.damo.util.Utils;

/**
 * 大漠插件对象,免费版
 * @version v3.1233
 * @author 陈均
 *
 */
public class DmSoft {
	private static final String DM_DLL = "dm.dll";

	private ActiveXComponent dm;

	public AlgorithmOper algorithmOper;
	public BackstageOper backstageOper;
	public BaseSetOper baseSetOper;
	public CompileOper compileOper;
	public FileOper fileOper;
	public MemoryOPer memoryOPer;
	public MouseOper mouseOper;
	public PictureColorOper pictureColorOper;
	public SystemOper systemOper;
	public TextOper textOper;
	public WindowOper windowOper;


	public DmSoft(){
		try {
			loadDmInfo();
			initDmOpers();
		} catch (Exception e) {
			throw new DMException(e);
		}

	}

	/**
	 * 加载大漠插件信息
	 * @throws Exception
	 */
	public void loadDmInfo() throws Exception{
		System.out.println("开始初始化大漠插件");
		if(!Utils.isWindows() || Utils.is64Bit()){
			throw new DMException("大漠插件只能在window平台使用.jdk必须是32位的.");
		}
		if(!checkDmDll()){
			File tempFolder = Utils.getTempFolderWritable();
			extractResourceToFolder(DmSoft.class.getClassLoader(),tempFolder);
			regDmDll(tempFolder);
		};
	}

	/*
	* @Description: 初始化大漠的操作组件
	*/
	public void initDmOpers(){
		dm = new ActiveXComponent("dm.dmsoft");
		algorithmOper = new AlgorithmOper(dm);
		backstageOper = new BackstageOper(dm);
		baseSetOper = new BaseSetOper(dm);
		compileOper = new CompileOper(dm);
		fileOper  = new FileOper(dm);
		memoryOPer = new MemoryOPer(dm);
		mouseOper = new MouseOper(dm);
		pictureColorOper = new PictureColorOper(dm);
		systemOper = new SystemOper(dm);
		textOper = new TextOper(dm);
		windowOper = new WindowOper(dm);
		System.out.println("大漠中的各个操作组件（算法、窗口、鼠标等）初始化成功");
	}

	/**
	 * 注册大漠插件
	 * @param targetFolder 文件目录
	 */
	public void regDmDll(File targetFolder){
		File targetFile = new File(targetFolder, DM_DLL);
		if(!targetFile.exists()){
			System.out.println(targetFile + "没有找到,注册失败");
		}else{
			String cmd = "regsvr32 "+ targetFile;
			Process p = null;
			try {
				p = Runtime.getRuntime().exec(cmd);
				new ProcessInputStream(p.getErrorStream()).start();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						p.getInputStream(), "GBK"));
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line != null) {
						System.out.println(line);
					}
				}
				System.out.println("成功注册dm.dll...");
			} catch (Exception e) {
				System.err.println("exec " + cmd + " failure：" + e.getMessage());
			} finally {
				if (p != null) {
					try {
						p.waitFor();
					} catch (Exception e) {
						System.err.println("exec Process waitFor failure："
								+ e.getMessage());
					} finally {
						p.destroy();
					}
				}
			}
		}
	}

	/**
	 * 释放资源dm.dll到指定文件夹中.
	 */
	public void extractResourceToFolder(ClassLoader classLoader,File targetFolder){
        targetFolder.mkdirs();
        if(! targetFolder.exists()) {
            throw new DMException("Folder does not exist despite effort of attempting to create it: " + targetFolder.getAbsolutePath());
        }
        URL url = classLoader.getResource(DM_DLL);
        String urlPath = url.toString();
        if(urlPath.startsWith("file:")){//目录资源
        	InputStream inputStream = null;
        	FileOutputStream outputStream = null;
        	try {
        		File targetFile = new File(targetFolder, DM_DLL);
        		outputStream = new FileOutputStream(targetFile);
                byte[] buffer = new byte[1024 * 4];
                int bytesRead = 0;
                inputStream = url.openStream();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
				throw new DMException("释放资源出错.",e);
			} finally {
                if(outputStream!=null){
                	try {
                		outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
                if(inputStream!=null){
                	try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        }else if(urlPath.startsWith("jar:file:")){//jar资源
        	try {
				ZipFile zipFile = new ZipFile(url.getFile());
				zipFile.extractFile(DM_DLL, targetFolder.getAbsolutePath());
			} catch (ZipException e) {
				throw new DMException("释放资源出错.",e);
			}
        }
        System.out.println("成功释放资源dm.dll到:"+targetFolder);
	}

	/**
	 * 检查大漠插件是否存在
	 * @return true存在,false不存在
	 */
	public boolean checkDmDll(){
		ActiveXComponent tempDm = null;
		try {
			tempDm = new ActiveXComponent("dm.dmsoft");
			String ver = Dispatch.call(tempDm, "Ver").getString();
			return "3.1233".equals(ver);
		} catch (Exception e) {
			System.out.println("创建大漠对象失败,插件没有正确获取到...");
			return false;
		}
	}



/////////////////////////////窗口////////////////////////////////////
	public WindowOper getWindow(){
		if(windowOper==null){
			synchronized (this){
				if(windowOper==null){
					windowOper = new WindowOper(dm);
				}
			}
		}
		return windowOper;
	}
/////////////////////////////后台设置////////////////////////////////////
    public BackstageOper getBackstage(){
    	if(backstageOper==null){
    		synchronized (this){
    			if(backstageOper==null){
    				backstageOper = new BackstageOper(dm);
    			}
    		}
    	}
    	return backstageOper;
    }

/////////////////////////////汇编////////////////////////////////////
    public CompileOper getCompile(){
    	if(compileOper==null){
    		synchronized (this){
    			if(compileOper==null){
					compileOper = new CompileOper(dm);
    			}
    		}
    	}
    	return compileOper;
    }

/////////////////////////////基本设置////////////////////////////////////



/////////////////////////////键鼠////////////////////////////////////

/////////////////////////////键鼠////////////////////////////////////

/////////////////////////////内存////////////////////////////////////

/////////////////////////////算法////////////////////////////////////


/////////////////////////////图色////////////////////////////////////


/////////////////////////////文件////////////////////////////////////



/////////////////////////////文字识别////////////////////////////////////



/////////////////////////////系统////////////////////////////////////

}

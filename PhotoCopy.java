package tools;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFileChooser;

public class PhotoCopy {
	private static long lpic = 0;
	private static long lvedio = 0;
	//打开对话框并返回选中的对话框径
    public static String path() {
    	int iselect = -1;
    	String strpath = "";
    	JFileChooser jfilechooser = new JFileChooser();
    	jfilechooser.setCurrentDirectory(new File("C:\\"));
    	jfilechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    	try {
    		iselect = jfilechooser.showOpenDialog(null);
    	}
    	catch (HeadlessException head) {
    		System.out.println("文件选择对话框无法打开");
    	}

    	if (iselect == JFileChooser.APPROVE_OPTION) {
    		strpath = jfilechooser.getSelectedFile().getAbsolutePath();
    	}
    	else {
    		System.out.println("操作取消");
    	}
    	return strpath;
    }

    //读取一个文件夹下所有文件及子文件夹下的所有文件
	public static void ReadAllFile(String filePath, List<File> list) {
		File f = null;
        f = new File(filePath);
        File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。  
        for (File file : files) {
            if(file.isDirectory()) {
                //如何当前路劲是文件夹，则循环读取这个文件夹下的所有文件 
                ReadAllFile(file.getAbsolutePath(), list);
            } else {
            	if (file.isFile()) {
                    list.add(file);
            	}
            }
        }
    }
//
//    //读取一个文件夹下的所有文件夹和文件 
//    public void ReadFile(String filePath) {
//        File f = null;
//        f = new File(filePath);
//        File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。 
//        List<File> list = new ArrayList<File>();
//        for (File file : files) {
//            list.add(file);
//       }
//    }
    //整理文件
    /*
     * listFile 文件列表
     * strPathOT 输出文件路径
     */
    public static void Zhengli(List<File> listFile, String strPathOT) {
		System.out.println("整理文件开始");
    	//初期化
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Calendar cal = Calendar.getInstance();
		//获取文件
		int ilength = listFile.size();
		for(int i = 0; i < ilength; i++) {
			File floop = listFile.get(i);
			if (floop.exists() && floop.isFile()) {
				String strfilename = floop.getName();
				int ifilelength1 = strfilename.length();
				int ifilelength2 = strfilename.lastIndexOf(".");
				//获取文件修改时间
				long ltime = floop.lastModified();

				if (ifilelength2 > 0 && ifilelength1 > ifilelength2) {
					//获取文件扩展名以及修改时间
					String str1 = strfilename.substring(ifilelength2 + 1, ifilelength1);
					cal.setTimeInMillis(ltime);
					String strlasttime = sdf.format(cal.getTime());
					Filecopy(floop, strfilename, str1, strlasttime, strPathOT);
				}
			}
		}
		System.out.println("整理文件结束");
    }
    //文件拷贝
    /* 
     * fFileIN   输入文件
     * strFileName 文件名
     * strFileExtName 文件扩展名
     * strFileTime 文件修改时间
     * strPathOT   输出文件路径
     */
    public static void Filecopy(File fFileIN, String strFileName, String strFileExtName,
    		String strFileTime, String strPathOT) {
    	//获取文件相关信息
    	String strM = strFileTime.substring(4, 6);
    	//文件扩展名变换
    	String strFextUP = strFileExtName.toUpperCase();
    	//输出文件类型初期化
    	String strType = "";
    	String strYear = strFileTime.substring(0, 4);
    	String strMonth = "";
		//通过扩展名将文件分类
		if (null == strFileName || "".equals(strFileName)
			|| null == strFileExtName || "".equals(strFileExtName)
			|| null == strFileTime || "".equals(strFileTime)) {
			System.out.println("输入的文件信息有错误，请重新输入！");
			return;
		}
		if ("JPG".equals(strFextUP) || "PNG".equals(strFextUP) || "CR2".equals(strFextUP)
			|| "BMP".equals(strFextUP) || "PCX".equals(strFextUP) || "TIFF".equals(strFextUP)
			|| "GIF".equals(strFextUP) || "JPEG".equals(strFextUP) || "TGA".equals(strFextUP)
			|| "EXIF".equals(strFextUP) || "FPX".equals(strFextUP) || "SVG".equals(strFextUP)
			|| "PSD".equals(strFextUP) || "CDR".equals(strFextUP) || "PCD".equals(strFextUP)
			|| "DXF".equals(strFextUP) || "UFO".equals(strFextUP) || "EPS".equals(strFextUP)
			|| "AI".equals(strFextUP) || "HDRI".equals(strFextUP) || "RAW".equals(strFextUP)) {
			strType = "00照片";
			lpic++;
		}
		else if ("MPEG".equals(strFextUP) || "AVI".equals(strFextUP) || "MOV".equals(strFextUP)
				|| "ASF".equals(strFextUP) || "WMV".equals(strFextUP) || "NAVI".equals(strFextUP)
				|| "3GP".equals(strFextUP) || "REAL VIDEO".equals(strFextUP)
				|| "MKV".equals(strFextUP) || "FLV".equals(strFextUP) || "F4V".equals(strFextUP)
				|| "RMVB".equals(strFextUP) || "WebM".equals(strFextUP)) {
			strType = "00视频";
			lvedio++;
		}
		else {
			System.out.println("输入的文件格式有错误，请重新输入！");
			return;
		}

		//月份编辑
		switch(strM) {
		    case "01":
		    	strMonth = "一月";
		    	break;
		    case "02":
		    	strMonth = "二月";
		    	break;
		    case "03":
		    	strMonth = "三月";
		    	break;
		    case "04":
		    	strMonth = "四月";
		    	break;
		    case "05":
		    	strMonth = "五月";
		    	break;
		    case "06":
		    	strMonth = "六月";
		    	break;
		    case "07":
		    	strMonth = "七月";
		    	break;
		    case "08":
		    	strMonth = "八月";
		    	break;
		    case "09":
		    	strMonth = "九月";
		    	break;
		    case "10":
		    	strMonth = "十月";
		    	break;
		    case "11":
		    	strMonth = "十一月";
		    	break;
		    case "12":
		    	strMonth = "十二月";
		    	break;
	    	default:
		    	strMonth = "异常";
		    	break;
		}
		//输出文件路径编辑
    	String strPath = strPathOT.concat("\\").concat(strType).concat("\\")
    						.concat(strYear).concat("\\").concat(strMonth);
    	File fpathOT = new File(strPath);
		if (!fpathOT.exists()) {
    		fpathOT.mkdirs();
    	}

		//目录存在，判断文件是否存在
    	//重复判断用变量编辑
    	String strFile = strPath.concat("\\").concat(strFileName);
    	String strFileNew =
    			strFileName.substring(0, strFileName.length() - strFileExtName.length() - 1);
    	File fpath = new File(strFile);
    	//文件重复时，改名
    	int i = 1;
		while (fpath.exists()) {
			strFile = strPath.concat("\\").concat(
					strFileNew.concat(String.valueOf(i)).concat(".").concat(strFileExtName));
        	fpath = new File(strFile);
        	i++;
		}
		//文件复制开始
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel finc = null;
		FileChannel fotc = null;
		try {
			fis = new FileInputStream(fFileIN);
			fos = new FileOutputStream(strFile);
			finc = fis.getChannel();
			fotc = fos.getChannel();
			finc.transferTo(0, finc.size(), fotc);
		}
		catch (IOException e) {
            e.printStackTrace();
        }
		finally {
            try {
                fis.close();
                finc.close();
                fos.close();
                fotc.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
	
    }

    //主程序
	public static void main(String[] args) {
		System.out.println("文件处理      开始");
		//取得打开文件框的路径
		String strpath = path();
		List<File> listFile = new ArrayList<File>();
		//获取文件
		if (null != strpath && !"".equals(strpath)) {
			ReadAllFile(strpath, listFile);
		}
		else {
			System.out.println("文件取得失败");
		}
		//整理文件
		if (listFile != null && listFile.size() > 0) {
			System.out.println("输入文件数量==" + listFile.size());
			Zhengli(listFile, "H:\\整理");
			System.out.println("输出照片数量==" + lpic);
			System.out.println("输出视频数量==" + lvedio);
		}
		else {
			System.out.println("输入目录下没有对应的文件");
		}

		System.out.println("文件处理      结束");
	}
}

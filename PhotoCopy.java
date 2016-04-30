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
	//�򿪶Ի��򲢷���ѡ�еĶԻ���
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
    		System.out.println("�ļ�ѡ��Ի����޷���");
    	}

    	if (iselect == JFileChooser.APPROVE_OPTION) {
    		strpath = jfilechooser.getSelectedFile().getAbsolutePath();
    	}
    	else {
    		System.out.println("����ȡ��");
    	}
    	return strpath;
    }

    //��ȡһ���ļ����������ļ������ļ����µ������ļ�
	public static void ReadAllFile(String filePath, List<File> list) {
		File f = null;
        f = new File(filePath);
        File[] files = f.listFiles(); // �õ�f�ļ�������������ļ���  
        for (File file : files) {
            if(file.isDirectory()) {
                //��ε�ǰ·�����ļ��У���ѭ����ȡ����ļ����µ������ļ� 
                ReadAllFile(file.getAbsolutePath(), list);
            } else {
            	if (file.isFile()) {
                    list.add(file);
            	}
            }
        }
    }
//
//    //��ȡһ���ļ����µ������ļ��к��ļ� 
//    public void ReadFile(String filePath) {
//        File f = null;
//        f = new File(filePath);
//        File[] files = f.listFiles(); // �õ�f�ļ�������������ļ��� 
//        List<File> list = new ArrayList<File>();
//        for (File file : files) {
//            list.add(file);
//       }
//    }
    //�����ļ�
    /*
     * listFile �ļ��б�
     * strPathOT ����ļ�·��
     */
    public static void Zhengli(List<File> listFile, String strPathOT) {
		System.out.println("�����ļ���ʼ");
    	//���ڻ�
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Calendar cal = Calendar.getInstance();
		//��ȡ�ļ�
		int ilength = listFile.size();
		for(int i = 0; i < ilength; i++) {
			File floop = listFile.get(i);
			if (floop.exists() && floop.isFile()) {
				String strfilename = floop.getName();
				int ifilelength1 = strfilename.length();
				int ifilelength2 = strfilename.lastIndexOf(".");
				//��ȡ�ļ��޸�ʱ��
				long ltime = floop.lastModified();

				if (ifilelength2 > 0 && ifilelength1 > ifilelength2) {
					//��ȡ�ļ���չ���Լ��޸�ʱ��
					String str1 = strfilename.substring(ifilelength2 + 1, ifilelength1);
					cal.setTimeInMillis(ltime);
					String strlasttime = sdf.format(cal.getTime());
					Filecopy(floop, strfilename, str1, strlasttime, strPathOT);
				}
			}
		}
		System.out.println("�����ļ�����");
    }
    //�ļ�����
    /* 
     * fFileIN   �����ļ�
     * strFileName �ļ���
     * strFileExtName �ļ���չ��
     * strFileTime �ļ��޸�ʱ��
     * strPathOT   ����ļ�·��
     */
    public static void Filecopy(File fFileIN, String strFileName, String strFileExtName,
    		String strFileTime, String strPathOT) {
    	//��ȡ�ļ������Ϣ
    	String strM = strFileTime.substring(4, 6);
    	//�ļ���չ���任
    	String strFextUP = strFileExtName.toUpperCase();
    	//����ļ����ͳ��ڻ�
    	String strType = "";
    	String strYear = strFileTime.substring(0, 4);
    	String strMonth = "";
		//ͨ����չ�����ļ�����
		if (null == strFileName || "".equals(strFileName)
			|| null == strFileExtName || "".equals(strFileExtName)
			|| null == strFileTime || "".equals(strFileTime)) {
			System.out.println("������ļ���Ϣ�д������������룡");
			return;
		}
		if ("JPG".equals(strFextUP) || "PNG".equals(strFextUP) || "CR2".equals(strFextUP)
			|| "BMP".equals(strFextUP) || "PCX".equals(strFextUP) || "TIFF".equals(strFextUP)
			|| "GIF".equals(strFextUP) || "JPEG".equals(strFextUP) || "TGA".equals(strFextUP)
			|| "EXIF".equals(strFextUP) || "FPX".equals(strFextUP) || "SVG".equals(strFextUP)
			|| "PSD".equals(strFextUP) || "CDR".equals(strFextUP) || "PCD".equals(strFextUP)
			|| "DXF".equals(strFextUP) || "UFO".equals(strFextUP) || "EPS".equals(strFextUP)
			|| "AI".equals(strFextUP) || "HDRI".equals(strFextUP) || "RAW".equals(strFextUP)) {
			strType = "00��Ƭ";
			lpic++;
		}
		else if ("MPEG".equals(strFextUP) || "AVI".equals(strFextUP) || "MOV".equals(strFextUP)
				|| "ASF".equals(strFextUP) || "WMV".equals(strFextUP) || "NAVI".equals(strFextUP)
				|| "3GP".equals(strFextUP) || "REAL VIDEO".equals(strFextUP)
				|| "MKV".equals(strFextUP) || "FLV".equals(strFextUP) || "F4V".equals(strFextUP)
				|| "RMVB".equals(strFextUP) || "WebM".equals(strFextUP)) {
			strType = "00��Ƶ";
			lvedio++;
		}
		else {
			System.out.println("������ļ���ʽ�д������������룡");
			return;
		}

		//�·ݱ༭
		switch(strM) {
		    case "01":
		    	strMonth = "һ��";
		    	break;
		    case "02":
		    	strMonth = "����";
		    	break;
		    case "03":
		    	strMonth = "����";
		    	break;
		    case "04":
		    	strMonth = "����";
		    	break;
		    case "05":
		    	strMonth = "����";
		    	break;
		    case "06":
		    	strMonth = "����";
		    	break;
		    case "07":
		    	strMonth = "����";
		    	break;
		    case "08":
		    	strMonth = "����";
		    	break;
		    case "09":
		    	strMonth = "����";
		    	break;
		    case "10":
		    	strMonth = "ʮ��";
		    	break;
		    case "11":
		    	strMonth = "ʮһ��";
		    	break;
		    case "12":
		    	strMonth = "ʮ����";
		    	break;
	    	default:
		    	strMonth = "�쳣";
		    	break;
		}
		//����ļ�·���༭
    	String strPath = strPathOT.concat("\\").concat(strType).concat("\\")
    						.concat(strYear).concat("\\").concat(strMonth);
    	File fpathOT = new File(strPath);
		if (!fpathOT.exists()) {
    		fpathOT.mkdirs();
    	}

		//Ŀ¼���ڣ��ж��ļ��Ƿ����
    	//�ظ��ж��ñ����༭
    	String strFile = strPath.concat("\\").concat(strFileName);
    	String strFileNew =
    			strFileName.substring(0, strFileName.length() - strFileExtName.length() - 1);
    	File fpath = new File(strFile);
    	//�ļ��ظ�ʱ������
    	int i = 1;
		while (fpath.exists()) {
			strFile = strPath.concat("\\").concat(
					strFileNew.concat(String.valueOf(i)).concat(".").concat(strFileExtName));
        	fpath = new File(strFile);
        	i++;
		}
		//�ļ����ƿ�ʼ
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

    //������
	public static void main(String[] args) {
		System.out.println("�ļ�����      ��ʼ");
		//ȡ�ô��ļ����·��
		String strpath = path();
		List<File> listFile = new ArrayList<File>();
		//��ȡ�ļ�
		if (null != strpath && !"".equals(strpath)) {
			ReadAllFile(strpath, listFile);
		}
		else {
			System.out.println("�ļ�ȡ��ʧ��");
		}
		//�����ļ�
		if (listFile != null && listFile.size() > 0) {
			System.out.println("�����ļ�����==" + listFile.size());
			Zhengli(listFile, "H:\\����");
			System.out.println("�����Ƭ����==" + lpic);
			System.out.println("�����Ƶ����==" + lvedio);
		}
		else {
			System.out.println("����Ŀ¼��û�ж�Ӧ���ļ�");
		}

		System.out.println("�ļ�����      ����");
	}
}

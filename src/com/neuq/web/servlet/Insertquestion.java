package com.neuq.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.SmartFile;
import com.jspsmart.upload.SmartFiles;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.neuq.bean.Bct;
import com.neuq.bean.Tkt;
import com.neuq.bean.Xzt;
import com.neuq.dao.I.BctInterfaceDao;
import com.neuq.dao.I.TeacherInterfaceDao;
import com.neuq.dao.I.TktInterfaceDao;
import com.neuq.dao.I.XztInterfaceDao;
import com.neuq.dao.Impl.BctInterfaceImplDao;
import com.neuq.dao.Impl.TeacherInterfaceImplDao;
import com.neuq.dao.Impl.TktInterfaceImplDao;
import com.neuq.dao.Impl.XztInterfaceImplDao;


public class Insertquestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Insertquestion() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * �ϴ����
		 */
		String a = request.getParameter("questiontype");
		if(a.equals("xzt")||a.equals("bct")||a.equals("tkt")){
		
		/**
		 * �����߼���
		 */

		if(a.equals("xzt")){
			String question  = request.getParameter("question");
			String answer = request.getParameter("answer");
			String OptionA  = request.getParameter("OptionA");
			String OptionB  = request.getParameter("OptionB");
			String OptionC  = request.getParameter("OptionC");
			String OptionD  = request.getParameter("OptionD");
System.out.println(question);
			String questionpoint = "java";
			Xzt xzt = new Xzt(question,answer,OptionA,OptionB,OptionC,OptionD,questionpoint);
			XztInterfaceDao xf=new XztInterfaceImplDao();
			if(xf.insert(xzt)){
				System.out.println("xzt����ɹ�");
			}
		}
		else if(a.equals("tkt")){
			String question  = request.getParameter("question");
			String answer = request.getParameter("answer");
			String questionpoint = "java";
			Tkt tkt = new Tkt(question,answer,questionpoint);
			TktInterfaceDao tf=new TktInterfaceImplDao();
			if(tf.insert(tkt)){
				System.out.println("tkt����ɹ�");
			}
		}
		else if(a.equals("bct")){
			String question  = request.getParameter("question");
			String questionpoint = "java";
			Bct bct = new Bct(question,questionpoint);
			BctInterfaceDao bf=new BctInterfaceImplDao();			
			if(bf.insert(bct)){
				System.out.println("bct����ɹ�");
			}			
		}}
		else{
		
		/**
		 * �����߼���
		 */
		SmartUpload su = new SmartUpload();
		
		PageContext pageContext=JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		// ��ʼ��
		su.initialize(pageContext);
		// �����ļ��ϴ����Ե�����
		su.setAllowedFilesList("xls,xlsx");
		// �����ϴ������ļ��Ĵ�С
		su.setMaxFileSize(1024 * 1024 * 10);// 10mb
		// �������ϴ��ļ��Ĵ�С
		su.setTotalMaxFileSize(1024 * 1024 * 10 * 5);
		// 50mb
		try {
			su.upload();
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ��ʼ�����ϴ�
		// ��ȡ���ϴ����ļ�
		//SmartRequest sRequest=su.getRequest();
	
		SmartFiles sfs = su.getFiles();
		// ��ȡһ��
		SmartFile sf = sfs.getFile(0);
		try {
			//String fname = sf.getFileName();
			System.out.println(a + "   >>>>>>>" + sf.getFileName());
			//sf.saveAs("/upload//"+sf.getFileName() ,1);
			sf.saveAs("/upload//"+sf.getFileName() ,1);
		} catch (SmartUploadException e) {
			e.printStackTrace();
		}
		String path = request.getServletPath();
		System.out.println("�ļ��ϴ��ɹ���");
		TeacherInterfaceDao teacherInterfaceDao = new TeacherInterfaceImplDao();
		System.out.println("  1111   " + sf.getFileName()+"1111"+path);
		
		
		
		 if(a.equals("xztbatch")){
			int b = 1;
			try {
				if(teacherInterfaceDao.batchquestion(b,sf.getFileName(),path)){
				System.out.println("xzt����ɹ�");}
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		else if(a.equals("tktbatch")){
			int b = 2;
			try {
				if(teacherInterfaceDao.batchquestion(b,sf.getFileName(),path)){	
				System.out.println("tkt����ɹ�");}
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		else{
			int b = 3;
			try {
				if(teacherInterfaceDao.batchquestion(b,sf.getFileName(),path)){
				System.out.println("bct����ɹ�");}
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		//��תҳ�����
		
	}
		request.getRequestDispatcher("teacher/addQuestion.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

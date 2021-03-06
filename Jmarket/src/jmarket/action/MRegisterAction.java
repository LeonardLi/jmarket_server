package jmarket.action;

import java.io.BufferedReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jmarket.dao.TUserDAO;
import jmarket.pojo.TUser;
import jmarket.util.ConstantValue;
import jmarket.util.Util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * RegisterAction for mobile
 * @author Jenson
 *
 */
public class MRegisterAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,Object> jsonMap = new HashMap<String, Object>();
	
	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}
	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}
	
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*"); //允许哪些url可以跨域请求到本域
		response.setHeader("Access-Control-Allow-Methods","POST"); //允许的请求方法，一般是GET,POST,PUT,DELETE,OPTIONS
		response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type"); //允许哪些请求头可以跨域
		
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer jb = new StringBuffer();
	    String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) {
			  System.out.println(e.getMessage());
			  e.printStackTrace();
		  }
		  String postData = jb.toString();
		  if(postData == null || postData.isEmpty()) {
			  	jsonMap.clear();
				jsonMap.put(ConstantValue.FLAG_KEY, ConstantValue.FLAG_FAIL_VALUE);
				jsonMap.put(ConstantValue.MSG_KEY, ConstantValue.MSG_BADREQUEST);
				return SUCCESS;
		  }
		  JSONObject jsonObject = null;
		  try{
			  jsonObject = JSONObject.fromObject(postData);
		  }catch (JSONException e) {
			  System.out.println(e.getMessage());
			  e.printStackTrace();
			  jsonMap.clear();
			  jsonMap.put(ConstantValue.FLAG_KEY, ConstantValue.FLAG_FAIL_VALUE);
			  jsonMap.put(ConstantValue.MSG_KEY, ConstantValue.MSG_BADREQUEST);
			  return SUCCESS;
		  }
//		  String userName = jsonObject.optString(ConstantValue.USERNAME_KEY, "");
		  String psw = jsonObject.optString(ConstantValue.PSW_KEY, "");
		  String email = jsonObject.optString(ConstantValue.EMAIL_KEY, "");
		  if(email.equals("")) {
			  jsonMap.clear();
			  jsonMap.put(ConstantValue.FLAG_KEY, ConstantValue.FLAG_FAIL_VALUE);
			  jsonMap.put(ConstantValue.MSG_KEY, ConstantValue.MSG_BADREQUEST);
			  return SUCCESS;
		  }
		  TUserDAO tud=new TUserDAO();
		  int size = tud.findByUEmail(email).size();
		  System.out.println("size="+size);
		  if(size > 0){
			  jsonMap.clear();
			  jsonMap.put(ConstantValue.FLAG_KEY, ConstantValue.FLAG_FAIL_VALUE);
			  jsonMap.put(ConstantValue.MSG_KEY, ConstantValue.MSG_USEREXISTS);
			  tud.closeSession();
			  return SUCCESS;
		  }
		  String[] str = email.split("@");
		  String uid = "U"+str[0];
		  String uname = "JMU"+str[0];
		  TUser user = new TUser();
		  user.setUEmail(email);
		  user.setUPassword(psw);
		  user.setUId(uid);
		  user.setUName(uname);
		  user.setURegtime(new Timestamp(System.currentTimeMillis()));
		  user.setUFlag("1");
		  user.setUType("1");
		  try{
			tud.save(user);
		 }catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			tud.closeSession();
			return SUCCESS;
		 }
		  String token = Util.createRandomCharData(10);
		  Util.mobileGlobalTokenToEmail.put(token, email);
		  Util.mobileGlobalEmailToToken.put(email, token);
		  
		  Map<String, String> userInfoMap = new HashMap<String, String>();
		  userInfoMap.put(ConstantValue.USERNAME_KEY, user.getUName());
		  userInfoMap.put(ConstantValue.EMAIL_KEY, user.getUEmail());
		  userInfoMap.put(ConstantValue.UID_KEY, user.getUId());
		  userInfoMap.put(ConstantValue.USR_IMAGE_KEY, user.getUImage());
		  userInfoMap.put(ConstantValue.REGTIME_KEY, user.getURegtime().toString());
		  userInfoMap.put(ConstantValue.WECHAT_KEY, user.getUWechat());
		  userInfoMap.put(ConstantValue.QQ_KEY, user.getUQq());
		  userInfoMap.put(ConstantValue.PHONE_KEY, user.getUPhone());
		  
		  jsonMap.clear();
		  jsonMap.put(ConstantValue.FLAG_KEY, ConstantValue.FLAG_SUCC_VALUE);
		  jsonMap.put(ConstantValue.TOKEN_KEY, token);
		  jsonMap.put(ConstantValue.USER_INFO_KEY, userInfoMap);
		  
		  tud.closeSession();
		  return SUCCESS;
	}
}

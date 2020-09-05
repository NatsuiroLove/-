package cn.edu.zucc.personplan.comtrol.example;

import cn.edu.zucc.personplan.itf.IUserManager;
import cn.edu.zucc.personplan.model.BeanUser;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.BusinessException;
import cn.edu.zucc.personplan.util.DBUtil;
import cn.edu.zucc.personplan.util.DbException;

import java.sql.Connection;
import java.sql.*;
import java.util.Date;

public class ExampleUserManager implements IUserManager {

	@Override
	public BeanUser reg(String userid, String pwd,String pwd2) throws BaseException {

		// 异常处理.用户名不可为空
		if(userid.length()==0)throw new BaseException("用户名不可为空！");
		// 异常处理.密码不可为空
		else if (pwd.length()==0||pwd2.length()==0)throw new BaseException("密码不可为空！");
		// 异常处理.两次输入的密码必须相同
		else if (!pwd.equals(pwd2))throw new BaseException("两次输入的密码必须相同！");

		/* 链接到数据库 */
		Connection conn = null;
		try{
			conn=DBUtil.getConnection();
			/* 检查用户账号是否重复 */
			String sql="select user_id from tbl_user where user_id = ?";//sql语句
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			ResultSet rs=pst.executeQuery();//存放查询结果
			if(rs.next()){
				rs.close();
				pst.close();
				throw new BusinessException("用户已经存在！");
			}

			/* 检测结束为合法新用户 插入数据库中 */
			rs.close();
			pst.close();
			sql = "insert into tbl_user(user_id,user_pwd,register_time) values(?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1,userid);
			pst.setString(2,pwd);
			pst.setString(3,String.valueOf(new Timestamp(System.currentTimeMillis())));
			pst.execute();
			BeanUser user = new BeanUser();
			user.setRegister_time(new Date());
			user.setUser_id(userid);
			return user;

		}catch(SQLException ex){
			throw new DbException(ex);
		}finally {
			/* 关闭数据链接 */
			if(conn!=null){
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}

	}

	
	@Override
	public BeanUser login(String userid, String pwd) throws BaseException {

		Connection conn = null;
		BeanUser user = null;

		if(pwd==null);
		try{
			conn=DBUtil.getConnection();
			String sql="select register_time "+" from tbl_user where user_id = ? and user_pwd = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1,userid);
			pst.setString(2,pwd);
			java.sql.ResultSet rs = pst.executeQuery();

			if(rs.next()){
				user = new BeanUser();
				user.setUser_id(userid);
				user.setRegister_time(rs.getTimestamp(1));
				return user;
			}
			else {
				throw new BusinessException("用户不存在或密码错误！");
			}

		}catch(SQLException ex){
			throw new DbException(ex);
		}finally {
			if(conn!=null){
				try{
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public void changePwd(BeanUser user, String oldPwd, String newPwd,
			String newPwd2) throws BaseException {
		// TODO Auto-generated method stub
		
	}

}

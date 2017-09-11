/**
 * 
 */
package com.codeagles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.codeagles.bean.JianShuBean;

/**
 * @author hcn
 *	Dao业务层 将数据插入到数据库中
 */
public class MDao extends BaseDao{
	private Connection conn = null;
	private Statement stmt = null;
	//添加数据到数据库
	public int addInfo(JianShuBean jianShu){
		String sql = "insert into jsspider(title,date,content,createtime,updatetime)"
				+ "values(?,?,?,?,?)";
		PreparedStatement pstm;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, jianShu.getTitle());
			pstm.setDate(2, jianShu.getDate());
			pstm.setString(3, jianShu.getContent());
			pstm.setDate(4, jianShu.getCreatetime());
			pstm.setDate(5, jianShu.getUpdatetime());
			return pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		BaseDao.close();
		return -1;
	}
}

package com.candata.magic.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.candata.magic.pojo.Grade;
import com.candata.magic.pojo.Opus;
import com.candata.magic.pojo.Player;
import com.candata.magic.pojo.Portal;
import com.candata.magic.utils.JDBCConnection;

public class MegagamePagePipeline implements Pipeline {
	private static String sourceURL = "http://www.55188.com/forumdisplay.php?fid=8&filter=type&typeid=153";
	private int count;

	@Override
	public void process(ResultItems resultitems, Task task) {
		String reqUrl = resultitems.getRequest().getUrl();
		if (sourceURL.equals(reqUrl)) {
			List<Portal> list = resultitems.get("list");
			Connection connection = null;
			PreparedStatement pspt = null;
			try {
				connection = JDBCConnection.getConnection();
				String sql = "insert into t_portal(tid,theme,begintime,link,linkedcode,gid) values(?,?,?,?,?,?)";
				pspt = connection.prepareStatement(sql);
				for (Portal portal : list) {
					pspt.setInt(1, portal.getTid());
					pspt.setString(2, portal.getTheme());
					java.sql.Date date = new java.sql.Date(portal.getBegintime().getTime());
					pspt.setDate(3, date);
					pspt.setString(4, portal.getLink());
					pspt.setString(5, portal.getLinkedcode());
					pspt.setInt(6, portal.getGid());
					pspt.addBatch();
				}
				pspt.executeBatch();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pspt != null) {
						pspt.close();
					} 
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {

			List<Player> players = resultitems.get("players");
			Connection connection = null;
			PreparedStatement pspt = null;
			try {
				connection = JDBCConnection.getConnection();
				String sql = "insert into t_player(pid,username,userlink) values(?,?,?)";
				pspt = connection.prepareStatement(sql);
				for (Player player : players) {
					pspt.setInt(1, player.getPid());
					pspt.setString(2, player.getUsername());
					pspt.setString(3, player.getUserlink());
					pspt.addBatch();
				}
				pspt.executeBatch();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pspt != null) {
						pspt.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			List<Opus> opuses = resultitems.get("opuses");
			try {
				connection = JDBCConnection.getConnection();
				String sql = "insert into t_opus(opusid,stock,sccode,more) values(?,?,?,?)";
				pspt = connection.prepareStatement(sql);
				for (Opus opus : opuses) {
					pspt.setInt(1, opus.getOpusid());
					pspt.setString(2, opus.getStock());
					pspt.setInt(3, opus.getSccode());
					pspt.setString(4, opus.getMore());
					pspt.addBatch();
				}
				pspt.executeBatch();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pspt != null) {
						pspt.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


			List<Grade> grades = resultitems.get("grades");
			try {
				connection = JDBCConnection.getConnection();
				String sql = "insert into t_grade(gid,batchNo,ranking,opusid,pid) values(?,?,?,?,?)";
				pspt = connection.prepareStatement(sql);
				for (Grade grade : grades) {
					pspt.setInt(1, grade.getGid());
					pspt.setInt(2, grade.getBatchno());
					pspt.setInt(3, grade.getRanking());
					pspt.setInt(4, grade.getOpusid());
					pspt.setInt(5,grade.getPid());
					pspt.addBatch();
				}
				pspt.executeBatch();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pspt != null) {
						pspt.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

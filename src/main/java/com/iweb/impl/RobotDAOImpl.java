package com.iweb.impl;

import com.iweb.DAO.RobotDAO;
import com.iweb.pojo.Robot;
import com.iweb.test.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : ljb
 * @date : 2023-03-07 15:22
 **/
public class RobotDAOImpl implements RobotDAO {
    @Override
    public boolean search(String question) {
        String sql="select * from robot where question=? and is_deleted=0";
        try(Connection c= JDBCUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1,question);
            ResultSet rs = ps.executeQuery();
            //如果在这里面能找到question，则返回true
            while (rs.next()) {
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean search(Integer id) {
        String sql="select * from robot where pk_id=? and is_deleted=0";
        try(Connection c= JDBCUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            //如果在这里面能找到question，则返回true
            while (rs.next()) {
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(Robot r) {
        if(!search(r.getQuestion())) {
            String sql = "insert into robot(question,answer,gmt_create,gmt_modified,is_deleted) values(?,?,NOW(),NOW(),0))";
            try (Connection c = JDBCUtil.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, r.getQuestion());
                ps.setString(2, r.getAnswer());
                ps.execute();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            System.out.println("该问题已经存在，增加错误");
            return false;
        }
    }

    @Override
    public boolean update(Robot r) {
        if(search(r.getQuestion())) {
            String sql = "update robot set question=?,answer=?,gmt_create=NOW(),gmt_modified=NOW(),is_deleted=0 where pk_id=? and " +
                    "is_deleted=0";
            try (Connection c = JDBCUtil.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, r.getQuestion());
                ps.setString(2, r.getAnswer());
                ps.setInt(3, r.getPk_id());
                ps.execute();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            System.out.println("不存在该问答，更新错误");
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        if(search(id)){
            String sql="update robot set is_deleted=1 where pk_id=?";
            try(Connection c=JDBCUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)) {
                ps.setInt(1,id);
                ps.execute();
                return true;
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }else {
            System.out.println("没有该问答，删除失败");
            return false;
        }
    }

    @Override
    public List<Robot> listAll() {
        String sql="select * from robot where is_deleted=0";
        List<Robot> list=new ArrayList<>();
        try (Connection c=JDBCUtil.getConnection();
        PreparedStatement ps=c.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Robot robot=new Robot();
                robot.setQuestion(rs.getString("question"));
                robot.setAnswer(rs.getString("answer"));
                robot.setGmt_create(rs.getDate("gmt_create"));
                robot.setGmt_modified(rs.getDate("gmt_modified"));
                list.add(robot);
            }
            return list;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("显示错误");
            return null;
        }
    }

    @Override
    public boolean respond(String question) {
        String sql="SELECT answer FROM robot WHERE ? LIKE  CONCAT('%',question,'%')";
        try(Connection c=JDBCUtil.getConnection();
        PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,question);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("answer"));
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}

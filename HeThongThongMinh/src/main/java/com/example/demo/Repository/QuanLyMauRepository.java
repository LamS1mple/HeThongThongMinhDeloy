package com.example.demo.Repository;

import java.awt.Checkbox;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.interfaces.RSAKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Mau1;
import com.example.demo.Entity.Mau2;
import com.example.demo.Entity.Model2;
import com.example.demo.Entity.Nhan;
import com.example.demo.Entity.Nhan1;
import com.example.demo.Repository.Impl.QuanLyMauImpl;
@Repository
public class QuanLyMauRepository implements QuanLyMauImpl{

	@Autowired(required = true)
	JdbcTemplate jdbcTemplate;
	
	private static final String UPLOAD_DIRECTORY =  "D:\\IMG_HTTM\\De";

	
	private static final String GETALLNHANBYTEN = "Select * from nhan where ten like ?";
	private static final String GETALLMAUBYTEN = "Select * from mau2 where nhanId = ? and path like ?";
	private static final String ADDIMGMAU2 = "Insert into mau2 (path, ngayThem , nhanId, isTrain) values ( ?, ? , ? , ?)";
	private static final String UPDATEIMGMAU2 = "Update mau2 set path = ?, ngayThem = ? where id = ?";
	private static final String DELETEMAU2 = "Delete from mau2 where id = ?";
	
	//DETECTION
	private static final String GETNHAN1BYID = "Select * from nhan1 where idMau1 = ?";
	private static final String GETMAU1BYNAME = "Select * from mau1 where path like ?";
	private static final String ADDMAU1 = "Insert into mau1(path, ngayThem) values(? ,?)";
	private static final String ADDNHAN1 = "Insert into nhan1(X1,Y1,X2,Y2,idMau1) values(? ,? ,? ,? ,?)";
	private static final String GETKEY1 = "Select * from mau1 ";
	private static final String DELETENHAN1 = "Delete from nhan1 where idMau1 = ?";
	private static final String DELETEMAU1 = "Delete from mau1 where id = ?";
	private static final String UPDATEMAU1 = "Update mau1 set path = ?, ngayThem = ? where id = ?";
	//Model2
	private static final String GETALLMODEL = "Select * from model2";
	private static final String UPDATEMODEL = "Update model2 set isTrain = ? where id = ?";
	private static final String DELETEMODEL = "Delete from model2 where path = ?";
	private static final String CHECKTRAIN = "SELECT count(*) FROM mau2 where isTrain = ?";
	private static final String DATRAIN = "Update mau2 set isTrain = 1 where isTrain = 0";

	
	@Override
	public List<Nhan> getAllNhanByTen(String a) {
		List<Nhan> nhans = jdbcTemplate.query(GETALLNHANBYTEN ,
				
		new RowMapper<Nhan>() {

			@Override
			public Nhan mapRow(ResultSet rs, int rowNum) throws SQLException {
				Nhan nhan = new Nhan(rs.getLong(1),rs.getString(2));
				return nhan;
			}
			
		},"%" + a + "%" );
		
		return nhans;
	}

	@Override
	public List<Mau2> getAllMauByTen(int id, String name) {
		
		List<Mau2> mau2s = jdbcTemplate.query(GETALLMAUBYTEN, new RowMapper<Mau2>() {

			@Override
			public Mau2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Mau2(rs.getInt(1), rs.getString(2), rs.getDate(3));
			}
			
		}, id , "%"+name+"%");
		return mau2s;
	}

	@Override
	public void addImgMau2(int id, Mau2 mau2) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		jdbcTemplate.update(ADDIMGMAU2, mau2.getPathMau2(), mau2.getNgayThem() , id , 0);
	}

	@Override
	public void updateImgMau2(int id, Mau2 mau2) {
		jdbcTemplate.update(UPDATEIMGMAU2 , mau2.getPathMau2() , mau2.getNgayThem() ,id);
	}

	@Override
	public void deleteMau2(int id) {
		jdbcTemplate.update(DELETEMAU2, id);
	}
	
	//Detection

	@Override
	public List<Mau1> getAllMau1(String name) throws IOException {
		List<Mau1> mau1s = jdbcTemplate.query(GETMAU1BYNAME , new RowMapper<Mau1>() {
		
			@Override
			public Mau1 mapRow(ResultSet rs, int rowNum) throws SQLException {
				Mau1 mau1 = new Mau1( rs.getInt(1), rs.getString(2), rs.getDate(3) );
				return mau1;
			}
			
		},"%"+ name + "%");
		 
		for (int i = 0 ; i < mau1s.size() ; i ++) {
			List<Nhan1> nhan1s = jdbcTemplate.query(GETNHAN1BYID, new RowMapper<Nhan1>() {

				@Override
				public Nhan1 mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					return new Nhan1(rs.getInt(1), rs.getInt(2), rs.getInt(3) , rs.getInt(4), rs.getInt(5));
				}
				
				
			} , mau1s.get(i).getIdMau1());
			mau1s.get(i).setLisNhan1(nhan1s);
			FileInputStream fileInputStream = new FileInputStream(new File("D:/IMG_HTTM/De/" + mau1s.get(i).getPathMau1()));
			mau1s.get(i).setImgMau1(fileInputStream.readAllBytes());
			fileInputStream.close();
			
		}
		
		return mau1s;
	}

	
	private void addNhan1(List<Nhan1> a , int id) {
		for (Nhan1 i : a) {
			jdbcTemplate.update(ADDNHAN1, i.getX1(), i.getY1(), i.getX2(), i.getY2(), id);
		}
	}
	
	@Override
	public void addMau1(Mau1 mau1) {
		jdbcTemplate.update(ADDMAU1, mau1.getPathMau1(), mau1.getNgayThem());
		List<Integer> id = jdbcTemplate.query(GETKEY1, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				int a = rs.getInt(1);
				return a;
			}
			
		} );
		addNhan1(mau1.getListNhan1(), id.get(id.size() - 1));
		
	}

	@Override
	public void deleteMau1(int id) {
		deleteNhan1(id);
		jdbcTemplate.update(DELETEMAU1 , id);
	}

	@Override
	public void deleteNhan1(int id) {
		jdbcTemplate.update(DELETENHAN1 , id);
		
	}

	@Override
	public void updateMau1(Mau1 mau1,  int id) {
		deleteNhan1(id);
		jdbcTemplate.update(UPDATEMAU1, mau1.getPathMau1() , mau1.getNgayThem() , id);
		addNhan1(mau1.getListNhan1(), id);
	}

	@Override
	public List<Model2> getAllModel() throws IOException {
		List<Model2> list = jdbcTemplate.query(GETALLMODEL,  new RowMapper<Model2>() {

			@Override
			public Model2 mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return new Model2(rs.getInt(1), rs.getString(2),rs.getDate(3), rs.getFloat(4),
						 rs.getFloat(5),  rs.getFloat(6),  rs.getFloat(7), rs.getBoolean(8));
			}
			
		});
		return list;
	}

	public void updateModel2(int id, boolean check) {
		jdbcTemplate.update(UPDATEMODEL, check , id);
	}

	@Override
	public void deleteModel(String name) {
		jdbcTemplate.update(DELETEMODEL,name );
		
	}
	public int count (boolean s) {
		return jdbcTemplate.queryForObject(CHECKTRAIN, Integer.class, s);
	}

	@Override
	public void updateModel() {
		jdbcTemplate.update(DATRAIN);
		
	}
	
//	public  void a0 () {
//		jdbcTemplate.update("Update mau2 set isTrain = 0 where isTrain = 1");
//	}
//	public static void main(String[]a1) {
//		QuanLyMauRepository a = new QuanLyMauRepository();
//		a.a0();
//	}
}

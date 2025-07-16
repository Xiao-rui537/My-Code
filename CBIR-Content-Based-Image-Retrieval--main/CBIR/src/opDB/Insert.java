package opDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Insert {
	Connection con;
	String driver = "com.mysql.cj.jdbc.Driver";
	String user = "root";
	String password = "123456";
	String picture_url = "jdbc:mysql://localhost:3306/picture?serverTimezone=GMT";
	String feature_url = "jdbc:mysql://localhost:3306/feature?serverTimezone=GMT";

	public ArrayList<String> readFile(String file) {
		ArrayList<String> datas = new ArrayList<>();
		try {
			File inputFile = new File(file);
			if (!inputFile.exists()) {
				System.err.println("File does not exist: " + file);
				return datas;
			}
			if (!inputFile.canRead()) {
				System.err.println("No permission to read the file:" + file);
				return datas;
			}
			FileReader fr = new FileReader(inputFile);
			BufferedReader bf = new BufferedReader(fr);
			String str;
			while ((str = bf.readLine()) != null) {
				datas.add(str);
			}
			bf.close();
			fr.close();
		} catch (IOException e) {
			System.err.println("Error while reading file:" + file);
			e.printStackTrace();
		}
		return datas;
	}

	// 插入数据库
	public void insertdb(ArrayList<String> sqls, String mode) throws Exception {
		Class.forName(driver);
		int num = sqls.size();
		if (mode.equals("path")) {
			// 插入图片路径
			con = DriverManager.getConnection(picture_url, user, password);
			Statement statement = con.createStatement();
			for (int i = 0; i < num; i++) {
				statement.executeUpdate(sqls.get(i));
			}
			statement.close();
		} else {
			// 插入特征数据
			con = DriverManager.getConnection(feature_url, user, password);
			Statement statement = con.createStatement();
			for (int i = 0; i < num; i++) {
				System.out.println(sqls.get(i));
				statement.executeUpdate(sqls.get(i));
			}
			statement.close();
		}
		con.close();
	}

	// 生成路径插入sql语句
	public ArrayList<String> pathInsertsql(ArrayList<String> paths) {
		ArrayList<String> sqls = new ArrayList<String>();
		int num = paths.size();
		String sql = null;
		String path;
		sqls.add("truncate table paths;");
		for (int i = 0; i < num; i++) {
			path = paths.get(i);
			path = path.replace("\\", "\\\\");
			sql = "insert into paths values(" + i + ", '" + path + "');";
			sqls.add(sql);
		}
		return sqls;
	}

	// 生成颜色特征插入sql语句
	public ArrayList<String> colorInsertsql(ArrayList<String> colors) {
		ArrayList<String> sqls = new ArrayList<String>();
		int num = colors.size();
		double[] color = new double[9];
		String sql = null;
		sqls.add("truncate table color;");
		for (int i = 0; i < num; i++) {
			String[] datas = colors.get(i).split(";");
			sql = "insert into color values(" + i + ", '" + datas[0] + "', '" + datas[1]
					+ "', '" + datas[2] + "', '" + datas[3] + "', '" + datas[4] + "', '" + datas[5]
					+ "', '" + datas[6] + "', '" + datas[7] + "', '" + datas[8] + "');";
			sqls.add(sql);
		}
		return sqls;
	}

	// 生成纹理特征插入sql语句
	public ArrayList<String> textureInsertsql(ArrayList<String> texs) {
		ArrayList<String> sqls = new ArrayList<String>();
		int num = texs.size();
		double[] tex = new double[8];
		String sql = null;
		sqls.add("truncate table texture;");
		for (int i = 0; i < num; i++) {
			String[] datas = texs.get(i).split(";");
			sql = "insert into texture values(" + i + ", '" + datas[0] + "', '" + datas[1]
					+ "', '" + datas[2] + "', '" + datas[3] + "', '" + datas[4] + "', '" + datas[5]
					+ "', '" + datas[6] + "', '" + datas[7] + "');";
			sqls.add(sql);
		}
		return sqls;
	}

	// 生成形状特征插入sql语句
	public ArrayList<String> shapeInsertsql(ArrayList<String> texs) {
		ArrayList<String> sqls = new ArrayList<String>();
		int num = texs.size();
		double[] tex = new double[8];
		String sql = null;
		sqls.add("truncate table shape;");
		for (int i = 0; i < num; i++) {
			String[] datas = texs.get(i).split(";");
			sql = "insert into shape values(" + i + ", '" + datas[0] + "', '" + datas[1]
					+ "', '" + datas[2] + "', '" + datas[3] + "', '" + datas[4] + "', '" + datas[5]
					+ "', '" + datas[6] + "', '" + datas[7] + "');";
			sqls.add(sql);
		}
		return sqls;
	}
}

import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class Gaji implements PTABC {
	//static Scanner scanner;
	static Connection conn;
	String url = "jdbc:mysql://localhost:3306/database_karyawan";
	
	public Integer noPegawai;
	public Integer gajiPokok = 0; 
    public Integer jumlahHariMasuk = 0;
    public double potongan = 0;
    public Integer jumlahHariAbsen = 0;
    public Integer totalGaji = 0;
    public String namaPegawai, jabatan;


    Scanner Input = new Scanner (System.in);

    public void displaydatabase() throws SQLException {
		String text1 = "\nDaftar Data Seluruh Pegawai";
		System.out.println(text1.toUpperCase());
						
		String sql ="SELECT * FROM karyawan";
		conn = DriverManager.getConnection(url,"root","");
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
		while(result.next()){
			System.out.print("\nNomor pegawai\t  : ");
            System.out.print(result.getInt("id_pegawai"));
            System.out.print("\nNama pegawai\t  : ");
            System.out.print(result.getString("nama_pegawai"));
            System.out.print("\nJabatan\t\t  : ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nJumlah hari masuk : ");
            System.out.print(result.getInt("hari_masuk"));
            System.out.print("\nTotal gaji\t  : ");
            System.out.print(result.getInt("gaji_total"));
            System.out.print("\n");
		}
	}
    
    public void insertdata() throws SQLException {
    	String text2 = "\nTambah Data Pegawai";
		System.out.println(text2.toUpperCase());
		
    	try {
	        // NoPegawai
	    	System.out.print("Masukkan nomor pegawai: ");
	        noPegawai = Input.nextInt();
	        Input.nextLine();
	
	        // NamaPegawai
	        System.out.print("\nMasukkan nama pegawai: ");
	        namaPegawai = Input.nextLine(); 
		        
	        // PilihJabatan
	        int pilihJabatan;
	        System.out.println("\n1. Direktur\n2. Manajer\n3. Administrasi\n4. Karyawan");
	        System.out.print("Pilih jabatan (1 - 4) : ");
	        pilihJabatan = Input.nextInt();
	        if (pilihJabatan == 1){
	            jabatan = "Direktur";
	        }
	        else if (pilihJabatan == 2){
	            jabatan = "Manajer";
	        }
	        else if (pilihJabatan == 3){
	            jabatan = "Staff";
	        }
	        else if (pilihJabatan == 4){
	            jabatan = "Karyawan";
	        }
	        else{
	            System.out.println("Jabatan tidak tersedia");
	        }
	        System.out.println(jabatan.toUpperCase());
	
	        if (jabatan == "Direktur"){
	            gajiPokok = 12000000;
	        }
	        else if (jabatan == "Manajer"){
	            gajiPokok = 10000000;
	        }
	        else if (jabatan == "Staff"){
	            gajiPokok = 7500000;
	        }
	        else if (jabatan == "Karyawan"){
	            gajiPokok = 5000000;
	        }
	        else{
	            System.out.println("\nGaji pokok tidak tersedia");
	        }
	        System.out.println("\nGaji pokok : Rp" + gajiPokok);
	        
	        // JumlahHariMasuk
	        System.out.print("\nMasukkan jumlah hari absen (0 - 30): ");
	        jumlahHariAbsen = Input.nextInt();
	        jumlahHariMasuk = 30 - jumlahHariAbsen;
	        System.out.println("Jumlah hari masuk: " + jumlahHariMasuk);
	        
	        // Potongan
	        potongan = jumlahHariAbsen*100000;
	        System.out.println("\nPotongan: Rp" + (int) potongan);
	        
	        // TotalGaji
	        totalGaji = gajiPokok - (int) potongan;
	        System.out.println("Total gaji: Rp" + totalGaji);
	        System.out.println("");
	        
	        String sql = "INSERT INTO karyawan (id_pegawai, nama_pegawai, jabatan, hari_masuk, gaji_total) VALUES ('"+noPegawai+"','"+namaPegawai+"','"+jabatan+"','"+jumlahHariMasuk+"','"+totalGaji+"')";
	        conn = DriverManager.getConnection(url,"root","");
	        Statement statement = conn.createStatement();
	        statement.execute(sql);
	        System.out.println("Berhasil input data!!");
    	}
    	catch (SQLException e) {
	        System.err.println("Terjadi kesalahan input data");
	    } 
    	catch (InputMismatchException e) {
	    	System.err.println("Inputan harus berupa angka");
	   	}
	} 
	public void ubahdata() throws SQLException{
		String text3 = "\nUbah Data Pegawai";
		System.out.println(text3.toUpperCase());
		
		try {
            displaydatabase();
            System.out.print("\nMasukkan Nomor Pegawai yang akan di ubah : ");
            Integer noPegawai = Integer.parseInt(Input.nextLine());
            
            String sql = "SELECT * FROM karyawan WHERE id_pegawai = " +noPegawai;
            conn = DriverManager.getConnection(url,"root","");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if(result.next()){
                
                System.out.print("Nama baru ["+result.getString("nama_pegawai")+"]\t: ");
                String namaPegawai = Input.nextLine();
                   
                sql = "UPDATE data_pegawai SET nama_pegawai='"+namaPegawai+"' WHERE id_pegawai='"+noPegawai+"'";
                //System.out.println(sql);

                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Berhasil memperbaharui data pegawai (Nomor "+noPegawai+")");
                }
            }
            statement.close();        
        } 
		catch (SQLException e) {
        	System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
	}
	
	public void delete() {
		String text4 = "\nHapus Data Pegawai";
		System.out.println(text4.toUpperCase());
		
		try{
	        displaydatabase();
	        System.out.print("\nMasukan ID Pegawai yang akan Anda Hapus : ");
	        Integer noPegawai= Integer.parseInt(Input.nextLine());
	        
	        String sql = "DELETE FROM karyawan WHERE id_pegawai = "+ noPegawai;
	        conn = DriverManager.getConnection(url,"root","");
	        Statement statement = conn.createStatement();
	        //ResultSet result = statement.executeQuery(sql);
	        
	        if(statement.executeUpdate(sql) > 0){
	            System.out.println("Berhasil menghapus data pegawai (Nomor "+noPegawai+")");
	        }
	   }
		catch(SQLException e){
	        System.out.println("Terjadi kesalahan dalam menghapus data");
	    }
        catch(Exception e){
            System.out.println("masukan data yang benar");
        }
	}
	
	public void searchdata() throws SQLException {
		String text5 = "\nCari Data Pegawai";
		System.out.println(text5.toUpperCase());
				
		System.out.print("Masukkan Nama Pegawai yang ingin dilihat : ");    
		String keyword = Input.nextLine();
		
		String sql = "SELECT * FROM karyawan WHERE nama_pegawai LIKE '%"+keyword+"%'";
		conn = DriverManager.getConnection(url,"root","");
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql); 
                
        while(result.next()){
        	System.out.print("\nNomor pegawai\t  : ");
            System.out.print(result.getInt("id_pegawai"));
            System.out.print("\nNama pegawai\t  : ");
            System.out.print(result.getString("nama_pegawai"));
            System.out.print("\nJabatan\t\t  : ");
            System.out.print(result.getString("jabatan"));
            System.out.print("\nJumlah hari masuk : ");
            System.out.print(result.getInt("hari_masuk"));
            System.out.print("\nTotal gaji\t  : ");
            System.out.print(result.getInt("gaji_total"));
            System.out.print("\n");
        }
	}   
}
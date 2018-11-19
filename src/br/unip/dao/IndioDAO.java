package br.unip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.unip.models.Indio;
import br.unip.utils.ConnectionFactory;

public class IndioDAO {
	
	//ADD Indio
	public boolean adicionar(Indio indio){
			
		String sql = "INSERT INTO tbl_indio(idReservaIndigena, nome, sexo, dtNasc, ativo) VALUES(?,?,?,?, 1)";
			
		ConnectionFactory fab = new ConnectionFactory();
		Connection con = fab.abrirConexao();//Estancia a conexao usada no statement
			
		try {
				
			PreparedStatement stm = (PreparedStatement) con.prepareStatement(sql);
			stm.setInt(1, indio.getIdReserva());
			stm.setString(2, indio.getNome());
			stm.setString(3, indio.getSexo());
			stm.setString(4,  indio.getDtNasc());
				
			stm.execute();
			fab.fecharConexao();
				
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean deleteIndio(Indio indio){
				
			String sql = "INSERT INTO tbl_reserva_indigena_log(idReservaIndigena, idIndio, tipo, data) VALUES(?,?,?,NOW())";
				
			ConnectionFactory fab = new ConnectionFactory();
			Connection con = fab.abrirConexao();//Estancia a conexao usada no statement
				
			try {
					
				PreparedStatement stm = (PreparedStatement) con.prepareStatement(sql);
				stm.setInt(1, indio.getIdReserva());
				stm.setInt(2, indio.getId());
				stm.setString(3, "DECREASE");
				stm.execute();
				
				sql = "UPDATE tbl_indo SET ativo = 0 WHERE id = " + indio.getId();
				
				stm.execute();
				
				fab.fecharConexao();
					
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				return false;
			}
			
			return true;
		}
	
	public ArrayList<Indio> listarIndios(int idReserva){
		
		ArrayList<Indio> indios = new ArrayList<>();
		String sql = "SELECT * FROM tbl_indio ";
		
		if ( idReserva > 0) {
			sql = sql + " WHERE idReservaIndigena = " + idReserva;		
		}
		
		ConnectionFactory fab = new ConnectionFactory();
		Connection con = fab.abrirConexao();//Estancia a conexao usada no statement
		
		try {
			
			PreparedStatement stm = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs;
			
			rs = stm.executeQuery();

			while( rs.next() ){
				Indio indio = new Indio();
				indio.setId( rs.getInt("id") );
				indio.setIdReserva( rs.getInt("idReservaIndigena") );
				indio.setNome( rs.getString("nome") );
				indio.setSexo( rs.getString("sexo"));
				indio.setDtNasc(rs.getString("dtNasc"));
				indio.setAtivo( rs.getInt("ativo"));
				indios.add(indio);
			}
			
			fab.fecharConexao();
			
		} catch (SQLException e) {

		}


		
		return indios;
	}
	
	public Indio getIndio(int id){	

		Indio indio = new Indio();
		String sql = "SELECT * FROM tbl_indio WHERE id = ?";

		ConnectionFactory fab = new ConnectionFactory();
		Connection con = fab.abrirConexao();//Estancia a conexao usada no statement
		
		try {
			
			PreparedStatement stm = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs;
			
			stm.setInt(1, id);
			
			rs = stm.executeQuery();

			
			while( rs.next() ){
				indio.setId( rs.getInt("id") );
				indio.setIdReserva( rs.getInt("idReservaIndigena") );
				indio.setNome( rs.getString("nome") );
				indio.setDtNasc(rs.getString("dtNasc"));
				indio.setAtivo( rs.getInt("ativo"));
			}
			
			fab.fecharConexao();
			
		} catch (SQLException e) {}
		
		return indio;
	}

}

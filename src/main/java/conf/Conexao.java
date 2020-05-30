package conf;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Conexao {

	private Connection connection;
	private DataSource ds;

	private Logger logger = Logger.getLogger(Conexao.class.getSimpleName());

	public Conexao(String datasource) {
		try {
			this.configure(datasource);
			connection = this.ds.getConnection();
		} catch (Exception e) {
			logger.severe(e.getClass().getSimpleName() + ":" + e.getMessage());
		}
	}

	public void configure(String datasource) {
		Context context;
		try {
			context = new InitialContext();
			this.ds = (DataSource) context.lookup(datasource);
		} catch (NamingException e) {
			logger.severe(e.getClass().getSimpleName() + ":" + e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void fecharConexao() {
		try {
			connection.close();
		} catch (SQLException e) {
			logger.severe(e.getClass().getSimpleName() + ":" + e.getMessage());
		}
	}

}
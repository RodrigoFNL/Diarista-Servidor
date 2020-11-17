package br.com.diarista.folks.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.folks.entity.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, String> 
{
	Optional<Usuario> findByCpf(String cpf);	
	Optional<Usuario> findByCoupon(String coupon);
	Optional<Usuario> findByEmail(String email);
}

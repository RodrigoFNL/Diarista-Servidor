package br.com.diarista.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.diarista.entity.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> 
{
	Optional<Usuario> findByCpf(String cpf);	
	Optional<Usuario> findByCoupon(String coupon);
	Optional<Usuario> findByEmail(String email);
}

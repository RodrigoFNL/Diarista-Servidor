package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="andress")
public class Endereco
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
}

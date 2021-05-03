package org.sid.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "acti")
public class Acti {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String titre;
	private String title;
	private String description;
	private String participants;
	private Date date_creation;
	private Date date_acti;
	private Date date_acti_fin;
	private String repetition;
	private String id_user;
	private String nom_animateur;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Benevole> benevoles_list = new ArrayList<> ( );
}

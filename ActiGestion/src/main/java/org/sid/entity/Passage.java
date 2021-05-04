package org.sid.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "passage")
public class Passage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date passageDate;
	private Long idBenevole;

}

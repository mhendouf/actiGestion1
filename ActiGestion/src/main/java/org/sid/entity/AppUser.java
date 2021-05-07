package org.sid.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUser {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true)
	private String username;
	// @JsonIgnore
	private String password;
	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<AppRoles> roles = new ArrayList<> ( );
}

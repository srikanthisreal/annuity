package com.lendico.annuity.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AnnuityRequest {

	@NotNull
	private BigDecimal loanAmount;

	@NotNull
	private BigDecimal nominalRate;

	@NotNull
	private Integer duration;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date startDate;

}

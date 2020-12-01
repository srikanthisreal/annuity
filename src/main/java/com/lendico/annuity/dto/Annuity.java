package com.lendico.annuity.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Annuity {

	private List<Installment> borrowerPayments;

}

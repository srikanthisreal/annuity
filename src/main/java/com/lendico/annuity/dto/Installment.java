package com.lendico.annuity.dto;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class defines the data transfer Object of Monthly payment.
 * 
 * @author Srikanth
 *
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Installment {

	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal borrowerPaymentAmount;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private LocalDateTime date;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal initialOutstandingPrincipal;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal interest;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal principal;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal remainingOutstandingPrincipal;
}

class MoneySerializer extends JsonSerializer<BigDecimal> {
	@Override
	public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeString(value.setScale(2, RoundingMode.HALF_UP).toPlainString());
	}
}

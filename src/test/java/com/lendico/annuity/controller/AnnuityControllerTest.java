package com.lendico.annuity.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.lendico.annuity.constants.RestPathURI;
import com.lendico.annuity.dto.Annuity;
import com.lendico.annuity.dto.AnnuityRequest;
import com.lendico.annuity.dto.Installment;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnnuityControllerIntegrationTest {

	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	@LocalServerPort
	private int port;

	@Test
	public void testGeneratePlan() {
		AnnuityRequest annuityRequest = perpareAnnuityRequest();

		HttpEntity<AnnuityRequest> entity = new HttpEntity<AnnuityRequest>(annuityRequest, headers);

		ResponseEntity<Annuity> postForEntity = restTemplate.postForEntity(
				createURLWithPort(RestPathURI.CONTEXT_PATH + "/" + RestPathURI.GENERATE_PLAN), entity, Annuity.class);
		assertThat(postForEntity.getStatusCode().is2xxSuccessful());
		List<Installment> borrowerPayments = postForEntity.getBody().getBorrowerPayments();
		assertEquals(borrowerPayments.size(), 24);

	}

	

	private AnnuityRequest perpareAnnuityRequest() {
		AnnuityRequest annuityRequest = new AnnuityRequest();
		annuityRequest.setDuration(24);
		annuityRequest.setLoanAmount(new BigDecimal(5000));
		annuityRequest.setNominalRate(new BigDecimal(5.0));
		annuityRequest.setStartDate(Calendar.getInstance().getTime());
		return annuityRequest;
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}

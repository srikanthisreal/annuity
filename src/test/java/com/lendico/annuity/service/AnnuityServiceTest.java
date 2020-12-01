package com.lendico.annuity.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.lendico.annuity.dto.Annuity;
import com.lendico.annuity.dto.AnnuityRequest;
import com.lendico.annuity.dto.Installment;
import com.lendico.annuity.exceptionhandling.ValidationException;

@SpringBootTest
class AnnuityControllerIntegrationTest {

	@InjectMocks
	private AnnuityService annuityService;

	@Test
	public void testgetRepaymentPlan() {
		Annuity repaymentPlan = annuityService.getRepaymentPlan(perpareAnnuityRequest());

		List<Installment> borrowerPayments = repaymentPlan.getBorrowerPayments();
		assertEquals(borrowerPayments.size(), 24);
		assertEquals(new BigDecimal(219.36).setScale(2, BigDecimal.ROUND_HALF_UP),
				borrowerPayments.get(0).getBorrowerPaymentAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(new BigDecimal(5000).setScale(2, BigDecimal.ROUND_HALF_UP),
				borrowerPayments.get(0).getInitialOutstandingPrincipal().setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(new BigDecimal(20.83).setScale(2, BigDecimal.ROUND_HALF_UP),
				borrowerPayments.get(0).getInterest().setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(new BigDecimal(198.53).setScale(2, BigDecimal.ROUND_HALF_UP),
				borrowerPayments.get(0).getPrincipal().setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(new BigDecimal(4801.47).setScale(2, BigDecimal.ROUND_HALF_UP),
				borrowerPayments.get(0).getRemainingOutstandingPrincipal().setScale(2, BigDecimal.ROUND_HALF_UP));

	}

	@Test
	public void testgetRepaymentPlanValidationErrorDuration() {
		AnnuityRequest perpareAnnuityRequest = perpareAnnuityRequest();
		perpareAnnuityRequest.setDuration(null);
		assertThrows(ValidationException.class, () -> {
			annuityService.getRepaymentPlan(perpareAnnuityRequest);
		});
	}
	
	@Test
	public void testgetRepaymentPlanValidationError() {
		AnnuityRequest perpareAnnuityRequest = perpareAnnuityRequest();
		perpareAnnuityRequest.setLoanAmount(BigDecimal.ZERO);
		assertThrows(ValidationException.class, () -> {
			annuityService.getRepaymentPlan(perpareAnnuityRequest);
		});
	}

	private AnnuityRequest perpareAnnuityRequest() {
		AnnuityRequest annuityRequest = new AnnuityRequest();
		annuityRequest.setDuration(24);
		annuityRequest.setLoanAmount(new BigDecimal(5000).setScale(2, BigDecimal.ROUND_HALF_UP));
		annuityRequest.setNominalRate(new BigDecimal(5.0).setScale(2, BigDecimal.ROUND_HALF_UP));
		annuityRequest.setStartDate(Calendar.getInstance().getTime());
		return annuityRequest;
	}
}

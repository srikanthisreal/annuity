package com.lendico.annuity.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lendico.annuity.dto.Annuity;
import com.lendico.annuity.dto.AnnuityRequest;
import com.lendico.annuity.dto.Installment;
import com.lendico.annuity.exceptionhandling.ProcessingException;
import com.lendico.annuity.exceptionhandling.ValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Srikanth
 *
 */
@Slf4j
@Service
public class AnnuityService {
	/**
	 * 
	 * @param annuityRequest
	 * @return
	 */
	public Annuity getRepaymentPlan(AnnuityRequest annuityRequest) {

		Annuity annuity = new Annuity();
		try {
			validateRequest(annuityRequest);
			log.info("Validation success!");
			List<Installment> installments = new ArrayList<>();

			BigDecimal monthlyInterest = getMonthlyInterest(annuityRequest);
			BigDecimal annuityValue = getAnnuity(annuityRequest, monthlyInterest);
			LocalDateTime localStartDate = LocalDateTime.ofInstant(annuityRequest.getStartDate().toInstant(),
					ZoneId.systemDefault());

			for (int i = 1; i <= annuityRequest.getDuration(); i++) {

				Installment installment = new Installment();
				installment.setDate(i == 1 ? localStartDate : localStartDate.plusMonths(1));
				installment.setBorrowerPaymentAmount(annuityValue);
				installment.setInitialOutstandingPrincipal(annuityRequest.getLoanAmount());

				BigDecimal interestAmmount = getInterest(monthlyInterest, annuityRequest);
				installment.setInterest(getInterest(monthlyInterest, annuityRequest));

				installment.setPrincipal(annuityValue.subtract(interestAmmount));

				BigDecimal remainingPrinciple = annuityRequest.getLoanAmount().subtract(installment.getPrincipal());

				installment.setRemainingOutstandingPrincipal(
						remainingPrinciple.compareTo(BigDecimal.ONE) > 0 ? remainingPrinciple : BigDecimal.ZERO);

				annuityRequest.setLoanAmount(remainingPrinciple);

				installments.add(installment);

			}
			annuity.setBorrowerPayments(installments);
		} catch (ValidationException e) {
			log.error("Error while calculating repayment plan {}: ", e.getMessage());
			throw e;
		} catch (ProcessingException e) {
			log.error("Error while calculating repayment plan {}: ", e.getMessage());
			throw e;
		}
		return annuity;

	}

	/**
	 * Calculate Annuity.
	 * 
	 * @param annuityRequest
	 * @param monthlyInterest
	 * @return
	 */
	private BigDecimal getAnnuity(AnnuityRequest annuityRequest, BigDecimal monthlyInterest) {
		double power = -annuityRequest.getDuration();
		return BigDecimal
				.valueOf(((annuityRequest.getLoanAmount().multiply(monthlyInterest)).doubleValue())
						/ (1 - (Math.pow((1 + monthlyInterest.doubleValue()), power))))
				.setScale(2, BigDecimal.ROUND_HALF_UP);

	}

	/**
	 * Calculate Monthly Interest.
	 * 
	 * @param annuityRequest
	 * @return
	 */
	private BigDecimal getMonthlyInterest(AnnuityRequest annuityRequest) {
		BigDecimal yearlyInterest = annuityRequest.getNominalRate().divide(new BigDecimal(100), 8,
				BigDecimal.ROUND_HALF_UP);
		return yearlyInterest.divide(new BigDecimal(12), 8, BigDecimal.ROUND_HALF_UP);

	}

	private BigDecimal getInterest(BigDecimal monthlyInterest, AnnuityRequest annuityRequest) {
		return (monthlyInterest.multiply(annuityRequest.getLoanAmount()));
	}

	private void validateRequest(AnnuityRequest annuityRequest) {
		if (annuityRequest == null || annuityRequest.getLoanAmount() == null
				|| annuityRequest.getLoanAmount().equals(BigDecimal.ZERO) || annuityRequest.getNominalRate() == null
				|| annuityRequest.getNominalRate().equals(BigDecimal.ZERO) || annuityRequest.getDuration() == null) {
			throw new ValidationException("Bad Request");
		}
	}

}
